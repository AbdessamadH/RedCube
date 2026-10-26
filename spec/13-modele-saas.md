# 13 — Modèle SaaS : produit, offres, commercialisation

> Les prix ci-dessous sont des **hypothèses de travail** à valider par une étude terrain
> (entretiens avec 15–20 restaurateurs) avant de les publier.

## Positionnement

« Le système tout-en-un du restaurant marocain » : menu QR 3D, caisse, commandes, paiement mobile,
stock — et en option, la détection intelligente des tables par caméra, que peu de concurrents proposent.

Cibles prioritaires :

1. Restaurants et cafés **moyen / haut de gamme** des grandes villes (Casablanca, Rabat, Marrakech, Tanger)
   et zones touristiques — clientèle internationale → valeur forte du menu multilingue 3D.
2. **Chaînes et franchises** locales (multi-sites, besoin de stock et reporting centralisés).
3. Plus tard : snacks / fast-food (offre d'entrée de gamme simplifiée), hôtels-restaurants.

## Offres (hypothèse)

| Offre | Contenu | Prix indicatif ❓ |
|-------|---------|-------------------|
| **Essentiel** | Menu QR (photos), commande à table, caisse, KDS, paiement | ~ 300–500 MAD / mois / établissement |
| **Pro** | + menu 3D/AR, stock & fiches techniques, click & collect, livraison, tableau de bord | ~ 700–1 000 MAD / mois |
| **Vision** (module) | Détection des tables par caméra, plan de salle live, alertes | Abonnement + **kit matériel** (achat ou location) + installation |
| **Groupe** | Multi-sites, cuisine centrale, rapports consolidés, API | Sur devis |

Revenus complémentaires :
- **Prestation de numérisation 3D** des plats (forfait par plat ou par carte) ;
- installation / formation sur site ;
- vente ou location du matériel (tablettes, imprimantes, caméras, boîtier IA) ;
- commission d'apport éventuelle de la part du PSP de paiement ❓ (sans encaisser nous-mêmes — voir doc 06).

Période d'essai gratuite (ex. 30 jours) sur l'offre Essentiel ; engagement annuel avec remise.

## Ce que le SaaS implique dans le produit

| Besoin | Fonction à construire |
|--------|----------------------|
| Inscription autonome | **Onboarding** : créer son restaurant, importer sa carte (Excel/photo du menu → saisie assistée par IA), générer les QR |
| Abonnements | Gestion des offres, essais, factures d'abonnement (encaissement via CMI/virement), relances, suspension |
| Activer/désactiver des modules | **Feature flags par offre** |
| Support | Console **super-admin** : liste des restaurants, santé des boîtiers vision, accès support (avec accord et journalisé) |
| Personnalisation | Logo, couleurs, domaine/sous-domaine du restaurant (`nomresto.redcube.ma` ❓) |
| Multi-sites | Organisation → restaurants, droits par site |
| Mises à jour | Déploiement continu sans interruption pendant les services ; mises à jour à distance des boîtiers vision |
| Mesure | Analytics produit (activation, usage par module, churn) |

## Spécificités du marché marocain à intégrer

- **Langues** : français, arabe (interface RTL), anglais ; darija pour les messages marketing ❓ ; espagnol utile au nord.
- **WhatsApp** est le canal n°1 : confirmations de commande, reçus, réservations, relances via **WhatsApp Business API**.
- **Paiement à la livraison** et espèces très présents → le produit doit les gérer nativement.
- **Livraison** : Glovo est l'acteur dominant (intégration en phase ultérieure) ; beaucoup de restaurants livrent avec leurs propres livreurs → module livraison propre utile.
- Connexion parfois instable → **mode hors ligne** de la caisse et du KDS indispensable.
- Saisonnalité (Ramadan : horaires de ftour/shour, menus spéciaux ; saison touristique) → cartes et horaires programmables.
- Support client par **téléphone/WhatsApp**, en français et en darija.

## Concurrence (à étudier en phase 0)

Recenser : caisses/POS déjà présentes au Maroc (locales et internationales), solutions de menu QR,
plateformes de livraison qui proposent des outils restaurateurs. Notre différenciation : **intégration tout-en-un
+ 3D/AR + vision + adaptation locale** (paiement, langues, WhatsApp, hors ligne).

## Indicateurs business

- Nombre d'établissements actifs, MRR (revenu mensuel récurrent), churn mensuel < 3 %.
- Coût d'acquisition client vs valeur vie client.
- Taux d'activation : restaurant inscrit → première commande QR en < 7 jours.
