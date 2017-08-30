package com.example.user.driver_app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rishad on 24/8/17.
 */

public class Drivers {



    private String id;
    @SerializedName("drivername")
    private String driverName;

    @SerializedName("vehicle")
    private String vehicleName;
    @SerializedName("vehiclenumber")
    private String vehicleNumber;
    @SerializedName("mobilenumber")
    private String mobileNumber;
    @SerializedName("Address")
    private String address;


    public Drivers()
    {}

    public Drivers(String driverName,String vehicleName,String vehicleNumber,String mobileNumber,String address )
    {
        this.driverName=driverName;
        this.vehicleName=vehicleName;
        this.vehicleNumber=vehicleNumber;
        this.mobileNumber=mobileNumber;
        this.address=address;
    }


    public String getDriverName()
    {
        return driverName;
    }

    public void setDriverName(String driverName)
    {
        this.driverName=driverName;
    }

    public  String getVehicleName()
    {
        return vehicleName;
    }

    public  void setVehicleName(String vehicleName)
    {
        this.vehicleName=vehicleName;
    }

    public  String getVehicleNumber()
    {
        return vehicleNumber;
    }

    public  void setVehicleNumber(String vehicleNumber)
    {
        this.vehicleNumber=vehicleNumber;
    }


    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber=mobileNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }
}
