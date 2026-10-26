# 01 — Architecture technique

## Vue d'ensemble

```
                         ┌──────────────────────── CLOUD ─────────────────────────┐
 Client (navigateur) ──► │  App client (PWA)        API principale    PostgreSQL  │
 Back-office (web)   ──► │  Back-office (web)  ◄──► (REST + WebSocket) Redis      │
 App staff (mobile)  ──► │                          Workers (jobs)    Stockage S3 │
 KDS (tablette)      ──► │                              ▲     ▲       (modèles 3D)│
                         └──────────────────────────────┼─────┼───────────────────┘
                                 Prestataire paiement ──┘     │ événements (MQTT/HTTPS)
                                 (webhooks)                   │  « table 12 = à débarrasser »
                         ┌──────────────────────── RESTAURANT (edge) ─────────────┐
                         │  Caméras IP ──RTSP──► Boîtier IA (Jetson / mini-PC)    │
                         │                       analyse locale, aucune vidéo     │
                         │                       ne sort du restaurant            │
                         └────────────────────────────────────────────────────────┘
```

Principe clé : **la vidéo reste dans le restaurant**. Seuls des états de tables
(`table_id`, `état`, `confiance`, `horodatage`) remontent au cloud.

## Applications

| App | Techno proposée | Pourquoi |
|-----|-----------------|----------|
| **App client** (menu, commande, paiement) | Next.js (React) en **PWA** | Ouverte par QR, rien à installer, SEO pour la commande en ligne |
| **Back-office** gérant | Next.js, même design system | Partage du code avec l'app client |
| **Caisse (POS)** | PWA / app Android tablette, hors ligne d'abord (base locale + synchro) | Voir [12-caisse.md](12-caisse.md) |
| **Site vitrine + onboarding SaaS** | Next.js | Inscription, essai, choix de l'offre |
| **Console super-admin** | Next.js | Support, abonnements, supervision des boîtiers |
| **App staff** (serveurs) | React Native (Expo) ou PWA | Notifications push fiables, vibration ; l'expérience Android native du dépôt peut servir |
| **KDS cuisine** | PWA plein écran sur tablette | Simple à déployer, fonctionne hors ligne quelques minutes |
| **API principale** | Node.js + TypeScript (NestJS) | Un seul langage front/back, typage partagé |
| **Service vision** | Python (Ultralytics YOLO exporté en ONNX/TensorRT) | Écosystème IA de référence |
| **Base de données** | PostgreSQL | Relationnel, transactions (stock, paiements) |
| **Temps réel / cache / files** | Redis (+ BullMQ pour les jobs) | Pub/sub WebSocket, files de tâches |
| **Fichiers** | Stockage objet S3 compatible + CDN | Modèles 3D, photos |

Alternative « aller vite » : Supabase (Postgres + Auth + Realtime + Storage gérés) à la place
de l'API maison pour le MVP. ❓ À trancher selon l'équipe.

## Organisation du code (monorepo)

```
apps/
  client/        # PWA client (menu 3D, commande, paiement)
  backoffice/    # gérant
  staff/         # serveurs
  kds/           # cuisine
  api/           # API + WebSocket
  vision-edge/   # service Python sur le boîtier du restaurant
packages/
  ui/            # design system partagé
  types/         # types/DTO partagés (générés depuis l'API)
  config/        # lint, tsconfig…
infra/           # Docker, IaC (Terraform), CI/CD
spec/            # ce dossier
```

## Multi-tenant

Le produit étant vendu en **SaaS**, le multi-tenant est une exigence du jour 1 :
chaque donnée porte un `restaurant_id` (et `organisation_id` pour les groupes), avec isolation
au niveau base (Row Level Security) et tests automatiques anti-fuite entre restaurants.
Les modules sont activés par **offre d'abonnement** (feature flags).

## Déploiement & environnements

- `dev` (local, Docker Compose) → `staging` → `production`.
- Hébergement : de préférence **au Maroc** (loi 09-08 : le transfert de données à l'étranger est encadré par la CNDP),
  avec une architecture portable (Docker, PostgreSQL standard, stockage S3 compatible). Voir [09-securite-legal.md](09-securite-legal.md). ❓ comparer les offres locales.
- Notifications : **WhatsApp Business API** (canal principal au Maroc), SMS, web push.
- CI/CD : GitHub Actions (tests, lint, build, déploiement).
- Observabilité : logs centralisés, métriques, alertes (Sentry pour les erreurs front/back).

## Exigences non fonctionnelles

| Exigence | Cible |
|----------|-------|
| Ouverture du menu après scan | < 2 s en 4G (hors modèle 3D, chargé à la demande) |
| Latence commande → KDS | < 1 s |
| Latence changement d'état table → plan de salle | < 5 s |
| Disponibilité pendant les services | 99,9 % |
| Mode dégradé | **Caisse**, KDS et app staff continuent si Internet coupe (base locale, resynchronisation) |
| Accessibilité | WCAG 2.1 AA sur l'app client |
| Langues | **FR, AR (RTL), EN** dès le MVP ; ES ensuite |
| Devise / formats | MAD, formats de date et de nombre marocains, fuseau Africa/Casablanca |
