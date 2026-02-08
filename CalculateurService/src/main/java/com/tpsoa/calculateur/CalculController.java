package com.tpsoa.calculateur;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/calcul")
public class CalculController {
    @PostMapping("/valeur-stock")
    public double calculerValeur(@RequestBody List<Map<String, Object>> produits) {
        return produits.stream()
            .mapToDouble(p -> {
                Number prix = (Number) p.get("prix");
                Number quantite = (Number) p.get("quantite");
                return prix.doubleValue() * quantite.intValue();
            })
            .sum();
    }
}
