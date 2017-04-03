package com.app.skreppis.skreppis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nökkvi on 3.4.2017.
 */

public class RideRequestList {
    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("next")
    @Expose
    private DriverListItemResponse next;

    @SerializedName("previous")
    @Expose
    private DriverListItemResponse previous;

    @SerializedName("results")
    @Expose
    private List<RideRequestListItemResponse> results;

    public int getCount() {
        return count;
    }

    public DriverListItemResponse getNext() {
        return next;
    }

    public DriverListItemResponse getPrevious() {
        return previous;
    }

    public List<RideRequestListItemResponse> getResults() {
        return results;
    }
}
