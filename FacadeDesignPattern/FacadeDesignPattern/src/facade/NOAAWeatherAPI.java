package facade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class NOAAWeatherAPI {
    public JSONObject getGridPoint(double lat, double lon) throws IOException {
        String urlString = String.format("https://api.weather.gov/points/%f,%f", lat, lon);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner sc = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();
        while (sc.hasNext()) {
            inline.append(sc.nextLine());
        }
        sc.close();

        return new JSONObject(inline.toString());
    }

    public JSONObject getForecast(String gridId, int gridX, int gridY) throws IOException {
        String urlString = String.format("https://api.weather.gov/gridpoints/%s/%d,%d/forecast", gridId, gridX, gridY);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner sc = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();
        while (sc.hasNext()) {
            inline.append(sc.nextLine());
        }
        sc.close();

        return new JSONObject(inline.toString());
    }
}
