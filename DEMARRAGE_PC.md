# Démarrage du projet sur PC (local)

Ce guide explique uniquement comment lancer le projet **en local sur ton ordinateur** après téléchargement des fichiers.

## 1) Prérequis
- Java 17 installé
- Maven 3.9+ installé
- Un terminal (PowerShell, CMD, bash, etc.)

Vérification rapide :
```bash
java -version
mvn -version
```

---

## 2) Ouvrir le projet
Décompresse/télécharge le projet puis place-toi à la racine (dossier qui contient `CatalogueService`, `CalculateurService`, `ClientSOA`).

---

## 3) Lancer CatalogueService (port 8081)
Dans un terminal :
```bash
cd CatalogueService
mvn spring-boot:run
```

Le service sera accessible sur :
- `http://localhost:8081/catalogue/produits`

---

## 4) Lancer CalculateurService (port 8082)
Dans un **deuxième** terminal :
```bash
cd CalculateurService
mvn spring-boot:run
```

Le service sera accessible sur :
- `http://localhost:8082/calcul/valeur-stock`

---

## 5) Tester rapidement les endpoints

### 5.1 Lire les produits
```bash
curl http://localhost:8081/catalogue/produits
```

### 5.2 Ajouter un produit
```bash
curl -X POST http://localhost:8081/catalogue/produits \
  -H "Content-Type: application/json" \
  -d '{"nom":"MonProduit","prix":10.5,"quantite":4}'
```

### 5.3 Lire un produit par nom
```bash
curl http://localhost:8081/catalogue/produits/MonProduit
```

### 5.4 Calculer la valeur du stock
```bash
curl -X POST http://localhost:8082/calcul/valeur-stock \
  -H "Content-Type: application/json" \
  -d '[{"nom":"MonProduit","prix":10.5,"quantite":4}]'
```

---

## 6) Exécuter le client Java
Dans un troisième terminal :
```bash
cd ClientSOA
javac ClientSOA.java
java ClientSOA
```

Pour tester l’ajout + recalcul en une commande :
```bash
java ClientSOA ProduitAjoute 12.5 3
```

---

## 7) Problèmes fréquents
- **Port déjà utilisé** : fermer le processus qui utilise 8081/8082 ou changer le port de lancement.
- **Erreur Maven (réseau)** : vérifier l’accès internet/proxy ou configurer un mirror Maven.
- **`curl` absent sous Windows** : utiliser Postman ou PowerShell (`Invoke-RestMethod`).
