# 06 — Paiement par smartphone (marché marocain)

## Objectif

Le client paie depuis sa table, quand il veut, sans attendre l'addition ni le TPE.
Au Maroc, **les espèces restent très utilisées** : le produit doit être **hybride**
(paiement mobile + carte + espèces saisies en caisse) et ne jamais bloquer un client qui paie en liquide.

## Contraintes du marché marocain

- **Stripe n'accepte pas les commerçants établis au Maroc** → il faut une passerelle locale.
- Les paiements par carte en ligne passent en pratique par le **CMI (Centre Monétique Interbancaire)**
  ou par des passerelles/PSP agréés qui s'appuient sur lui (ex. Payzone, autres PSP agréés Bank Al-Maghrib ❓ à comparer).
- **Paiement mobile interopérable** (wallets m-wallet agréés Bank Al-Maghrib : CIH, Attijari, Orange Money,
  inwi money, Barid Cash…) avec **paiement marchand par QR code** : très pertinent pour un produit scanné à table.
- **Apple Pay / Google Pay** : disponibilité selon la banque émettrice et la passerelle → ❓ à vérifier
  auprès du CMI/PSP retenu ; à prévoir dans l'architecture, sans en faire une dépendance du MVP.
- Devise : **MAD** (dirhams), montants en centimes entiers.

## Point clé pour un SaaS : qui encaisse l'argent ?

Si notre plateforme encaissait l'argent des clients puis le reversait aux restaurants, elle deviendrait
un **intermédiaire de paiement**, activité réglementée nécessitant un agrément de **Bank Al-Maghrib**.

➡️ **Choix recommandé** : chaque restaurant signe **son propre contrat commerçant** (CMI / PSP) ;
notre SaaS se contente d'intégrer la passerelle avec les identifiants du restaurant (stockés chiffrés).
L'argent va directement sur le compte du restaurant. Nous facturons notre abonnement séparément.

On peut accompagner l'ouverture du contrat (partenariat commercial avec un PSP = argument de vente
et éventuellement commission d'apport ❓).

## Couche d'abstraction

Le code passe par une interface `PaymentProvider` (`createPayment`, `handleWebhook`, `refund`, `getStatus`)
avec un adaptateur par passerelle :

| Adaptateur | Phase |
|------------|-------|
| CMI (paiement carte en ligne, redirection/3-D Secure) | MVP |
| PSP local (Payzone ou équivalent, selon négociation) | MVP ou v1.1 |
| Wallets mobiles / QR marchand interopérable | v1.1 |
| TPE physique connecté (si le PSP propose une API) | v2 |
| Stripe / Adyen (expansion Europe/Afrique) | plus tard |

## Fonctionnalités

1. **Payer l'addition de la table** (même total en temps réel sur tous les téléphones).
2. **Partager l'addition** : à parts égales, par articles, ou montant libre ; le reste à payer se met à jour pour tous.
3. **Pourboire** (boutons ou montant libre), répartition configurable entre serveurs.
4. **Espèces / TPE classique** : le serveur encaisse dans l'app staff ou la caisse → la note se solde.
5. **Prépaiement** pour l'emporter/la livraison (ou **paiement à la livraison** en espèces, très courant au Maroc).
6. **Reçu / facture** conforme (voir [12-caisse.md](12-caisse.md)) affiché, envoyé par e-mail ou **WhatsApp**.
7. **Remboursement** total/partiel (droits responsables uniquement).
8. **Rapprochement** : chaque paiement relié à la commande, la table, le serveur ; clôture journalière ; export comptable.

## Flux technique (simplifié)

```
Client ─► API : « payer 235,00 MAD sur la note N »
API    ─► passerelle du restaurant (identifiants du tenant) : création de la transaction
Client ─► page de paiement sécurisée de la passerelle (3-D Secure)  /  scan QR wallet
Passerelle ─► callback serveur signé ─► API : paiement confirmé ─► note mise à jour ─► WebSocket à la table
```

La **notification serveur (callback)** fait foi, jamais la redirection côté client. Gérer : double paiement,
échec, abandon, paiements simultanés sur la même note (montant réservé), et une tâche de **réconciliation**
qui interroge la passerelle pour les transactions restées « en attente ».

## Sécurité

Les données de carte ne transitent jamais par nos serveurs (page hébergée par la passerelle) → périmètre PCI-DSS minimal.

## Critères d'acceptation

- [ ] Paiement carte via CMI de bout en bout en environnement de test, puis en production sur le restaurant pilote.
- [ ] 4 personnes paient leur part simultanément sans incohérence du reste à payer.
- [ ] Un paiement confirmé par callback alors que le client a fermé la page est bien enregistré.
- [ ] Une table réglée en espèces se clôture en 2 gestes dans l'app staff.
