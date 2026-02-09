package com.tpsoa.calculateur;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCalculateStockValueFromJsonNumbers() throws Exception {
        String payload = "[{\"nom\":\"A\",\"prix\":15.5,\"quantite\":2},{\"nom\":\"B\",\"prix\":3,\"quantite\":4}]";

        mockMvc.perform(post("/calcul/valeur-stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(content().string("43.0"));
    }
}
