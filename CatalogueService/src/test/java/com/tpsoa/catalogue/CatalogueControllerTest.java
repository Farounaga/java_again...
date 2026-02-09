package com.tpsoa.catalogue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndFindProduct() throws Exception {
        String nom = "ProduitTest-" + UUID.randomUUID();
        String payload = String.format("{\"nom\":\"%s\",\"prix\":10.0,\"quantite\":2}", nom);

        mockMvc.perform(post("/catalogue/produits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nom").value(nom));

        mockMvc.perform(get("/catalogue/produits/{nom}", nom))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.prix").value(10.0))
            .andExpect(jsonPath("$.quantite").value(2));
    }

    @Test
    void shouldReturnBadRequestWhenProductInvalid() throws Exception {
        mockMvc.perform(post("/catalogue/produits")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"\",\"prix\":-1,\"quantite\":-2}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenProductUnknown() throws Exception {
        mockMvc.perform(get("/catalogue/produits/{nom}", "introuvable"))
            .andExpect(status().isNotFound());
    }
}
