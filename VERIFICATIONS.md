# Vérifications (état actuel)

## Résumé rapide
- ✅ **OK** : endpoints Catalogue demandés sont en place.
- ✅ **OK** : validation des champs (`nom` non vide, `prix >= 0`, `quantite >= 0`).
- ✅ **OK** : stockage mémoire des produits.
- ✅ **OK** : intégration avec le Calculateur via le client (recalcul de la valeur totale).
- ✅ **OK** : tests unitaires/intégration ont été ajoutés.
- ⚠️ **PAS OK dans cet environnement** : exécution `mvn test` bloquée (accès Maven Central en `403 Forbidden`).

---

## Détail des vérifications

### 1) Catalogue
- ✅ `POST /catalogue/produits` : présent.
- ✅ `GET /catalogue/produits/{nom}` : présent.
- ✅ `GET /catalogue/produits` : présent.
- ✅ Validation des entrées : présente via annotations Bean Validation.

### 2) Calculateur
- ✅ `POST /calcul/valeur-stock` : présent.
- ✅ Calcul `prix * quantite` sur une liste JSON : présent.

### 3) ClientSOA
- ✅ Récupère la liste du Catalogue.
- ✅ Envoie la liste au Calculateur pour calculer la valeur totale.
- ✅ Peut ajouter un produit (si arguments fournis), puis recalculer.

### 4) Tests
- ✅ Tests ajoutés dans `CatalogueService/src/test/...`.
- ✅ Tests ajoutés dans `CalculateurService/src/test/...`.
- ⚠️ Exécution non validée ici à cause du réseau Maven (`403`).

---

# Déploiement sur ton infra

## Prérequis
- Java 17 installé.
- Maven 3.9+ installé.
- Ports ouverts:
  - `8081` (CatalogueService)
  - `8082` (CalculateurService)

## Option A — Déploiement simple (VM/serveur Linux)

### 1. Build des JAR
Depuis la racine du repo:

```bash
cd CatalogueService
mvn clean package -DskipTests

cd ../CalculateurService
mvn clean package -DskipTests
```

Tu obtiens:
- `CatalogueService/target/service-0.0.1-SNAPSHOT.jar`
- `CalculateurService/target/service-0.0.1-SNAPSHOT.jar`

### 2. Lancer les services
Dans deux terminaux (ou via `nohup`/systemd):

```bash
# Terminal 1
cd CatalogueService
java -jar target/service-0.0.1-SNAPSHOT.jar --server.port=8081

# Terminal 2
cd CalculateurService
java -jar target/service-0.0.1-SNAPSHOT.jar --server.port=8082
```

### 3. Vérifier rapidement

```bash
curl http://<HOST>:8081/catalogue/produits

curl -X POST http://<HOST>:8081/catalogue/produits \
  -H 'Content-Type: application/json' \
  -d '{"nom":"ProduitInfra","prix":12.5,"quantite":3}'

curl http://<HOST>:8081/catalogue/produits/ProduitInfra

curl -X POST http://<HOST>:8082/calcul/valeur-stock \
  -H 'Content-Type: application/json' \
  -d '[{"nom":"ProduitInfra","prix":12.5,"quantite":3}]'
```

## Option B — Démarrage en services systemd (recommandé en prod)
Créer 2 unités:
- `catalogue.service`
- `calculateur.service`

Exemple minimal (`/etc/systemd/system/catalogue.service`):

```ini
[Unit]
Description=Catalogue Service
After=network.target

[Service]
User=app
WorkingDirectory=/opt/tpsoa/CatalogueService
ExecStart=/usr/bin/java -jar /opt/tpsoa/CatalogueService/target/service-0.0.1-SNAPSHOT.jar --server.port=8081
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

Même principe pour `calculateur.service` avec port `8082`.

Puis:

```bash
sudo systemctl daemon-reload
sudo systemctl enable --now catalogue
sudo systemctl enable --now calculateur
sudo systemctl status catalogue
sudo systemctl status calculateur
```

## Notes importantes pour ton infra
- Si ton infra bloque Maven Central (comme ici), configure un mirror (Nexus/Artifactory) dans `~/.m2/settings.xml`.
- En prod, ajoute un reverse proxy (Nginx/Traefik) + TLS.
- Pense à déplacer les ports/URLs dans des variables d’environnement si besoin.
