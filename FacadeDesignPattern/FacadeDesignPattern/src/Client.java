import facade.WeatherFacade;

import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        WeatherFacade weatherFacade = new WeatherFacade();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the latitude: ");
        double lat = scanner.nextDouble();
        System.out.print("Enter the longitude: ");
        double lon = scanner.nextDouble();

        Map<String, String> weatherData = weatherFacade.getWeather(lat, lon);

        for (Map.Entry<String, String> entry : weatherData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
