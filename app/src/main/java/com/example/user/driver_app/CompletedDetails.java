package com.example.user.driver_app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rishad on 29/8/17.
 */

public class CompletedDetails {


    private  String id;
    @SerializedName("date")
    private  String date;
    @SerializedName("time")
    private  String time;
    @SerializedName("pickup")
    private  String pickUp;
    @SerializedName("vehicle")
    private  String vehicle;
    @SerializedName("riderphone")
    private  String riderPhone;
    @SerializedName("email")
    private  String email;
    @SerializedName("ridestatus")
    private  String status;
    @SerializedName("assigndriver")
    private  String assignDriver;


    public CompletedDetails()
    {}

    public CompletedDetails(String date,String time,String pickUp,String vehicle,String riderPhone,String email,String status,String assignDriver)
    {
        this.date=date;
        this.time=time;
        this.pickUp=pickUp;
        this.vehicle=vehicle;
        this.riderPhone=riderPhone;
        this.email=email;
        this.status=status;
        this.assignDriver=assignDriver;

    }

    public String getDate()
    {
        return date;
    }
    public  void setDate(String date)
    {
        this.date=date;
    }
    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time=time;
    }
    public String getPickUp()
    {
        return pickUp;
    }
    public  void setPickUp(String pickUp)
    {
        this.pickUp=pickUp;
    }
    public String getVehicle()
    {
        return vehicle;
    }
    public void  setVehicle(String vehicle)
    {
        this.vehicle=vehicle;
    }
    public  String getRiderPhone()
    {
        return riderPhone;
    }
    public void setRiderPhone(String riderPhone)
    {
        this.riderPhone=riderPhone;
    }

    public  String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getStatus()
    {
        return status;
    }
    public  void  setStatus(String status)
    {
        this.status=status;
    }

    public String getAssignDriver()
    {
        return assignDriver;
    }
    public  void setAssignDriver(String assignDriver)
    {
        this.assignDriver=assignDriver;
    }


}
