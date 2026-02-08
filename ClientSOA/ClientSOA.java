import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientSOA {
    public static void main(String[] args) throws Exception {
        var client = HttpClient.newHttpClient();

        System.out.println("Valeur initiale du stock : " + calculerValeurTotale(client) + " €");

        if (args.length == 3) {
            ajouterProduit(client, args[0], Double.parseDouble(args[1]), Integer.parseInt(args[2]));
            System.out.println("Valeur du stock après ajout : " + calculerValeurTotale(client) + " €");
        }
    }

    private static double calculerValeurTotale(HttpClient client) throws Exception {
        var reqCatalogue = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8081/catalogue/produits"))
            .GET()
            .build();
        var resCatalogue = client.send(reqCatalogue, HttpResponse.BodyHandlers.ofString());

        var reqCalcul = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8082/calcul/valeur-stock"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(resCatalogue.body()))
            .build();
        var resCalcul = client.send(reqCalcul, HttpResponse.BodyHandlers.ofString());
        return Double.parseDouble(resCalcul.body());
    }

    private static void ajouterProduit(HttpClient client, String nom, double prix, int quantite) throws Exception {
        String jsonBody = String.format("{\"nom\":\"%s\",\"prix\":%s,\"quantite\":%s}", nom, prix, quantite);
        var reqAjout = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8081/catalogue/produits"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        var resAjout = client.send(reqAjout, HttpResponse.BodyHandlers.ofString());
        if (resAjout.statusCode() >= 400) {
            throw new IllegalStateException("Échec d'ajout du produit: " + resAjout.body());
        }
    }
}
