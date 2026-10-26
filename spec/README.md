# RedCube Resto — Spécification du projet

**Produit SaaS** de gestion de restaurant « de A à Z », destiné à être **vendu aux restaurants
au Maroc** : de la table scannée par le client jusqu'au réapprovisionnement du stock, en passant
par la caisse, la cuisine, le paiement et le pilotage.

> Statut : **brouillon v0.2** — intègre les décisions : produit à vendre (SaaS), marché marocain,
> pas de caisse ni de plateforme de livraison existante à intégrer (la caisse fait partie du produit).
> Les points marqués ❓ sont des décisions ouvertes (voir [`10-questions-ouvertes.md`](10-questions-ouvertes.md)).

## Sommaire

| # | Document | Contenu |
|---|----------|---------|
| 00 | [Vision & périmètre](00-vision.md) | Problème, cibles, acteurs, parcours, ce qui est hors périmètre |
| 01 | [Architecture technique](01-architecture.md) | Applications, stack, schéma global, déploiement |
| 02 | [Menu 3D via QR code](02-menu-3d-qr.md) | QR par table, menu web, modèles 3D / réalité augmentée |
| 03 | [Vision : état des tables](03-vision-tables.md) | Caméras, détection IA, machine à états des tables |
| 04 | [Gestion de stock](04-stock.md) | Fiches techniques, déstockage auto, inventaires, achats |
| 05 | [Commandes](05-commandes.md) | Sur place, à emporter, livraison, écran cuisine (KDS) |
| 06 | [Paiement smartphone](06-paiement.md) | CMI / wallets mobiles, modèle d'encaissement SaaS, partage d'addition, pourboires |
| 07 | [Fonctions additionnelles](07-fonctions-additionnelles.md) | Mes propositions pour le point « 6. autre » |
| 08 | [Modèle de données & API](08-donnees-api.md) | Entités, événements temps réel, endpoints principaux |
| 09 | [Sécurité & légal (Maroc)](09-securite-legal.md) | Loi 09-08 / CNDP, vidéo, hébergement, sécurité |
| 10 | [Questions ouvertes](10-questions-ouvertes.md) | Décisions à prendre avant de démarrer |
| 11 | [Roadmap & organisation](11-roadmap.md) | Phases, MVP, équipe, risques, critères de réussite |
| 12 | [Caisse (POS) & facturation](12-caisse.md) | Caisse intégrée, factures conformes Maroc, journal inaltérable, matériel |
| 13 | [Modèle SaaS](13-modele-saas.md) | Cibles, offres et prix, onboarding, spécificités du marché marocain |

## En une phrase par module

1. **Menu 3D** — le client scanne le QR de sa table, voit les plats en 3D (et en réalité augmentée sur la table), commande sans installer d'application.
2. **Vision tables** — des caméras + une IA locale classent chaque table : *libre & propre*, *libre à débarrasser*, *occupée non servie*, *occupée servie*. Le plan de salle s'actualise en direct.
3. **Stock** — chaque plat vendu décrémente automatiquement ses ingrédients ; alertes de seuil, DLC, bons de commande fournisseurs.
4. **Commandes en ligne** — un seul flux de commandes (table, emporter, livraison, WhatsApp ; Glovo plus tard) vers l'écran cuisine.
5. **Paiement smartphone** — payer depuis la table (carte via CMI, wallets mobiles / QR marchand), partager l'addition, laisser un pourboire ; espèces toujours gérées.
6. **Caisse intégrée** (ajoutée : aucune caisse existante à intégrer) — encaissement multi-moyens, factures conformes (ICE, TVA), rapport Z, fonctionne hors ligne.
7. **Autres** — réservations, fidélité, appel serveur, allergènes, tableau de bord, prévisions IA, planning du personnel, HACCP… (détail dans le doc 07).
