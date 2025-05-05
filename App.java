import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class App{
    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static void main(String[] args) {
        App obj = new App();

        System.out.println("testing send GET");
        try {
            obj.sendGet();
        } catch (Exception e) {
            System.out.println("Error during GET request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendGet() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://api.github.com"))
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());
    }
}