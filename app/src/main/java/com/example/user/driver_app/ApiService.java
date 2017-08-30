package com.example.user.driver_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rishad on 24/8/17.
 */

public interface ApiService {


    @GET("login.php")
    Call<List> getMyJSON(@Query("apiKey") String apiKey,@Query("vehicleNumber") String vehicleNumber);


    @GET("login.php")
    Call<List<Drivers>> getDriverDetails(@Query("apiKey") String apiKey,@Query("vehicleNumber") String vehicleNumber);


    @GET("getbooking.php")
    Call<List<BookingDetails>> getBookingDetails(@Query("apiKey") String apiKey,@Query("drivername") String driverName);


    @POST("location_insert.php")
    @FormUrlEncoded
    Call<PostLocation> savePost(@Query("apiKey") String apiKey,@Field("latitude") String latitude,
                        @Field("longitude") String longitude, @Field("drivername") String vName,
                                @Field("vehiclenumber") String vNumber,@Field("rideremail") String riderMail
                                );


    @POST("location_update.php")
    @FormUrlEncoded
    Call<PostLocation> locationUpdates(@Query("apiKey") String apiKey,@Field("latitude") String latitude,
                                @Field("longitude") String longitude, @Field("drivername") String vName,
                                @Field("vehiclenumber") String vNumber,@Field("rideremail") String riderMail
    );


    @POST("move_to_completed.php")
    @FormUrlEncoded
    Call<List> movetoCompleted(@Query("apiKey") String apiKey,@Field("drivername") String driverName,
                                       @Field("rideremail") String riderEmail, @Field("date") String date,
                                       @Field("time") String time
    );

    @GET("get_completed_details.php")
    Call<List<CompletedDetails>> getCompledDetails(@Query("apiKey") String apiKey,@Query("drivername") String driverName);


}
