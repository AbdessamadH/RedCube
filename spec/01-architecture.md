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

Dès le départ, chaque donnée porte un `restaurant_id` (et `organisation_id` pour les groupes).
Cela permet de vendre la solution à d'autres restaurants (modèle SaaS) sans refonte.

## Déploiement & environnements

- `dev` (local, Docker Compose) → `staging` → `production`.
- Hébergement : un cloud européen ou proche des clients (OVHcloud, Scaleway, AWS eu-west-3…). ❓
- CI/CD : GitHub Actions (tests, lint, build, déploiement).
- Observabilité : logs centralisés, métriques, alertes (Sentry pour les erreurs front/back).

## Exigences non fonctionnelles

| Exigence | Cible |
|----------|-------|
| Ouverture du menu après scan | < 2 s en 4G (hors modèle 3D, chargé à la demande) |
| Latence commande → KDS | < 1 s |
| Latence changement d'état table → plan de salle | < 5 s |
| Disponibilité pendant les services | 99,9 % |
| Mode dégradé | KDS et app staff continuent si Internet coupe (file locale, resynchronisation) |
| Accessibilité | WCAG 2.1 AA sur l'app client |
| Langues | FR, EN, AR (RTL) au minimum ❓ |
