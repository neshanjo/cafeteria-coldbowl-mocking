package de.schneider21.mensacoldbowl;

import de.schneider21.mensacoldbowl.meal.business.IMealService;
import de.schneider21.mensacoldbowl.meal.business.IWeatherService;
import de.schneider21.mensacoldbowl.meal.business.MealService;
import de.schneider21.mensacoldbowl.meal.business.WeatherBitService;
import de.schneider21.mensacoldbowl.meal.data.ExampleData;
import de.schneider21.mensacoldbowl.meal.data.IMealRepository;
import de.schneider21.mensacoldbowl.meal.data.InMemoryRepository;

public class ServiceFactory {

    public enum Profile {
        PRODUCTION, LOCAL, TEST
    }

    private static Profile PROFILE_FOR_INITIALIZATION;

    /**
     * initially sets the profile for the service factory. Must be called exactly once, before the first
     * {@link ServiceFactory#getInstance()} call.
     *
     *
     * @param profile
     */
    public static void setProfile(Profile profile) {
        if (ServiceFactory.PROFILE_FOR_INITIALIZATION != null) {
            throw new IllegalStateException("profile already set");
        }
        ServiceFactory.PROFILE_FOR_INITIALIZATION = profile;
    }

    private static volatile ServiceFactory INSTANCE;

    public static ServiceFactory getInstance() {
        // double check idiom
        if (INSTANCE == null) {
            synchronized (ServiceFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceFactory();
                }
            }
        }
        return INSTANCE;
    }

    private IMealRepository mealRepository;
    private IMealService mealService;
    private IWeatherService weatherService;

    private ServiceFactory() {
        if (PROFILE_FOR_INITIALIZATION == null) {
            throw new IllegalStateException("Profile must be set before service factory initialization");
        }
        // dependencies are directly instantiated. This could be optimized by using lazy initialitation.

        if (PROFILE_FOR_INITIALIZATION == Profile.PRODUCTION) {
            throw new IllegalStateException("Production services are not ready yet!");
        }
        // instantiation of services for LOCAL and TEST profile
        mealRepository = new InMemoryRepository(ExampleData.EXAMPLE_MEALS);
        weatherService = new WeatherBitService();
        mealService = new MealService(mealRepository, weatherService);
    }


    public IMealService getMealService() {
        return mealService;
    }

    public IMealRepository getMealRepository() {
        return mealRepository;
    }
}
