package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 3.4.2017.
 */

public class RideRequestListItemResponse {
    private String passengerName;
    private String start;
    private String end;
    private boolean over;

    public String getPassengerName() {
        return passengerName;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean isOver() {
        return over;
    }
}
