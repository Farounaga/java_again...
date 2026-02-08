
package com.tpsoa.calculateur;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/calcul")
public class CalculController {
    @PostMapping("/valeur-stock")
    public double calculerValeur(@RequestBody List<Map<String, Object>> produits) {
        return produits.stream()
            .mapToDouble(p -> ((Double)p.get("prix")) * ((Integer)p.get("quantite")))
            .sum();
    }
}
