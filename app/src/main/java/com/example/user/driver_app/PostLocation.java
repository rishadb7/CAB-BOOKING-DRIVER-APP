package com.example.user.driver_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rishad on 27/8/17.
 */

public class PostLocation {

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("drivername")
    @Expose
    private String driverName;
    @SerializedName("vehiclenumber")
    @Expose
    private String vehicleNumber;
    @SerializedName("rideremail")
    @Expose
    private String riderMail;




}
