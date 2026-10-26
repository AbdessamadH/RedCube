# 03 — Vision : état des tables par caméra

## Objectif

Afficher en temps réel, sur le plan de salle et l'app des serveurs, l'état de chaque table :

| État | Couleur | Définition |
|------|---------|------------|
| `LIBRE_PROPRE` | 🟢 vert | Aucune personne, aucune vaisselle/déchet sur la table |
| `LIBRE_A_DEBARRASSER` | 🟠 orange | Aucune personne, mais vaisselle/verres/déchets présents |
| `OCCUPEE_NON_SERVIE` | 🔴 rouge | Personnes assises, aucun plat/boisson servi (chrono affiché) |
| `OCCUPEE_SERVIE` | 🔵 bleu | Personnes assises et au moins un plat/boisson servi |

Alertes : table orange depuis > X min, table rouge depuis > Y min sans commande, etc.

## Matériel

- **Caméras IP PoE** fixes en plongée (vue de dessus ou en biais à ~45°), 1 caméra couvre
  typiquement 4 à 10 tables ; résolution 1080p à 4 MP suffisante.
- **Boîtier IA local** : NVIDIA Jetson Orin Nano / mini-PC avec GPU ou accélérateur (Hailo, Coral).
  Analyse ~1 à 2 images/seconde par caméra : largement suffisant, l'état d'une table change lentement.
- Switch PoE, onduleur.
- Ordre de grandeur matériel ❓ à chiffrer sur devis auprès de distributeurs au Maroc ; à packager en **kit standard** vendu ou loué (voir doc 13).

## Pipeline logiciel (sur le boîtier)

```
Flux RTSP ─► échantillonnage 1–2 fps ─► détection d'objets (YOLO)
         ─► affectation aux zones « table » (polygones dessinés une fois dans le back-office)
         ─► règles + lissage temporel ─► état de table ─► envoi au cloud si changement
```

1. **Détection** — modèle YOLO (v8/v11) pré-entraîné sur COCO, qui connaît déjà
   `person`, `cup`, `bowl`, `bottle`, `wine glass`, `fork`, `knife`, `dining table`…
   puis **affiné (fine-tuning)** sur des images du restaurant avec des classes métier :
   `assiette_pleine`, `assiette_vide`, `verre`, `serviette_usagee`, `dechets`, `nappe_propre`.
2. **Zones** — pour chaque caméra, le gérant dessine le polygone de chaque table et de ses
   chaises sur une capture d'écran (outil de calibrage dans le back-office).
3. **Règles** (exemple) :
   - personnes dans la zone chaises ≥ 1 pendant > 20 s → *occupée* ;
   - aucune personne pendant > 60 s → *libre* ;
   - *libre* + objets « sales » détectés → `LIBRE_A_DEBARRASSER`, sinon `LIBRE_PROPRE` ;
   - *occupée* + plat/boisson détecté → `OCCUPEE_SERVIE`, sinon `OCCUPEE_NON_SERVIE`.
4. **Lissage** — hystérésis / vote sur une fenêtre glissante pour éviter le clignotement
   (un serveur qui passe devant ≠ table occupée).

## Fusion avec les autres données (fiabilité ++)

La caméra n'est pas seule à décider :

- une commande envoyée par la table → confirme *occupée* ;
- le KDS marque « servi » → bascule vers `OCCUPEE_SERVIE` même si la caméra hésite ;
- paiement complété + caméra « personne » absente → `LIBRE_A_DEBARRASSER` immédiat ;
- un serveur peut **toujours corriger** l'état d'un geste dans l'app ; ces corrections
  sont journalisées et servent de **données d'entraînement** pour améliorer le modèle.

## Protection de la vie privée (by design)

- Traitement 100 % local, **aucune image n'est envoyée ni stockée** par défaut.
- Pas de reconnaissance faciale, pas de suivi d'individus, floutage des visages si une
  capture est conservée (ex. pour l'entraînement, avec consentement/encadrement).
- Voir [09-securite-legal.md](09-securite-legal.md) (signalétique, analyse d'impact).

## Indicateurs produits

- Temps moyen d'attente avant service (rouge → bleu).
- Temps moyen de débarrassage (orange → vert).
- Taux de rotation des tables, taux d'occupation par créneau.

## Critères d'acceptation (salle pilote)

- [ ] Précision de l'état ≥ 95 % sur un échantillon annoté de 500 situations réelles.
- [ ] Changement d'état visible sur l'app staff en < 5 s.
- [ ] Aucun clignotement d'état (< 1 faux changement / table / heure).
- [ ] Fonctionnement en éclairage du soir (bougies, lumière tamisée) validé.

## Démarche recommandée

1. **POC 2 semaines** : 1 caméra, 4 tables, modèle COCO + règles simples, mesure de précision.
2. Collecte et annotation d'images (outil : CVAT / Label Studio / Roboflow).
3. Fine-tuning, puis déploiement sur toute la salle.
