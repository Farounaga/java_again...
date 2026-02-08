package com.tpsoa.catalogue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record Produit(
    @NotBlank(message = "Le nom est obligatoire") String nom,
    @NotNull(message = "Le prix est obligatoire") @PositiveOrZero(message = "Le prix doit être >= 0") Double prix,
    @NotNull(message = "La quantité est obligatoire") @PositiveOrZero(message = "La quantité doit être >= 0") Integer quantite
) {
}
