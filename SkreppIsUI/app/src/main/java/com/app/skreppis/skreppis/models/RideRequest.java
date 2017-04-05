package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 2.4.2017.
 */

public class RideRequest {
    private String start;
    private String end;
    private String pickup;
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

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getPickup() {

        return pickup;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
