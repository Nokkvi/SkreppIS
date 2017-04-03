package com.app.skreppis.skreppis.models;

/**
 * Created by Elisabet on 03/04/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RideRequestListItemResponse {
    @SerializedName("name")
    @Expose
    private String passengerName;

    @SerializedName("phone_number")
    @Expose
    private String passengerPhone;

    @SerializedName("riderequests")
    @Expose
    private String passengerRideRequests;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("end")
    @Expose
    private String end;

    @SerializedName("over")
    @Expose
    private boolean over;

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean isOver() {
        return over;
    }


    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public String getPassengerRideRequests() {
        return passengerRideRequests;
    }
}
