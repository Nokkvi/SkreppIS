package com.app.skreppis.skreppis.interfaces;

import com.app.skreppis.skreppis.models.RegisterRequest;
import com.app.skreppis.skreppis.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Nökkvi on 21.3.2017.
 */

public interface SkreppIsApi {
    @POST("/register/")
    Call<RegisterResponse> getRegistered(@Body RegisterRequest registerRequest);
}
