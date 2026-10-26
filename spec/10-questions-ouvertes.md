# 10 — Questions ouvertes

## ✅ Décisions prises

| # | Question | Décision | Conséquences dans la spec |
|---|----------|----------|---------------------------|
| 1 | Pour qui ? | **Produit à vendre (SaaS)** à d'autres restaurants | Multi-tenant jour 1, onboarding, abonnements, console super-admin ([13](13-modele-saas.md)) |
| 2 | Pays | **Maroc** | Paiement CMI/PSP local + wallets, loi 09-08 / CNDP, hébergement au Maroc, FR/AR/EN, MAD, WhatsApp ([06](06-paiement.md), [09](09-securite-legal.md)) |
| 3 | Caisse / plateformes existantes | **Aucune** | **Caisse intégrée au produit** ([12](12-caisse.md)) ; livraison propre d'abord, Glovo plus tard ([05](05-commandes.md)) |

## Business (prioritaires)

4. **Restaurant pilote** : avez-vous 1 à 3 restaurants partenaires prêts à tester gratuitement pendant le développement ? (indispensable)
5. **Cible prioritaire** : restaurants haut de gamme / touristiques, chaînes, cafés, ou fast-food ? Dans quelle ville commencer ?
6. **Budget** disponible et date visée pour la première version commercialisable ?
7. **Structure** : société déjà créée (pour signer avec le CMI/PSP, la CNDP, les restaurants) ?
8. Le **module Vision** (caméras) est-il au cœur de l'offre, ou une option premium ? Qui installe le matériel (vous, un partenaire installateur) ?
9. Nom commercial : « RedCube » est-il le nom du produit ? (domaine `.ma` à réserver)

## Produit

10. Combien de plats en 3D au lancement pour un restaurant type ? Prestation 3D faite par vous ou par le restaurateur lui-même ?
11. Commande à table : paiement **avant** ou **après** le repas (ou choix par restaurant — recommandé) ?
12. Lesquelles des fonctions additionnelles ([07](07-fonctions-additionnelles.md)) retenez-vous ?
13. Darija dans l'interface client, ou seulement arabe standard / français / anglais ?

## Technique

14. Équipe : développeurs internes, freelances, agence ? Quelles compétences (JS, Python, mobile) ?
15. Le dossier `android/` existant (intro RedCube) doit-il devenir la base de la caisse / app staff, ou rester l'identité visuelle (écran de démarrage) ?

## À vérifier par nous (recherches, pas des décisions)

- Conditions et API du **CMI** et des PSP agréés (frais, délais d'intégration, environnement de test).
- Disponibilité réelle d'**Apple Pay / Google Pay** via ces passerelles au Maroc.
- Procédure **CNDP** pour la vidéo et pour l'hébergement ; offres d'hébergement cloud au Maroc.
- Mentions obligatoires des factures et calendrier de la **facturation électronique** DGI.
- Taux de TVA applicables à la restauration.
- Accès à l'**API partenaire Glovo**.
- Concurrents présents au Maroc (POS, menus QR) et leurs prix.
