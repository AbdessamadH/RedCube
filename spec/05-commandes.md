# 05 — Commandes (sur place et en ligne)

## Canaux

| Canal | Origine | Particularités |
|-------|---------|----------------|
| **Table (QR)** | App client | Rattachée à la table, panier partagé, envoi en plusieurs fois (entrées puis plats) |
| **Serveur** | App staff | Le serveur saisit pour le client (personnes âgées, groupes) |
| **À emporter / click & collect** | Site / PWA / **WhatsApp** | Créneau de retrait, paiement en ligne ou sur place |
| **Livraison propre** | Site / PWA / WhatsApp | Zone, frais, **paiement à la livraison** (espèces) ou en ligne, app livreur simple (phase 2) |
| **Plateformes tierces** (plus tard) | Glovo en priorité | Aucun restaurant pilote ne les utilise aujourd'hui : intégration en phase 4, via API partenaire si accessible ❓ |

Chaque restaurant dispose d'une **page de commande en ligne** à son nom (lien à partager sur Instagram,
Google Maps, WhatsApp) — sans commission, argument fort face aux plateformes.

Toutes les commandes arrivent dans **le même flux** et le même KDS.

## Cycle de vie d'une commande

```
BROUILLON (panier) → ENVOYEE → ACCEPTEE → EN_PREPARATION → PRETE → SERVIE / RETIREE / LIVREE → CLOTUREE
                         └─► REFUSEE / ANNULEE (avec motif, remboursement si payée)
```

Chaque **ligne** a aussi son propre statut (une boisson peut être servie avant le plat).

## Écran cuisine (KDS)

- Tickets triés par heure, couleur selon le temps d'attente (vert → orange → rouge).
- Routage par **poste** : bar, chaud, froid, pâtisserie — chaque poste ne voit que ses lignes.
- Regroupement des quantités (« 5 × burgers en cours »).
- Bouton « Prêt » → notification au serveur (ou au client pour l'emporter).
- Bouton « Rupture » → rend le plat indisponible partout.
- Imprimante de tickets en option (ESC/POS) pour les cuisines qui préfèrent le papier.
- **Fonctionne hors ligne** : file locale, resynchronisation au retour du réseau.

## Règles métier

- Horaires d'ouverture et fermeture des prises de commande (ex. cuisine ferme à 22 h 30).
- Délai de préparation estimé dynamique (selon la charge actuelle du KDS).
- Limite de capacité : suspendre l'emporter/livraison automatiquement si la cuisine est saturée.
- Annulation : libre avant `ACCEPTEE`, sur validation d'un responsable ensuite.
- Notes client (« sans oignon ») et allergies mises en évidence sur le ticket.

## Notifications

- Client : commande reçue, prête, en route — par **WhatsApp** en priorité, SMS en secours, web push.
- Serveur : plat prêt à servir, client qui appelle, table en attente depuis trop longtemps.

## Critères d'acceptation

- [ ] Commande QR visible sur le KDS en < 1 s.
- [ ] Une commande en ligne (livraison) et une commande table apparaissent dans le même KDS.
- [ ] Coupure Internet de 10 min en cuisine : aucun ticket perdu.
