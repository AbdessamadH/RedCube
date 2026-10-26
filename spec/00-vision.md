# 00 — Vision & périmètre

## Problème

Dans un restaurant, les pertes de temps et d'argent viennent surtout de :

- **l'attente** : le client attend la carte, le serveur, puis l'addition ;
- **l'aveuglement en salle** : le responsable ne sait pas d'un coup d'œil quelles tables sont
  à débarrasser ou quels clients assis n'ont encore rien reçu ;
- **le stock subi** : ruptures en plein service, gaspillage, inventaires faits à la main ;
- **l'éparpillement** : une tablette par plateforme de livraison, une caisse, un TPE, un cahier
  de réservations — rien ne se parle.

## Vision

Un **système unique** où chaque événement (scan, commande, plat envoyé, table débarrassée,
paiement, livraison fournisseur) alimente les autres modules en temps réel.

## Acteurs

| Acteur | Support | Besoins principaux |
|--------|---------|--------------------|
| **Client sur place** | Smartphone (navigateur, sans installation) | Voir la carte, commander, appeler, payer |
| **Client à distance** | Site web / PWA | Commander à emporter ou en livraison, réserver |
| **Serveur** | Smartphone / montre staff | Plan de salle live, alertes (table à débarrasser, client qui appelle, plat prêt) |
| **Cuisine / bar** | Écran KDS (tablette murale) | File des tickets, minuteurs, ruptures |
| **Responsable / gérant** | Back-office web | Carte, prix, stock, achats, personnel, statistiques |
| **Fournisseur** (optionnel) | E-mail / portail | Recevoir les bons de commande |

## Parcours cible « client sur place »

```
Arrivée → s'installe → (caméra : table « occupée non servie », chrono démarre)
  → scanne le QR → menu 3D → commande → ticket en cuisine (KDS)
  → plat servi → (caméra/serveur : « occupée servie »)
  → paie sur son téléphone → part → (caméra : « libre à débarrasser » → alerte serveur)
  → serveur débarrasse → (caméra : « libre & propre ») → table réattribuable
```

## Périmètre v1 (MVP) vs plus tard

| Inclus en v1 | Plus tard |
|--------------|-----------|
| Menu web via QR + modèles 3D sur une sélection de plats | 3D de toute la carte, AR avancée |
| Commande à table + à emporter | Livraison propre avec suivi livreur |
| KDS cuisine | Intégration plateformes (Uber Eats, Glovo, Deliveroo…) |
| Paiement en ligne (carte, Apple Pay, Google Pay) + partage d'addition | Programme de fidélité complet |
| Stock : fiches techniques, déstockage auto, alertes | Commande fournisseur automatique, prévisions IA |
| Vision tables sur 1 salle pilote | Multi-salles / multi-sites, analytics vidéo |

## Hors périmètre (explicitement)

- Reconnaissance faciale ou identification des personnes (interdit/risqué juridiquement, inutile ici).
- Enregistrement vidéo longue durée (ce n'est pas un système de vidéoprotection).
- Comptabilité générale (export vers le logiciel du comptable à la place).
