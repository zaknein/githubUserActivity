package com.zaknein.githubUserActivity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class App{
    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static void main(String[] args) {
        App obj = new App();

        System.out.println("testing send GET");
        String username = args[0];
        try {
            obj.sendGet(username);
        } catch (Exception e) {
            System.out.println("Error during GET request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendGet(String username) throws Exception{
        
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://api.github.com/users/" + username + "/events"))
            .setHeader("User-Agent", "App")
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        if(response.statusCode() == 200){
            String responsebody = response.body();

            JSONArray events = new JSONArray(responsebody);

             // Verificar si hay eventos
            if (events.length() == 0) {
                System.out.println("No events found for user " + username);
            } else {
                System.out.println("Events found for user " + username + ":");
                
                // Iterar sobre cada evento
                for (int i = 0; i < events.length(); i++) {
                    JSONObject event = events.getJSONObject(i);
                    
                    // Mostrar detalles básicos del evento
                    String eventType = event.getString("type"); // Tipo de evento (push, create, etc.)
                    String repoName = event.getJSONObject("repo").getString("name"); // Nombre del repositorio
                    String createdAt = event.getString("created_at"); // Fecha del evento
                    
                    System.out.println("Event Type: " + eventType);
                    System.out.println("Repository: " + repoName);
                    System.out.println("Created At: " + createdAt);
                    System.out.println("--------------------------------------");
                }
            }
        } else {
            System.out.println("Request failed. Status Code: " + response.statusCode());
        
        } 
        // print response body
        System.out.println(response.body());
    }
}