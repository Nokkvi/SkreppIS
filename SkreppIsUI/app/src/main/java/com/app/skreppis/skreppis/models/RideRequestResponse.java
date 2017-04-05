package com.app.skreppis.skreppis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nökkvi on 2.4.2017.
 */

public class RideRequestResponse {
    @SerializedName("passenger")
    @Expose
    private String passenger;
    @SerializedName("driver")
    @Expose
    private String driver;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("seats")
    @Expose
    private int seats;
    @SerializedName("pickuploc")
    @Expose
    private String pickuploc;
    @SerializedName("over")
    @Expose
    private boolean over;

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getSeats() {
        return seats;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getDriver() {
        return driver;
    }

    public String getPickuploc() {
        return pickuploc;
    }

    public boolean isOver() {
        return over;
    }
}
