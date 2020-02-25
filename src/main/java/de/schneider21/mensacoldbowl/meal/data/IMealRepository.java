package de.schneider21.mensacoldbowl.meal.data;

import java.util.List;

public interface IMealRepository {

    List<Meal> findAllSortByDateAsc();

    Meal findByDate(String dateString);
}
