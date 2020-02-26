package de.schneider21.mensacoldbowl.meal.data;

import java.util.Collections;
import java.util.List;

public class InMemoryRepository implements IMealRepository {

    private List<Meal> allMeals;

    public InMemoryRepository(List<Meal> allMeals) {
        this.allMeals = allMeals;
    }

    public InMemoryRepository() {

    }

    public List<Meal> findAllSortByDateAsc() {
        return Collections.unmodifiableList(allMeals);
    }

    @Override
    public Meal findByDate(String dateString) {
        return allMeals.stream()
                .filter(meal -> dateString.equals(meal.getDate()))
                .findFirst()
                .orElse(null);
    }
}
