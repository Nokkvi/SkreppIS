package com.app.skreppis.skreppis.models;

/**
 * Created by Elisabet on 03/04/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nökkvi on 26.3.2017.
 */

public class PassengerListItemResponse {
    @SerializedName("name")
    @Expose
    private String passengerName;

    @SerializedName("phone_number")
    @Expose
    private String passengerPhone;

    @SerializedName("riderequests")
    @Expose
    private String passengerRideRequests;

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

