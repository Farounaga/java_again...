package com.tpsoa.catalogue;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CatalogueService {
    private final List<Produit> produits = new CopyOnWriteArrayList<>(List.of(
        new Produit("Château Exemple", 15.5, 12),
        new Produit("Domaine Démo", 22.0, 6)
    ));

    public List<Produit> getProduits() {
        return new ArrayList<>(produits);
    }

    public Optional<Produit> getProduitByNom(String nom) {
        return produits.stream()
            .filter(p -> p.nom().equalsIgnoreCase(nom))
            .findFirst();
    }

    public Produit addProduit(Produit produit) {
        produits.add(produit);
        return produit;
    }
}
