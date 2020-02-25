package de.schneider21.mensacoldbowl.meal.api;

import java.util.List;

import de.schneider21.mensacoldbowl.ServiceFactory;
import de.schneider21.mensacoldbowl.framework.RestController;
import de.schneider21.mensacoldbowl.meal.business.IMealService;
import de.schneider21.mensacoldbowl.meal.data.Meal;

public class MealController extends RestController {

    public MealController() {
        super();
        addHttpGetMapping("/meal", this::getMeals);
        addHttpGetMapping("/meal/.*", this::getMeal);
    }

    public List<Meal> getMeals(IRequestParameters parameters) {
        final String fromDate = parameters.getQueryParameter("fromDate");
        if (fromDate == null) {
            throw new IllegalArgumentException("Parameter fromDate is missing");
        }

        final IMealService mealService = ServiceFactory.getInstance().getMealService();
        final List<Meal> meals = mealService.getAllMealsSinceDateOrderedByDateAsc(fromDate);

        return meals;
    }

    public Meal getMeal(IRequestParameters parameters) {
        final String dateFromPath = parameters.getPath().substring("/meal/".length());

        final IMealService mealService = ServiceFactory.getInstance().getMealService();
        final Meal meal = mealService.getMealForDate(dateFromPath);

        return meal;
    }

}
