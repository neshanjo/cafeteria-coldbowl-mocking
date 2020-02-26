package de.schneider21.mensacoldbowl.meal.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.schneider21.mensacoldbowl.meal.data.InMemoryRepository;
import de.schneider21.mensacoldbowl.meal.data.Meal;

class MealServiceTest {

    private IMealService mealService;
    private IWeatherService weatherService;

    private final Meal meal1 = new Meal("2020-03-01", "Kaviar mit Pommes", new BigDecimal("20.00"));
    private final Meal meal2 =
            new Meal("2020-03-02", "Schneckenauflauf mit Fischstäbchen", new BigDecimal("1.10"));
    private final Meal meal3 =
            new Meal("2020-03-03", "Saure Gurken mit roter Grütze", new BigDecimal("2.70"));

    @BeforeEach
    void setUp() {
        weatherService = new IWeatherService() {
            @Override
            public Double getTemperatureInCelsius(String cityName, String countryCode, String dateString) {
                return null;
            }
        };
        mealService = new MealService(
                new InMemoryRepository(
                        Arrays.asList(meal1, meal2, meal3)
                ), weatherService
        );
    }

    @AfterEach
    void tearDown() {
    }

    private static void assertEqual(Meal expected, Meal actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }
        // from here: expected is not null
        assertNotNull(actual);
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getColdBowlProbabilityInPercent(), actual.getColdBowlProbabilityInPercent());
    }

    @Test
    void getMealForDate_mealPresent() {
        final Meal expected = meal2;
        final Meal actual = mealService.getMealForDate("2020-03-02");
        assertEqual(expected, actual);
    }

    @Test
    void getMealForDate_mealNotPresent() {
        final Meal notExisting = mealService.getMealForDate("2019-03-02");
        assertNull(notExisting);
    }

    @Test
    void calculateColdBowlProbabilityInPercent() {
        assertNull(mealService.calculateColdBowlProbabilityInPercent(null));
        assertEquals(0, mealService.calculateColdBowlProbabilityInPercent(-1.0));
        assertEquals(0, mealService.calculateColdBowlProbabilityInPercent(0.0));
        assertEquals(0, mealService.calculateColdBowlProbabilityInPercent(19.0));
        assertEquals(0, mealService.calculateColdBowlProbabilityInPercent(19.9));
        assertEquals(0, mealService.calculateColdBowlProbabilityInPercent(20.0));

        assertEquals(10, mealService.calculateColdBowlProbabilityInPercent(21.0));
        assertEquals(47, mealService.calculateColdBowlProbabilityInPercent(24.7));
        assertEquals(50, mealService.calculateColdBowlProbabilityInPercent(25.0));
        assertEquals(99, mealService.calculateColdBowlProbabilityInPercent(29.9));

        assertEquals(100, mealService.calculateColdBowlProbabilityInPercent(30.0));
        assertEquals(100, mealService.calculateColdBowlProbabilityInPercent(30.1));
        assertEquals(100, mealService.calculateColdBowlProbabilityInPercent(40.14));
        assertEquals(100, mealService.calculateColdBowlProbabilityInPercent(100.0));
    }
}