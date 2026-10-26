# 11 — Roadmap & organisation

Hypothèse : petite équipe (voir ci-dessous), restaurant pilote disponible pour les tests.
Durées indicatives, à recaler après les réponses du doc 10.

## Phase 0 — Cadrage & design (3–4 semaines)

- Réponses aux questions ouvertes, choix pays/prestataires/caisse.
- Ateliers sur place : observer un service complet, interviewer serveurs et cuisine.
- Maquettes Figma : app client, KDS, app staff, back-office ; tests sur 5 clients.
- **POC vision** en parallèle (1 caméra, 4 tables) → décision go / no-go sur la précision.
- **POC 3D** : 3 plats scannés, test AR sur iPhone et Android.

## Phase 1 — MVP « service à table » (8–10 semaines)

- Socle : monorepo, auth, rôles, multi-tenant, CI/CD, environnements.
- Back-office : carte, plats, options, allergènes, tables, QR imprimables.
- App client : menu (photos + 3D sur sélection), panier partagé, commande, appel serveur.
- KDS cuisine + app staff (plan de salle manuel, notifications).
- Paiement en ligne (carte, Apple/Google Pay) + partage d'addition + pourboire.
- Intégration caisse certifiée (si France).
- 🎯 **Pilote en conditions réelles** sur quelques services, puis correction.

## Phase 2 — Stock & en ligne (6–8 semaines)

- Ingrédients, fiches techniques, déstockage auto, alertes, inventaire mobile, pertes, food cost.
- Rupture → indisponibilité automatique.
- Click & collect, puis intégration plateformes de livraison (via agrégateur).
- Tableau de bord gérant.

## Phase 3 — Vision en production (6–8 semaines, démarrée dès la fin du POC)

- Installation caméras + boîtier, outil de calibrage des zones.
- Collecte/annotation, fine-tuning, fusion avec commandes/KDS.
- Plan de salle live, alertes, indicateurs de temps de service.
- Démarches légales (AIPD, signalétique, information du personnel) **avant** la mise en route.

## Phase 4 — Croissance

- Réservations + liste d'attente branchée sur la détection de tables.
- Fidélité/CRM, avis, bons de commande fournisseurs automatiques, OCR factures.
- HACCP + capteurs, planning du personnel, prévisions IA.
- Multi-sites / version SaaS commercialisable.

**Total jusqu'à un produit complet v1 (phases 0–3) : ~6 à 8 mois.**

## Équipe type

| Rôle | Charge |
|------|--------|
| Chef de projet / product owner (vous ?) | Temps partiel, décisions, lien avec le restaurant |
| Designer UX/UI | Phase 0 à temps plein, puis ponctuel |
| 2 développeurs full-stack TypeScript | Temps plein |
| 1 ingénieur vision / IA (Python) | Mi-temps, phases 0 et 3 |
| Opérateur 3D (peut être formé en interne) | Ponctuel, ~1 h par plat |
| Juriste / DPO | Ponctuel (vidéo, RGPD, CGV) |

## Principaux risques

| Risque | Impact | Parade |
|--------|--------|--------|
| Précision vision insuffisante (lumière, angles, occlusions) | Fort | POC tôt, fusion avec les données de commande, correction manuelle |
| Juridique vidéo | Fort | AIPD, traitement local, pas d'enregistrement, avis juridique |
| Clients qui ne veulent pas commander sur téléphone | Moyen | Le serveur peut toujours prendre la commande dans l'app staff |
| Poids des modèles 3D / téléphones anciens | Moyen | Chargement à la demande, compression, repli photo |
| Obligations de caisse certifiée | Fort (France) | S'interfacer avec une caisse certifiée existante |
| Réseau instable en salle | Moyen | Wi-Fi pro, mode hors ligne du KDS, 4G de secours |
| Adoption par l'équipe | Fort | Impliquer les serveurs dès les maquettes, formation courte |

## Critères de réussite (à 3 mois après lancement)

- ≥ 40 % des commandes à table passées via QR.
- Temps d'attente moyen avant prise de commande réduit de 50 %.
- Temps de débarrassage moyen < 5 min.
- Écart d'inventaire théorique/réel < 3 %.
- Ticket moyen +5 à 10 % (upsell, commande plus facile).
- Note de satisfaction client ≥ 4,5/5.

## Prochaines étapes concrètes

1. Répondre aux questions du [doc 10](10-questions-ouvertes.md).
2. Valider la liste des fonctions v1 (cocher dans le doc 07).
3. Lancer en parallèle les deux POC (vision + 3D) — ce sont les deux inconnues techniques.
4. Démarrer les maquettes.
