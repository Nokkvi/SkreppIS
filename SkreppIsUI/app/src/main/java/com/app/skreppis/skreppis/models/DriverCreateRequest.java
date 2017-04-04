package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 4.4.2017.
 */

public class DriverCreateRequest {
    private boolean isActive;
    private String car_seats;
    private boolean smoking_allowed;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCar_seats() {
        return car_seats;
    }

    public void setCar_seats(String car_seats) {
        this.car_seats = car_seats;
    }

    public boolean isSmoking_allowed() {
        return smoking_allowed;
    }

    public void setSmoking_allowed(boolean smoking_allowed) {
        this.smoking_allowed = smoking_allowed;
    }
}
