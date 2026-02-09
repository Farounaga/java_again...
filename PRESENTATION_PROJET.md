# Présentation du projet TP SOA

## 1) Objectif
Ce projet illustre une architecture orientée services (SOA) simple avec :
- un **service Catalogue** pour exposer et gérer les produits,
- un **service Calculateur** pour calculer la valeur totale du stock,
- un **client Java** qui orchestre les appels entre les deux.

---

## 2) Composants

### CatalogueService (port 8081)
Rôle : gérer les produits.

Fonctionnalités :
- `GET /catalogue/produits` : retourne la liste des produits.
- `GET /catalogue/produits/{nom}` : retourne un produit par son nom.
- `POST /catalogue/produits` : ajoute un produit.

Validation des entrées :
- `nom` non vide,
- `prix >= 0`,
- `quantite >= 0`.

Stockage :
- en mémoire (liste thread-safe), avec des produits initiaux de démonstration.

### CalculateurService (port 8082)
Rôle : calculer la valeur globale d’un stock.

Fonctionnalité :
- `POST /calcul/valeur-stock` : reçoit une liste de produits JSON et retourne la somme `prix * quantite`.

### ClientSOA
Rôle : démontrer l’intégration entre services.

Comportement :
- lit la liste depuis le Catalogue,
- appelle le Calculateur pour obtenir la valeur totale,
- peut ajouter un produit (si arguments passés), puis recalculer la valeur.

---

## 3) Ce qui a été implémenté

- Gestion des produits côté Catalogue avec endpoints de lecture/ajout.
- Validation des données côté API.
- Intégration client → catalogue + calculateur pour recalculer la valeur.
- Tests ajoutés pour :
  - Catalogue (création, validation, not found),
  - Calculateur (calcul du total).

---

## 4) Bénéfices de la solution

- **Séparation des responsabilités** : chaque service a un rôle clair.
- **Évolutivité** : le Catalogue et le Calculateur peuvent évoluer séparément.
- **Testabilité** : endpoints clés couverts par des tests.
- **Lisibilité** : architecture simple et facile à présenter.

---

## 5) Limites actuelles

- Données en mémoire (pas de persistance durable).
- Pas d’authentification / autorisation.
- Dépendance à Maven Central pour build/tests (si réseau restreint, prévoir un mirror Maven interne).

---

## 6) Pistes d’amélioration

- Passer à une persistance H2 / PostgreSQL.
- Ajouter gestion d’erreurs standardisée (format d’erreur API).
- Dockeriser les services.
- Ajouter CI/CD (build + tests automatiques).
