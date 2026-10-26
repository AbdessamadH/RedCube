# 11 — Roadmap & organisation

Hypothèse : petite équipe (voir ci-dessous) et **1 à 3 restaurants pilotes** au Maroc.
Durées indicatives, à recaler selon le budget et l'équipe.

## Phase 0 — Cadrage, marché & design (4–6 semaines)

- **Étude terrain** : 15–20 entretiens restaurateurs (douleurs, outils actuels, prix acceptable), analyse concurrentielle.
- Recrutement des **restaurants pilotes**.
- Démarches : contacts CMI / PSP (compte de test), avocat (CGV, contrat de sous-traitance, CNDP), choix de l'hébergeur au Maroc.
- Maquettes Figma : app client, caisse, KDS, app staff, back-office, onboarding — en FR et AR (RTL).
- **POC vision** en parallèle (1 caméra, 4 tables) → décision : module au lancement ou plus tard.
- **POC 3D** : 3 plats scannés, test AR sur iPhone et Android courants au Maroc.

## Phase 1 — MVP « restaurant pilote » (10–12 semaines)

- Socle SaaS : monorepo, auth, rôles, **multi-tenant**, CI/CD, hébergement, sauvegardes.
- Back-office : carte (FR/AR/EN), plats, options, allergènes, tables, QR imprimables.
- App client : menu (photos + 3D sur sélection), panier partagé, commande, appel serveur.
- **Caisse** : encaissement espèces / TPE / paiement en ligne, factures conformes, rapport Z, mode hors ligne, impression.
- KDS cuisine + app staff (plan de salle manuel, notifications).
- Paiement en ligne **CMI** + partage d'addition + pourboire.
- 🎯 **Pilote** dans 1 restaurant pendant 3–4 semaines, puis corrections.

## Phase 2 — Version commercialisable (8–10 semaines)

- **Onboarding autonome**, import de carte assisté par IA, gestion des abonnements et des offres, console super-admin.
- Stock : ingrédients, fiches techniques, déstockage auto, alertes, inventaire mobile, pertes, food cost.
- Commande en ligne : page du restaurant, click & collect, **livraison propre** avec paiement à la livraison, notifications **WhatsApp**.
- Tableau de bord gérant.
- Kit de conformité pour les restaurants (CNDP, panneaux), CGV, site vitrine.
- 🚀 **Lancement commercial** (offres Essentiel et Pro).

## Phase 3 — Module Vision en production (6–8 semaines, démarrée dès la fin du POC)

- Kit matériel standard (caméras, boîtier IA), procédure d'installation, outil de calibrage des zones.
- Collecte/annotation dans les restaurants pilotes, fine-tuning, fusion avec commandes/KDS.
- Plan de salle live, alertes, indicateurs de temps de service ; supervision des boîtiers depuis la console super-admin.
- 🚀 Lancement de l'offre **Vision**.

## Phase 4 — Croissance

- Wallets mobiles / QR marchand, Apple/Google Pay si disponibles.
- Réservations + liste d'attente branchée sur la détection de tables.
- Intégration Glovo, fidélité/CRM, avis, bons de commande fournisseurs automatiques, OCR factures.
- HACCP + capteurs, planning du personnel, prévisions IA.
- Offre Groupe (multi-sites), facturation électronique DGI, expansion (Afrique francophone, Europe).

**Total jusqu'au lancement commercial (phases 0–2) : ~6 à 7 mois ; module Vision : ~8 à 9 mois.**

## Équipe type

| Rôle | Charge |
|------|--------|
| Fondateur / product owner (vous) | Décisions, restaurants pilotes, ventes |
| Designer UX/UI (maîtrise FR + AR/RTL) | Phase 0 à temps plein, puis ponctuel |
| 2–3 développeurs full-stack TypeScript | Temps plein (dont 1 orienté mobile/hors ligne pour la caisse) |
| 1 ingénieur vision / IA (Python) | Mi-temps, phases 0 et 3 |
| Opérateur 3D / installateur | Ponctuel, puis temps plein quand les ventes démarrent |
| Commercial / support (FR + darija) | À partir de la phase 2 |
| Avocat + expert-comptable | Ponctuel (CNDP, CGV, facturation) |

## Principaux risques

| Risque | Impact | Parade |
|--------|--------|--------|
| Intégration CMI/PSP lente ou coûteuse | Fort | Démarrer les démarches en phase 0 ; le MVP fonctionne aussi en espèces/TPE |
| Restaurateurs réticents à payer un abonnement | Fort | Étude de prix en phase 0, essai gratuit, ROI démontré (temps de service, stock) |
| Précision vision insuffisante | Fort | POC tôt, fusion avec les commandes, correction manuelle, module optionnel |
| Juridique (vidéo, données, transfert hors Maroc) | Fort | Traitement local, hébergement au Maroc, avocat, kit de conformité |
| Clients qui préfèrent commander au serveur | Moyen | Le serveur commande dans l'app staff ; le QR reste un plus |
| Connexion instable | Moyen | Caisse et KDS hors ligne, routeur 4G de secours |
| Concurrence de solutions POS établies | Moyen | Différenciation : 3D + vision + local (WhatsApp, AR, espèces) |
| Support et installation qui ne passent pas à l'échelle | Moyen | Onboarding autonome, vidéos de formation, partenaires installateurs |

## Critères de réussite

**Pilote (3 mois après mise en service)** : ≥ 40 % des commandes à table via QR, temps d'attente avant commande −50 %,
écart d'inventaire < 3 %, ticket moyen +5 à 10 %.

**Commercial (12 mois après le lancement)** : objectif de N restaurants payants ❓ à fixer selon le budget,
churn mensuel < 3 %, activation (première commande QR) en < 7 jours.

## Prochaines étapes concrètes

1. Répondre aux questions restantes du [doc 10](10-questions-ouvertes.md) (surtout : restaurant pilote, budget, équipe).
2. Lancer l'étude terrain et les contacts CMI / avocat.
3. Lancer en parallèle les deux POC (vision + 3D).
4. Démarrer les maquettes.
