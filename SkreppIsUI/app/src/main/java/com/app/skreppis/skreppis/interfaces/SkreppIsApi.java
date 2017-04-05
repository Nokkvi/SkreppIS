package com.app.skreppis.skreppis.interfaces;


import com.app.skreppis.skreppis.models.*;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nökkvi on 21.3.2017.
 */

public interface SkreppIsApi {
    @POST("/register/")
    Call<RegisterResponse> getRegistered(@Body RegisterRequest registerRequest);

    @POST("/passenger/create/")
    Call<PassengerCreateResponse> createPassenger(@Header("Authorization") String token, @Body PassengerCreateRequest passengerCreateRequest);

    @POST("/driver/create/")
    Call<DriverCreateResponse> createDriver(@Header("Authorization") String token, @Body DriverCreateRequest driverCreateRequest);

    @POST("/login/")
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);

    @POST("/api-token-auth/")
    Call<AuthResponse> Auth(@Body AuthRequest authRequest);

    @GET("/driver/")
    Call<DriverList> getDriverList();

    @GET("/driver/{name}")
    Call<DriverListItemResponse> getDriver(@Header("Authorization") String token, @Path("name") String username);

    @GET("/driver/")
    Call<DriverList> getDriverListSearch(@Query("search") String zone, @Query("q") String car_seats);

    @POST("/passenger/requestride")
    Call<RideRequestResponse> requestRide(@Header("Authorization") String token, @Body RideRequest rideRequest);

    @GET("/passenger/riderequests")
    Call<RideRequestList> getPassengerList();

    @GET("/passenger/riderequests")
    Call<RideRequestList> getPassengerListSearch(@Query("q") String start);

    @GET("/passenger/riderequests/{passenger__name}")
    Call<RideRequestResponse> checkIfRideRequest(@Header("Authorization") String token, @Path("passenger__name") String name);

    @PUT("/passenger/riderequests/{passenger__name}/editrequest")
    Call<RideRequestResponse> updateRideRequest(@Header("Authorization") String token, @Path("passenger__name") String name, @Body RideRequest rideRequest);

    @PUT("/driver/{name}/toggleactive")
    Call<ToggleActiveResponse> toggleActive(@Header("Authorization") String token, @Path("name") String username);

    @PUT("/driver/{name}/changeactive")
    Call<ChangeActiveResponse> changeActive(@Header("Authorization") String token, @Path("name") String username, @Body ChangeActiveRequest changeActiveRequest);

    @DELETE("/driver/{name}/zonedelete")
    Call<DropZoneResponse> dropZones(@Header("Authorization") String token, @Path("name") String username);

    @POST("/driver/{name}/addzone")
    Call<AddZoneResponse> addZone(@Header("Authorization") String token, @Path("name") String username, @Body AddZoneRequest AddZoneRequest);

}
