# 02 — Menu 3D via QR code

## Objectif

Le client scanne le QR code imprimé sur sa table ; la carte s'ouvre dans son navigateur,
déjà rattachée à **sa table**. Il peut faire tourner chaque plat en 3D et, s'il le veut,
le **poser virtuellement sur la table** (réalité augmentée) pour juger de la taille.

## QR code par table

- Un QR = une URL : `https://<domaine>/t/<code_table>` (ex. `/t/K7P2QX`).
- `code_table` est un identifiant aléatoire, non devinable, lié à `restaurant_id` + `table_id`.
- **Anti-commande à distance** : au scan, l'API ouvre une *session de table* courte
  (ex. 3 h) ; la commande n'est acceptée que si (au choix, configurable) :
  1. la table est marquée occupée (par la caméra ou le serveur), **ou**
  2. le client est sur le Wi-Fi du restaurant / géolocalisé à proximité, **ou**
  3. le paiement est fait avant l'envoi en cuisine.
- Plusieurs personnes peuvent rejoindre la même session (panier partagé de la table).
- Support physique : sticker résistant ou chevalet ; option **NFC** dans le même support.
- Le back-office génère les QR en PDF prêt à imprimer, avec le numéro de table visible.

## Carte (sans 3D)

- Catégories, plats, photos, description, prix, **allergènes** (14 allergènes réglementaires),
  régimes (végétarien, halal, sans gluten…), calories optionnelles.
- Options et suppléments (cuisson, sauce, taille, accompagnement) avec règles min/max.
- Plat marqué **indisponible** automatiquement quand le stock d'un ingrédient est épuisé.
- Cartes multiples : midi / soir / brunch, horaires de disponibilité.
- Recherche et filtres (ex. « sans lactose »).
- Multilingue **FR / AR (affichage RTL) / EN**, traduction assistée par IA dans le back-office ; prix en MAD.
- Cartes spéciales programmables (ex. **menu ftour pendant le Ramadan**).

## Modèles 3D

### Visualisation

- Composant **`<model-viewer>`** (Google, open source) : rotation, zoom, éclairage réaliste.
- **AR** sans application :
  - Android : *Scene Viewer* (WebXR) avec fichier `.glb` ;
  - iPhone : *AR Quick Look* avec fichier `.usdz`.
- Chargement **à la demande** (bouton « Voir en 3D ») pour ne pas ralentir la carte.
- Repli automatique : photo HD + vidéo 360° si l'appareil ne supporte pas la 3D.

### Production des modèles (le vrai travail)

| Méthode | Qualité | Coût / effort |
|---------|---------|---------------|
| **Photogrammétrie** smartphone (peut être vendue comme prestation, voir doc 13) (Polycam, RealityScan, Luma…) : 60–150 photos autour de l'assiette | Très réaliste | Faible matériel, ~30–60 min par plat |
| Gaussian splatting (Luma, Polycam) | Le plus réaliste | Format moins standard pour l'AR, à surveiller |
| Modélisation 3D par un graphiste (Blender) | Stylisé, parfait | Coûteux par plat |

Pipeline d'optimisation (automatisé par un worker à l'upload) :

1. upload du modèle brut dans le back-office ;
2. nettoyage, décimation, compression géométrie (Draco/meshopt) et textures (KTX2) ;
3. export `.glb` (≤ 3–5 Mo) + `.usdz` ;
4. mise à l'échelle réelle (diamètre de l'assiette) pour une AR crédible ;
5. génération d'une vignette.

## Critères d'acceptation

- [ ] Scan → carte affichée en < 2 s sur un smartphone milieu de gamme en 4G.
- [ ] Le panier est rattaché à la bonne table sans aucune saisie.
- [ ] Un plat 3D s'affiche en < 4 s ; l'AR s'ouvre sur iOS et Android récents.
- [ ] Un plat en rupture disparaît (ou est grisé) en moins d'une minute.
- [ ] Commande impossible depuis un QR photographié et utilisé hors du restaurant (règle active).
