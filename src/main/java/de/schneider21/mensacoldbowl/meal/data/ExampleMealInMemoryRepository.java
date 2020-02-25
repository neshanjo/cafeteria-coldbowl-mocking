package de.schneider21.mensacoldbowl.meal.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleMealInMemoryRepository implements IMealRepository {

    private List<Meal> allMeals;

    public ExampleMealInMemoryRepository() {
        allMeals = new ArrayList<>();
        allMeals.add(new Meal("2020-03-23", "Gebratene Hähnchenkeule  mit Mexikana-Salsa, Steakhouse-Fries und Weißkrautsalat \\\"Coleslaw\\\"", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-03-24", "Maultaschen mit Fleischfüllung, Kartoffel-Käse-Soße  und Salat", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-03-25", "Paniertes Schweineschnitzel  mit Rahmsoße, Spätzle  und Salat", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-03-26", "Rindersaftgulasch  mit hausgemachtem Karotten-Kartoffelstampf und Salat", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-03-27", "Indisches Fischcurry mit Seelachs und Gemüse, Duftreis und Salat", new BigDecimal("2.65")));

        allMeals.add(new Meal("2020-03-30", "Falafel mit \"Ras el Hanout\"-Soße, orientalischem Reis und Salat", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-03-31", "Geschmorte Lammkeule \"Provencial\" mit Burgundersoße und Pariser Kartoffeln", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-04-01", "\"Fajita Pueblo\" mit Paprika, Zucchini und Zwiebeln, dazu Sour-Cream-Dip, Tortillas und Salat", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-04-02", "Hähnchenbrustfilet, Ratatouillegemüse, Thymiankartoffeln und Salat", new BigDecimal("2.65")));
        allMeals.add(new Meal("2020-04-03", "'Dibbelabbes': Saarländischer Kartoffelauflauf mit Lauch, dazu Apfelmus", new BigDecimal("2.65")));
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
