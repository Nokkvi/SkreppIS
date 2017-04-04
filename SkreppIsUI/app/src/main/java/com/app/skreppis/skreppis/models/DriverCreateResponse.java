package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 4.4.2017.
 */

public class DriverCreateResponse {
    private boolean isActive;
    private String car_seats;
    private boolean smoking_allowed;

    public boolean isActive() {
        return isActive;
    }

    public String getCar_seats() {
        return car_seats;
    }

    public boolean isSmoking_allowed() {
        return smoking_allowed;
    }
}
