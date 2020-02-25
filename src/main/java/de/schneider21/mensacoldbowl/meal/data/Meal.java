package de.schneider21.mensacoldbowl.meal.data;

import java.math.BigDecimal;

public class Meal {

    // For a real system, it would be more robust to use Date or long instead of string here
    private String date;
    private String title;
    private BigDecimal price;
    private Integer coldBowlProbabilityInPercent;

    public Meal(String date, String title, BigDecimal price) {
        this.date = date;
        this.title = title;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getColdBowlProbabilityInPercent() {
        return coldBowlProbabilityInPercent;
    }

    public void setColdBowlProbabilityInPercent(Integer coldBowlProbabilityInPercent) {
        this.coldBowlProbabilityInPercent = coldBowlProbabilityInPercent;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", coldBowlProbabilityInPercent=" + coldBowlProbabilityInPercent +
                '}';
    }
}
