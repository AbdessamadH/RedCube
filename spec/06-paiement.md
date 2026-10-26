# 06 — Paiement par smartphone

## Objectif

Le client paie depuis sa table, quand il veut, sans attendre l'addition ni le TPE.

## Moyens de paiement

- Carte bancaire, **Apple Pay**, **Google Pay** (via le prestataire).
- Selon le pays ❓ : wallets locaux (ex. au Maroc : CMI, paiement mobile ; en Europe : Bancontact,
  iDEAL, titres-restaurant dématérialisés type Swile/Edenred/Pluxee).
- Espèces / TPE classique toujours possibles, saisis par le serveur pour clôturer la table.

## Prestataire

Le code passe par une **couche d'abstraction** `PaymentProvider` pour pouvoir changer de prestataire.

| Option | Pour |
|--------|------|
| **Stripe** | API excellente, Apple/Google Pay, Terminal (TPE physique), Connect (multi-restaurants) |
| Adyen | Gros volumes, multi-pays |
| CMI / prestataire local | Obligatoire ou plus avantageux selon le pays ❓ |

Les données de carte **ne transitent jamais** par nos serveurs (champs hébergés par le
prestataire) → conformité PCI-DSS simplifiée (niveau SAQ A).

## Fonctionnalités

1. **Payer l'addition de la table** (tout le monde voit le même total en temps réel).
2. **Partager l'addition** :
   - à parts égales (« je paie 1/4 ») ;
   - par articles (« je paie mon burger et ma boisson ») ;
   - montant libre.
   Le reste à payer se met à jour en direct pour tous les téléphones de la table.
3. **Pourboire** : boutons 0 / 5 / 10 / 15 % ou montant libre, répartition configurable.
4. **Prépaiement** pour emporter/livraison (et optionnellement à table, mode « fast-food »).
5. **Reçu** : affiché + envoyé par e-mail ; facture avec raison sociale à la demande.
6. **Remboursement** total/partiel depuis le back-office (droits responsables uniquement).
7. **Rapprochement** : chaque paiement est relié à la commande, la table, le serveur ;
   export comptable et Z de caisse journalier.

## Flux technique (simplifié)

```
Client ─► API : « payer 23,50 € sur la note N »  ─► API crée un PaymentIntent (prestataire)
Client ─► formulaire hébergé / Apple Pay ─► prestataire
Prestataire ─► webhook ─► API : paiement confirmé ─► note mise à jour ─► WebSocket à la table
```

Le **webhook** fait foi (jamais la redirection côté client). Gestion des cas : paiement
en double, échec, 3-D Secure, paiement simultané de deux clients sur la même note
(verrou / montant réservé).

## Point légal important : la caisse

En **France**, un logiciel qui enregistre des encaissements doit être **certifié anti-fraude
TVA (NF525 ou équivalent)** : inaltérabilité, sécurisation, conservation, archivage.
Deux stratégies :

- **A (recommandée pour démarrer)** : s'interfacer avec une **caisse certifiée existante**
  (Lightspeed, Zelty, Tiller, L'Addition…) — notre app gère menu/commande/paiement en ligne et
  pousse les ventes dans la caisse.
- **B** : devenir soi-même caisse certifiée (long et coûteux, à envisager plus tard).

Dans d'autres pays (ex. Maroc), vérifier les obligations de facturation électronique locales. ❓

## Critères d'acceptation

- [ ] Paiement Apple Pay / Google Pay en ≤ 3 interactions après « Payer ».
- [ ] 4 personnes paient leur part simultanément sans incohérence du reste à payer.
- [ ] Un paiement confirmé par webhook mais dont le client a fermé la page est bien enregistré.
