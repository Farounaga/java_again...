
package com.tpsoa.catalogue;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/catalogue")
public class CatalogueController {
    @GetMapping("/produits")
    public List<Map<String, Object>> getProduits() {
        return List.of(
            Map.of("nom", "Château Exemple", "prix", 15.5, "quantite", 12),
            Map.of("nom", "Domaine Démo", "prix", 22.0, "quantite", 6)
        );
    }
}
