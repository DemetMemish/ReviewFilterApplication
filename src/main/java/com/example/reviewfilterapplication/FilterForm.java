package com.example.reviewfilterapplication;

public class FilterForm {
    private String orderByRating;
    private int minRating;
    private String orderByDate;
    private String prioritizeText;

    public String getOrderByRating() {
        return orderByRating;
    }

    public void setOrderByRating(String orderByRating) {
        this.orderByRating = orderByRating;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public String getOrderByDate() {
        return orderByDate;
    }

    public void setOrderByDate(String orderByDate) {
        this.orderByDate = orderByDate;
    }

    public String getPrioritizeText() {
        return prioritizeText;
    }

    public void setPrioritizeText(String prioritizeText) {
        this.prioritizeText = prioritizeText;
    }
}
