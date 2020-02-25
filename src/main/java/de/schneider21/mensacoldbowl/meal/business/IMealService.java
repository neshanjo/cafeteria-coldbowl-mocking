package de.schneider21.mensacoldbowl.meal.business;

import java.util.List;

import de.schneider21.mensacoldbowl.meal.data.Meal;

public interface IMealService {

    List<Meal> getAllMealsSinceDateOrderedByDateAsc(String dateString);

    Meal getMealForDate(String dateString);
}
