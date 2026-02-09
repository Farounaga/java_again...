package com.tpsoa.catalogue;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogue")
public class CatalogueController {
    private final CatalogueService catalogueService;

    public CatalogueController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping("/produits")
    public List<Produit> getProduits() {
        return catalogueService.getProduits();
    }

    @GetMapping("/produits/{nom}")
    public ResponseEntity<Produit> getProduitByNom(@PathVariable String nom) {
        return catalogueService.getProduitByNom(nom)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/produits")
    public ResponseEntity<Produit> addProduit(@Valid @RequestBody Produit produit) {
        Produit savedProduit = catalogueService.addProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
    }
}
