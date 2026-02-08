
# TP SOA avec Spring Boot

## Structure
- CatalogueService (port 8081)
- CalculateurService (port 8082)
- ClientSOA (Java simple)

## Étapes
1. Dans chaque dossier de service :
   ```bash
   mvn spring-boot:run
   ```
2. Exécuter ClientSOA avec Java 17 :
   ```bash
   javac ClientSOA.java && java ClientSOA
   ```

## Test
- GET http://localhost:8081/catalogue/produits
- POST http://localhost:8082/calcul/valeur-stock
