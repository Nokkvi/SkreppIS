package com.app.skreppis.skreppis.interfaces;


import com.app.skreppis.skreppis.models.*;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Nökkvi on 21.3.2017.
 */

public interface SkreppIsApi {
    @POST("/register/")
    Call<RegisterResponse> getRegistered(@Body RegisterRequest registerRequest);

    @POST("/passenger/create/")
    Call<PassengerCreateResponse> createPassenger(@Header("Authorization") String token, @Body PassengerCreateRequest passengerCreateRequest);

    @POST("/login/")
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

    @POST("/api-token-auth/")
    Call<AuthResponse> Auth(@Body AuthRequest authRequest);

    @GET("/driver/")
    Call<DriverList> getDriverList();

    @GET("/driver/")
    Call<DriverList> getDriverListSearch(@Query("zones") String zone, @Query("car_seats") String car_seats);
}
