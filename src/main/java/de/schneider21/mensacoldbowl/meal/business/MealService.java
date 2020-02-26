package de.schneider21.mensacoldbowl.meal.business;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.schneider21.mensacoldbowl.meal.data.IMealRepository;
import de.schneider21.mensacoldbowl.meal.data.Meal;

public class MealService implements IMealService {

    private IMealRepository mealRepository;
    private IWeatherService weatherService;

    public MealService(IMealRepository mealRepository, IWeatherService weatherService) {
        Objects.requireNonNull(mealRepository);
        this.mealRepository = mealRepository;
        Objects.requireNonNull(weatherService);
        this.weatherService = weatherService;
    }

    @Override
    public List<Meal> getAllMealsSinceDateOrderedByDateAsc(String dateString) {
        return mealRepository.findAllSortByDateAsc()
                .stream()
                .filter(meal -> meal.getDate().compareTo(dateString) >= 0)
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public Meal getMealForDate(String dateString) {
        final Meal meal = mealRepository.findByDate(dateString);
        if (meal == null) {
            return null;
        }
        final Double temperatureInCelsius = weatherService.getTemperatureInCelsius("Kaiserslautern", "DE", dateString);
        if (temperatureInCelsius != null) {
            System.out.printf("MealService: Temperature received is %.2f°C\n", temperatureInCelsius);
        } else {
            System.out.printf("MealService: No temperature value could be found\n");
        }
        meal.setColdBowlProbabilityInPercent(calculateColdBowlProbabilityInPercent(temperatureInCelsius));
        return meal;
    }

    @Override
    public Integer calculateColdBowlProbabilityInPercent(Double temperature) {
        if (temperature == null) {
            return null;
        }
        return Math.toIntExact(Math.round(Math.max(0, Math.min(100, (temperature - 20) * 10))));
    }
}
