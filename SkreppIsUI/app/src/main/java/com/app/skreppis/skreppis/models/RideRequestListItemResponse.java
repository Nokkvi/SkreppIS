package com.app.skreppis.skreppis.models;

/**
 * Created by Elisabet on 03/04/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RideRequestListItemResponse {
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

    @SerializedName("pickuploc")
    @Expose
    private String pickuploc;

    @SerializedName("seats")
    @Expose
    private int seats;

    @SerializedName("over")
    @Expose
    private boolean over;

    @SerializedName("accepted")
    @Expose
    private boolean accepted;

    public String getPassenger() {
        return passenger;
    }

    public String getDriver() {
        return driver;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getPickuploc() {
        return pickuploc;
    }
}
