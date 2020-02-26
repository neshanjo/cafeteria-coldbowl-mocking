package de.schneider21.mensacoldbowl.meal.business;

public interface IWeatherService {

    Double getTemperatureInCelsius(String cityName, String countryCode, String dateString);
}
