package com.app.skreppis.skreppis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nökkvi on 26.3.2017.
 */

public class DriverListItemResponse {
    @SerializedName("name")
    @Expose
    private String driverName;

    @SerializedName("phone_number")
    @Expose
    private String driverPhone;

    @SerializedName("car_seats")
    @Expose
    private String driverSeats;

    @SerializedName("zones")
    @Expose
    private List<DriverZone> zones;

    @SerializedName("rating")
    @Expose
    private List<Rating> ratings;

    public String getDriverName() {
        return driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public String getDriverSeats() {
        return driverSeats;
    }

    public List<DriverZone> getDriverZones() { return zones; }

    public List<Rating> getDriverRating() { return ratings; }

}
