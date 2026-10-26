# 07 — Fonctions additionnelles (proposition pour le point « 6. autre »)

Classées par **valeur / effort**. ⭐ = recommandé dès la v1 ou v1.5.

## Pour le client

| Fonction | Description | Valeur | Effort |
|----------|-------------|--------|--------|
| ⭐ **Bouton « Appeler le serveur »** | Depuis le menu : appeler, demander l'addition, de l'eau, du pain… → notification ciblée au serveur de la zone | Haute | Faible |
| ⭐ **Filtre allergènes & régimes** | Le client coche ses allergies : la carte masque/avertit. Obligatoire d'informer sur les 14 allergènes | Haute | Faible |
| ⭐ **Réservations en ligne** | Créneaux, taille de groupe, acompte/empreinte bancaire anti no-show, rappels SMS ; reliées au plan de salle | Haute | Moyen |
| **Liste d'attente virtuelle** | Le client s'inscrit en arrivant, reçoit un SMS quand une table devient verte (lien direct avec la caméra !) | Haute | Faible |
| **Fidélité & CRM** | Points, récompenses, offre anniversaire, historique des commandes, « recommander mon plat habituel » | Moyenne | Moyen |
| **Avis après repas** | Note rapide après paiement ; les bonnes notes sont invitées à publier sur Google | Moyenne | Faible |
| **Commande & réservation via WhatsApp** | Le client écrit au numéro du restaurant ; un assistant prend la commande/réservation et l'envoie au KDS | Haute (Maroc) | Moyen |
| **Recommandations / upsell** | « Ce plat se marie avec… », dessert proposé en fin de repas | Moyenne | Faible |
| **Assistant conversationnel** | Le client pose des questions sur la carte (« qu'est-ce qui est épicé et sans gluten ? ») en langage naturel, dans sa langue | Moyenne | Moyen |
| **Cartes cadeaux** | Achat en ligne, utilisables au restaurant | Faible | Faible |

## Pour l'équipe

| Fonction | Description | Valeur | Effort |
|----------|-------------|--------|--------|
| ⭐ **Plan de salle live** | Vue unique : état caméra + commandes + temps d'attente + serveur assigné | Haute | Moyen |
| ⭐ **Tableau de bord gérant** | CA en direct, ticket moyen, plats stars/poids morts (matrice menu engineering), food cost, temps de service | Haute | Moyen |
| **Planning & pointage du personnel** | Planning hebdo, pointage, heures sup., répartition des pourboires | Moyenne | Moyen |
| **Prévision d'affluence & de ventes (IA)** | Selon historique, météo, jour, événements → aide au planning et aux commandes fournisseurs | Haute | Élevé |
| **HACCP digital** (référentiel ONSSA au Maroc) | Relevés de températures (capteurs IoT dans les frigos avec alerte), plan de nettoyage, traçabilité des lots | Haute (légal) | Moyen |
| **Lecture des factures fournisseurs (OCR/IA)** | Photo de facture → réception de stock et mise à jour des prix automatiques | Moyenne | Moyen |
| **Multi-établissements** | Groupe de restaurants, cuisine centrale, cartes et prix par site | Selon ambition | Élevé |
| **Écran de retrait** | Écran « commande n° 42 prête » pour l'emporter | Faible | Faible |

## Ma recommandation

Ajouter dès la v1 : **appel serveur, allergènes, plan de salle live, tableau de bord**.
En v2 : **réservations + liste d'attente** (elles exploitent directement la détection de
tables, c'est un vrai différenciateur), puis **HACCP** et **prévisions IA**.
