# 09 — Sécurité, légal, RGPD

> Ceci n'est pas un avis juridique : faire valider par un juriste / DPO selon le pays d'exploitation ❓.

## Caméras (le point le plus sensible)

Filmer des clients et des salariés est encadré (en France : RGPD + CNIL + Code du travail ;
au Maroc : loi 09-08 + CNDP).

- **Finalité claire et limitée** : « optimisation du service en salle », pas de surveillance des salariés.
- **Analyse d'impact (AIPD/DPIA)** recommandée avant déploiement.
- **Information** : panneaux visibles à l'entrée et en salle + mention dans la politique de confidentialité.
- **Information/consultation du personnel** (et des représentants du personnel s'il y en a).
- **Minimisation** : traitement local, pas d'enregistrement, pas de reconnaissance faciale,
  caméras orientées sur les tables (pas les toilettes, vestiaires, zones de pause).
- Si un système de vidéoprotection classique existe déjà, il relève de règles différentes ;
  garder les deux usages séparés.

## Données personnelles clients

- Commande à table **sans compte** possible (minimisation).
- Comptes clients, fidélité, réservations : consentement marketing distinct, droit d'accès/
  suppression, durée de conservation définie.
- Cookies/traceurs : bannière conforme si analytics non exemptés.
- Hébergement des données dans une zone conforme (UE pour clients européens).

## Paiement

- Champs de carte hébergés par le prestataire → périmètre PCI-DSS minimal (SAQ A).
- Webhooks signés et vérifiés, idempotence des traitements.
- Caisse : certification anti-fraude TVA en France (NF525) — voir [06-paiement.md](06-paiement.md).

## Information du consommateur

- Allergènes (14 réglementaires) affichés pour chaque plat.
- Prix TTC, mention du service, origine des viandes (France), selon la réglementation locale.

## Sécurité applicative

- Authentification staff : e-mail + mot de passe + **2FA** pour les gérants ; code PIN rapide pour les serveurs sur appareils du restaurant.
- Rôles et permissions fins (un serveur ne peut pas rembourser, un cuisinier ne voit pas le CA).
- Isolation multi-tenant (Row Level Security PostgreSQL).
- Boîtier vision : authentification par clé d'appareil, mises à jour signées, pas de port ouvert vers Internet.
- Chiffrement en transit (TLS) et au repos, sauvegardes quotidiennes testées, journal d'audit.
- Limitation de débit sur les API publiques (anti-abus des QR).
- Tests d'intrusion avant mise en production.
