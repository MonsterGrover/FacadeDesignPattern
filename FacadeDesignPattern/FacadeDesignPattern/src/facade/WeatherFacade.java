package facade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WeatherFacade {
    private NOAAWeatherAPI api;
    private LoggingAspect logger;
    private ErrorHandlingAspect errorHandler;

    public WeatherFacade() {
        api = new NOAAWeatherAPI();
        logger = new LoggingAspect();
        errorHandler = new ErrorHandlingAspect();
    }

    public Map<String, String> getWeather(double lat, double lon) {
        return errorHandler.handleError((coords) -> {
            JSONObject gridPoint = null;
            try {
                gridPoint = api.getGridPoint(lat, lon);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String gridId = gridPoint.getJSONObject("properties").getString("gridId");
            int gridX = gridPoint.getJSONObject("properties").getInt("gridX");
            int gridY = gridPoint.getJSONObject("properties").getInt("gridY");
            JSONObject forecast = null;
            try {
                forecast = api.getForecast(gridId, gridX, gridY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            logger.log("Fetched weather data for coordinates: " + lat + ", " + lon);

            return parseForecast(forecast);
        }).apply(new double[]{lat, lon});
    }

    private Map<String, String> parseForecast(JSONObject forecast) {
        Map<String, String> weatherMap = new HashMap<>();
        JSONArray periods = forecast.getJSONObject("properties").getJSONArray("periods");

        for (int i = 0; i < periods.length(); i++) {
            JSONObject period = periods.getJSONObject(i);
            String name = period.getString("name");
            String detailedForecast = period.getString("detailedForecast");
            weatherMap.put(name, detailedForecast);
        }

        return weatherMap;
    }
}
