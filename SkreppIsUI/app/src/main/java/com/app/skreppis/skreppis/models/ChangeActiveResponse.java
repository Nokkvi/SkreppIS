package com.app.skreppis.skreppis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nökkvi on 5.4.2017.
 */

public class ChangeActiveResponse {
    @SerializedName("isActive")
    @Expose
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }
}
