package de.schneider21.mensacoldbowl.meal.business;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherBitService implements IWeatherService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private ForecastResponse parseWeatherResponse(String body) throws IOException {
        return mapper.readerFor(ForecastResponse.class)
                .readValue(body);
    }

    @Override
    public Double getTemperatureInCelsius(String cityName, String countryCode, String dateString) {
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse("https://api.weatherbit.io/v2.0/forecast/daily")
                .newBuilder();
        urlBuilder.addQueryParameter("city", cityName)
        .addQueryParameter("country", countryCode)
        .addQueryParameter("key", "78b3d1a6b9b04371825477a5ec1bdda1");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        System.out.printf("WeatherBitService: Requesting weather data for %s (%s)\n", cityName, countryCode);
        try {
            final Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.printf("Could not get weather, got status %s and body %s\n", response.code(), response.body().string());
                return null;
            }
            final ForecastResponse forecastResponse = parseWeatherResponse(response.body().string());
            // example response: ForecastResponse{data=[ForecastData{datetime='2020-02-25', max_temp='8.4'},
            // ForecastData{datetime='2020-02-26', max_temp='4.3'}, ForecastData{datetime='2020-02-27', max_temp='2.7'},
            // ForecastData{datetime='2020-02-28', max_temp='5.1'}, ForecastData{datetime='2020-02-29', max_temp='8.3'}, ...
            // (16 days from current date)
            return forecastResponse.getData().stream()
                    .filter(data -> dateString.equals(data.getDatetime()))
                    .map(ForecastData::getMax_temp)
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            System.out.printf("Could not get weather: " + e.getMessage());
            return null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ForecastData {
        double max_temp;
        String datetime;

        public ForecastData() {
        }

        public double getMax_temp() {
            return max_temp;
        }

        public void setMax_temp(double max_temp) {
            this.max_temp = max_temp;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        @Override
        public String toString() {
            return "ForecastData{" +
                    "datetime='" + datetime + '\'' +
                    ", max_temp='" + max_temp + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ForecastResponse {

        private List<ForecastData> data;

        public List<ForecastData> getData() {
            return data;
        }

        public void setData(List<ForecastData> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "ForecastResponse{" +
                    "data=" + data +
                    '}';
        }
    }

}
