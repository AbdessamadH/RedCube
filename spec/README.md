# RedCube Resto — Spécification du projet

Application de gestion de restaurant « de A à Z » : de la table scannée par le client
jusqu'au réapprovisionnement du stock, en passant par la cuisine, le paiement et le pilotage.

> Statut : **brouillon v0.1** — à valider avec le porteur du projet.
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
| 06 | [Paiement smartphone](06-paiement.md) | Apple/Google Pay, partage d'addition, pourboires, caisse |
| 07 | [Fonctions additionnelles](07-fonctions-additionnelles.md) | Mes propositions pour le point « 6. autre » |
| 08 | [Modèle de données & API](08-donnees-api.md) | Entités, événements temps réel, endpoints principaux |
| 09 | [Sécurité, légal, RGPD](09-securite-legal.md) | Vidéo, données perso, PCI-DSS, fiscalité caisse |
| 10 | [Questions ouvertes](10-questions-ouvertes.md) | Décisions à prendre avant de démarrer |
| 11 | [Roadmap & organisation](11-roadmap.md) | Phases, MVP, équipe, risques, critères de réussite |

## En une phrase par module

1. **Menu 3D** — le client scanne le QR de sa table, voit les plats en 3D (et en réalité augmentée sur la table), commande sans installer d'application.
2. **Vision tables** — des caméras + une IA locale classent chaque table : *libre & propre*, *libre à débarrasser*, *occupée non servie*, *occupée servie*. Le plan de salle s'actualise en direct.
3. **Stock** — chaque plat vendu décrémente automatiquement ses ingrédients ; alertes de seuil, DLC, bons de commande fournisseurs.
4. **Commandes en ligne** — un seul flux de commandes (table, emporter, livraison, plateformes tierces) vers l'écran cuisine.
5. **Paiement smartphone** — payer depuis la table en 2 clics, partager l'addition, laisser un pourboire.
6. **Autres** — réservations, fidélité, appel serveur, allergènes, tableau de bord, prévisions IA, planning du personnel, HACCP… (détail dans le doc 07).
