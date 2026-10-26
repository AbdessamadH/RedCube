# 04 — Gestion de stock

## Objectif

Savoir à tout moment ce qu'il reste, ce que coûte chaque plat, quoi commander et ce qui est gaspillé.

## Concepts

- **Article de stock** (ingrédient) : nom, unité (kg, L, pièce), unité d'achat et facteur
  de conversion (ex. 1 carton = 12 bouteilles), coût moyen pondéré, seuil d'alerte,
  stock cible, zone (chambre froide, réserve sèche, bar), allergènes.
- **Lot** : quantité, date de réception, **DLC/DDM**, fournisseur, n° de lot (traçabilité).
- **Fiche technique (recette)** : liste d'ingrédients et quantités par portion,
  taux de perte, sous-recettes (sauce maison, fond…). Les **options** d'un plat ont
  leur propre impact stock (ex. « supplément fromage » = +30 g).
- **Mouvement de stock** : entrée (réception), sortie (vente), perte/casse, transfert,
  ajustement d'inventaire — chaque mouvement est horodaté et attribué à un utilisateur.

## Fonctionnalités

1. **Déstockage automatique** : chaque plat validé en cuisine décrémente ses ingrédients
   selon la fiche technique.
2. **Alertes** : seuil bas, rupture imminente (prévision sur la consommation moyenne),
   DLC proche (J-2), écart d'inventaire anormal.
3. **Rupture → carte** : si un ingrédient est à zéro, les plats concernés passent
   automatiquement « indisponible » sur le menu client et la caisse.
4. **Réception** : saisie du bon de livraison (ou photo de la facture → lecture OCR/IA),
   contrôle des quantités vs bon de commande, contrôle température (HACCP).
5. **Bons de commande fournisseurs** : proposition automatique (stock cible − stock actuel
   − commandes en cours), validation par le gérant, envoi par e-mail/PDF.
6. **Inventaire** : mode mobile pour compter rapidement par zone (scan code-barres possible),
   calcul des écarts théorique/réel.
7. **Pertes** : déclaration rapide (périmé, raté, casse, repas du personnel) avec motif.
8. **Coût matière** : coût de revient par plat, **ratio food cost** (%), marge par plat,
   alerte si le prix d'un ingrédient augmente.
9. **Multi-zones / multi-sites** (plus tard) : transferts entre cuisine centrale et restaurants.

## Rapports

- Valorisation du stock (FIFO ou coût moyen pondéré ❓).
- Consommation théorique vs réelle (révèle vols, sur-portions, oublis de saisie).
- Top pertes, top ingrédients en valeur, évolution des prix fournisseurs.

## Critères d'acceptation

- [ ] Vente d'un plat → stock des ingrédients mis à jour en < 5 s.
- [ ] Un ingrédient à 0 rend indisponible tous ses plats sur toutes les interfaces.
- [ ] Bon de commande généré en 1 clic à partir des alertes.
- [ ] Un inventaire complet d'une réserve de 150 articles faisable en < 30 min sur mobile.
