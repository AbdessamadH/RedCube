# 09 — Sécurité, légal, protection des données (Maroc)

> Ceci n'est pas un avis juridique : faire valider par un avocat marocain spécialisé (données personnelles,
> fiscalité, paiement) avant la commercialisation.

## Cadre applicable

- **Loi 09-08** relative à la protection des personnes physiques à l'égard du traitement des données
  à caractère personnel, contrôlée par la **CNDP**.
- **Code du travail** (information des salariés pour tout dispositif qui les concerne).
- Réglementation **Bank Al-Maghrib** pour le paiement (voir [06-paiement.md](06-paiement.md)).
- **Code général des impôts / DGI** pour la facturation (voir [12-caisse.md](12-caisse.md)).
- Loi **31-08** sur la protection du consommateur (information sur les prix, etc.).

## Rôles : qui est responsable de quoi ?

Pour un SaaS, il faut clarifier dans les contrats :

| Traitement | Responsable de traitement | Notre rôle |
|------------|---------------------------|------------|
| Données des clients du restaurant (commandes, réservations, fidélité) | **Le restaurant** | Sous-traitant |
| Analyse vidéo des tables | **Le restaurant** | Sous-traitant (fournisseur de la solution) |
| Comptes des restaurateurs, facturation de l'abonnement | **Nous** | Responsable |

→ Prévoir un **contrat de sous-traitance (DPA)** annexé aux CGV, et un **kit de conformité** livré à chaque
restaurant (modèles de déclaration CNDP, panneaux, notes d'information du personnel). C'est aussi un argument commercial.

## Caméras (point le plus sensible)

- La vidéosurveillance relève d'une **demande d'autorisation / déclaration préalable auprès de la CNDP**,
  faite par le restaurant (que nous aidons à préparer). ❓ confirmer la procédure exacte et les délais.
- **Finalité limitée** : « optimisation du service en salle » ; pas de surveillance des salariés ni de reconnaissance faciale.
- **Minimisation** : traitement local sur le boîtier, aucune image enregistrée ni envoyée par défaut ; seuls les états de tables sortent.
- **Information** : panneaux visibles à l'entrée et en salle, information écrite du personnel.
- Caméras orientées sur les tables uniquement (jamais toilettes, vestiaires, zones de pause).
- Images d'entraînement du modèle : collecte ponctuelle, visages floutés, accord écrit du restaurant, durée limitée.

## Hébergement et transfert de données

- Le **transfert de données personnelles hors du Maroc** est encadré et peut nécessiter une autorisation de la CNDP.
- Options :
  1. **Hébergement au Maroc** (datacenters locaux : opérateurs télécom, acteurs cloud marocains) — le plus simple juridiquement ;
  2. Cloud international avec autorisation de transfert CNDP.
- ➡️ Recommandation : architecture **portable** (conteneurs Docker, PostgreSQL standard, stockage S3 compatible)
  pour pouvoir héberger au Maroc dès le départ et changer d'hébergeur sans refonte. ❓ comparer les offres.
- Notre propre déclaration CNDP en tant qu'éditeur pour les traitements dont nous sommes responsables.

## Données clients

- Commande à table **sans compte** possible (minimisation).
- Consentement explicite et séparé pour le marketing (SMS, WhatsApp, e-mail).
- Droit d'accès, de rectification, d'opposition ; durées de conservation définies.

## Information du consommateur

- Prix TTC en dirhams, affichage clair des suppléments et frais de livraison.
- Allergènes et composition des plats (bonne pratique, et nécessaire pour l'export vers l'Europe plus tard).

## Sécurité applicative

- Authentification restaurateurs : e-mail/téléphone + mot de passe + **2FA** ; code PIN rapide pour les serveurs sur appareils du restaurant.
- Rôles et permissions fins (un serveur ne peut pas rembourser, un cuisinier ne voit pas le CA).
- **Isolation stricte entre restaurants** (Row Level Security PostgreSQL + tests automatisés de non-fuite entre tenants).
- Identifiants de passerelle de paiement des restaurants **chiffrés** (KMS / coffre de secrets).
- Boîtier vision : clé d'appareil, mises à jour signées, aucun port ouvert vers Internet.
- TLS partout, chiffrement au repos, sauvegardes quotidiennes testées, journal d'audit.
- Limitation de débit sur les API publiques (anti-abus des QR).
- Test d'intrusion avant le lancement commercial.

## Expansion future (Europe)

Si le produit est vendu en France plus tard : RGPD, CNIL, et **certification anti-fraude de caisse (NF525)** obligatoire.
Concevoir dès maintenant le journal de caisse inaltérable (voir doc 12) facilitera cette certification.
