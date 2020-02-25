package de.schneider21.mensacoldbowl;

import de.schneider21.mensacoldbowl.meal.business.IMealService;

public class Application {

    public static void main(String[] args) {

        ServiceFactory.setProfile(ServiceFactory.Profile.LOCAL);

        final IMealService mealService = ServiceFactory.getInstance().getMealService();
        mealService.getAllMealsSinceDateOrderedByDateAsc("2020-04-01")
                .forEach(System.out::println);
    }
}
