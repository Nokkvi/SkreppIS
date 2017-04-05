package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 2.4.2017.
 */

public class RideRequest {
    private String start;
    private String end;
    private String pickuploc;
    private int seats;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setPickupLoc(String pickupLoc) {
        this.pickuploc = pickupLoc;
    }

    public String getPickupLoc() {

        return pickuploc;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
