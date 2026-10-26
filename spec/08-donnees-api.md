# 08 — Modèle de données & API

## Entités principales

```
Plan(offre) 1─n Abonnement n─1 Organisation        # côté SaaS
Organisation 1─n Restaurant 1─n Salle 1─n Table 1─n QrCode
                     │                    └─n TableState (historique des états)
                     │                    └─n Camera ─n TableZone (polygones)
                     ├─n Utilisateur (rôle : gérant, manager, serveur, cuisine, livreur)
                     ├─n Menu 1─n Categorie 1─n Plat 1─n OptionGroupe 1─n Option
                     │                               └─n Media (photo, glb, usdz)
                     │                               └─1 FicheTechnique 1─n LigneRecette ─► Ingredient
                     ├─n Ingredient 1─n Lot
                     │              1─n MouvementStock
                     ├─n Fournisseur 1─n BonCommande 1─n LigneBonCommande
                     ├─n SessionTable (une occupation de table) 1─n Commande 1─n LigneCommande
                     │                                          1─n Note(addition) 1─n Paiement
                     ├─n SessionCaisse (ouverture/fermeture, fond, Z) 1─n Ticket ─► JournalCaisse (chaîné)
                     ├─n Facture (n° séquentiel, ICE client optionnel)
                     ├─1 ConfigPaiement (passerelle, identifiants chiffrés)
                     ├─n Client (optionnel, si compte/fidélité)
                     └─n Reservation
```

Conventions :
- Identifiants UUID, `restaurant_id` sur chaque table (multi-tenant + sécurité au niveau ligne).
- Montants en **centimes entiers** + code devise (`MAD`) ; jamais de flottants pour l'argent.
- Les prix sont **copiés** dans la ligne de commande au moment de la commande (historique fiable).
- Mouvements de stock et paiements : tables **append-only** (on n'efface pas, on corrige par un nouveau mouvement).

## API REST (extraits)

```
# Public / client
GET    /v1/public/tables/{code}            → restaurant, table, menu, session ouverte
POST   /v1/public/sessions/{id}/orders     → envoyer une commande
POST   /v1/public/sessions/{id}/calls      → appeler le serveur (type : addition, eau…)
POST   /v1/public/bills/{id}/payments      → initier un paiement (montant ou lignes)

# Staff
GET    /v1/floor                           → plan de salle + états
PATCH  /v1/tables/{id}/state               → correction manuelle d'état
PATCH  /v1/order-lines/{id}                → statut ligne (en préparation, prête, servie)

# Back-office
CRUD   /v1/menus, /v1/dishes, /v1/ingredients, /v1/recipes, /v1/suppliers
POST   /v1/stock/movements                 → réception, perte, ajustement
POST   /v1/purchase-orders/suggest         → proposition de commande fournisseur
GET    /v1/reports/sales?from=&to=

# Intégrations entrantes
POST   /v1/webhooks/payments/{provider}
POST   /v1/webhooks/whatsapp
POST   /v1/webhooks/delivery/{platform}    # plus tard (Glovo)
POST   /v1/pos/sync                         # synchro de la caisse après une coupure
POST   /v1/edge/table-states               → boîtier vision (auth par certificat/clé d'appareil)
```

Documentation générée en **OpenAPI** ; types TypeScript générés pour les fronts.

## Temps réel (WebSocket)

Canaux par rôle, par restaurant :

| Canal | Événements |
|-------|-----------|
| `table-session:{id}` | `cart.updated`, `order.status`, `bill.updated`, `payment.succeeded` |
| `floor:{restaurant}` | `table.state_changed`, `call.created`, `order.ready` |
| `kds:{restaurant}:{poste}` | `ticket.created`, `ticket.updated`, `dish.out_of_stock` |
| `backoffice:{restaurant}` | `stock.alert`, `sales.tick` |

## Événements internes (bus)

`order.sent` → (KDS, stock réservé) · `order_line.served` → (déstockage, état table) ·
`payment.succeeded` → (note, caisse, fidélité) · `table.state_changed` → (alertes, liste d'attente) ·
`ingredient.depleted` → (menu indisponible).
