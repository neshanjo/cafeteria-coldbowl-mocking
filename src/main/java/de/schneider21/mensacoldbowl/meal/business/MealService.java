package de.schneider21.mensacoldbowl.meal.business;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.schneider21.mensacoldbowl.meal.data.IMealRepository;
import de.schneider21.mensacoldbowl.meal.data.Meal;

public class MealService implements IMealService {

    private IMealRepository mealRepository;

    public MealService(IMealRepository mealRepository) {
        Objects.requireNonNull(mealRepository);
        this.mealRepository = mealRepository;
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
        return mealRepository.findByDate(dateString);
    }
}
