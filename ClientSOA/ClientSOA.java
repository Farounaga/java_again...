
import java.net.http.*;
import java.net.*;

public class ClientSOA {
    public static void main(String[] args) throws Exception {
        var client = HttpClient.newHttpClient();

        // Appel du service Catalogue
        var reqCatalogue = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8081/catalogue/produits"))
            .GET().build();
        var resCatalogue = client.send(reqCatalogue, HttpResponse.BodyHandlers.ofString());
        String produitsJson = resCatalogue.body();

        // Appel du service Calculateur
        var reqCalcul = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8082/calcul/valeur-stock"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(produitsJson))
            .build();
        var resCalcul = client.send(reqCalcul, HttpResponse.BodyHandlers.ofString());

        System.out.println("Valeur totale du stock : " + resCalcul.body() + " €");
    }
}
