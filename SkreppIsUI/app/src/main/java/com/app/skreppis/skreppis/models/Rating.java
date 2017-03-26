package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 26.3.2017.
 */

public class Rating {
    private String driver, passenger, comment;
    private int stars;

    public String getDriver() {
        return driver;
    }

    public int getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public String getPassenger() {
        return passenger;
    }
}
