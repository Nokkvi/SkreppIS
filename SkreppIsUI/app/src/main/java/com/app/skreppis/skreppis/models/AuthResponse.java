package com.app.skreppis.skreppis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nökkvi on 23.3.2017.
 */

public class AuthResponse {
    @SerializedName("token")
    @Expose
    private String token;

    public String getToken(){
        return token;
    }
}
