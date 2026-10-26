# 12 — Caisse (POS) et facturation

## Pourquoi ce module

Les restaurants ciblés n'ont pas forcément de caisse, et nous ne nous appuyons sur aucune caisse existante :
**la caisse fait donc partie du produit**. C'est aussi ce qui rend le SaaS indispensable au quotidien
(un restaurant ne change pas facilement de caisse → forte rétention).

## Fonctionnalités

- **Prise de commande au comptoir / en salle** sur tablette ou smartphone (même catalogue que le menu QR).
- **Plan de salle** : ouvrir, transférer, fusionner, scinder une table.
- **Encaissement multi-moyens** sur une même note : espèces (rendu monnaie), carte via TPE, paiement en ligne, wallet,
  ticket/bon, crédit client (« ardoise » pour clients habitués ❓).
- Remises (%, montant, offert), avec motif et droit selon le rôle.
- **Ouverture / fermeture de caisse** : fond de caisse, comptage, écart, **rapport Z** journalier.
- **Impression** : ticket client et tickets cuisine (imprimantes thermiques ESC/POS en réseau/Bluetooth),
  tiroir-caisse piloté par l'imprimante.
- **Mode hors ligne** : la caisse continue d'encaisser sans Internet et resynchronise ensuite.
- Écran client optionnel (affichage du total).

## Facturation conforme (Maroc)

Mentions à faire figurer sur les factures (à valider avec un expert-comptable ❓) :

- raison sociale, adresse, **ICE** (Identifiant Commun de l'Entreprise), **IF** (identifiant fiscal), **RC**, **patente/TP** ;
- numéro de facture séquentiel sans trou, date ;
- détail des lignes, **montant HT, TVA par taux, TTC** (taux TVA restauration ❓ à confirmer, paramétrable par produit) ;
- ICE du client si c'est une entreprise qui demande une facture.

Les taux et mentions sont **paramétrables** dans le back-office (pas codés en dur).

**Veille réglementaire** : la DGI prépare la **facturation électronique** ; l'architecture doit permettre
d'ajouter un connecteur de transmission des factures à l'administration sans refonte.

## Journal inaltérable (bonne pratique, prépare l'avenir)

- Chaque vente, annulation, remise et remboursement est écrit dans un **journal append-only chaîné**
  (chaque enregistrement contient l'empreinte du précédent) : toute modification devient détectable.
- Aucune suppression de ticket validé : une erreur se corrige par un ticket d'annulation.
- Archivage périodique signé.

Cela protège le restaurateur (contrôle fiscal, fraude interne) et facilite une future certification NF525 (France).

## Matériel recommandé (kit vendu ou conseillé)

| Élément | Remarque |
|---------|----------|
| Tablette Android 10" | La caisse + KDS ; Android est moins cher et répandu au Maroc |
| Imprimante thermique 80 mm réseau | Ticket client / cuisine |
| Tiroir-caisse | Relié à l'imprimante |
| TPE | Fourni par la banque / le CMI du restaurant |
| Routeur 4G de secours | Continuité si l'ADSL/fibre coupe |

## Critères d'acceptation

- [ ] Encaisser une table en espèces + carte (paiement mixte) en < 30 s.
- [ ] 2 h sans Internet : ventes enregistrées, aucune perte, synchronisation correcte au retour.
- [ ] Rapport Z cohérent au centime avec la somme des paiements de la journée.
- [ ] Toute tentative de modification d'un ticket validé est détectée par la vérification du journal.
