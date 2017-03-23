package com.app.skreppis.skreppis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nökkvi on 22.3.2017.
 */

public class LoginResponse {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("token")
    @Expose
    private String token;

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getToken(){
        return token;
    }
}
