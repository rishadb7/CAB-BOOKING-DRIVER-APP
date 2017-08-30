package com.example.user.driver_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 21/8/17.
 */

public class View_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{
    private Button start;
    private TextView name,time,date,phone,pickPoint,ctime,cdate,cphone,names,titleName;
    private String bookingDate,bookingTime,pickUp,riderPhone,riderEmail,driverName,vehicleNumber;
    private LocationManager locationManager;
    private Context context;
    private Boolean GpsStatus;
    private String apiKey="mongodb786";

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private Intent intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        name = (TextView)findViewById(R.id.picup_point);
        time = (TextView)findViewById(R.id.picup_time);
        date = (TextView)findViewById(R.id.picup_date);
        phone = (TextView)findViewById(R.id.contact_number);
        pickPoint = (TextView)findViewById(R.id.ppoint);
        ctime = (TextView)findViewById(R.id.ptime);
        cdate = (TextView)findViewById(R.id.pdate);
        cphone = (TextView)findViewById(R.id.pPhone);
        start = (Button)findViewById(R.id.start);
        names=(TextView)findViewById(R.id.mrs);
        titleName=(TextView)findViewById(R.id.titlename);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");
        titleName.setTypeface(tf);
        name.setTypeface(tf);
        time.setTypeface(tf);
        date.setTypeface(tf);
        phone.setTypeface(tf);
        pickPoint.setTypeface(tf);
        ctime.setTypeface(tf);
        cdate.setTypeface(tf);
        cphone.setTypeface(tf);
        names.setTypeface(tf);


        context = getApplicationContext();
        Intent i2 = new Intent(this,View_Activity.class);

        Intent intent = getIntent();
        bookingDate=intent.getStringExtra("date");
        bookingTime=intent.getStringExtra("time");
        pickUp=intent.getStringExtra("pickup");
        riderPhone=intent.getStringExtra("riderphone");
        riderEmail=intent.getStringExtra("email");

        names.setText(riderEmail);
        ctime.setText(bookingTime);
        cdate.setText(bookingDate);
        cphone.setText(riderPhone);
        pickPoint.setText(pickUp);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CheckGpsStatus();
                if(GpsStatus == true)
                {


                    sendPost();
                    Intent i = new Intent(View_Activity.this,CompleteActivity.class);
                    i.putExtra("riderEmail",riderEmail);
                    i.putExtra("date",bookingDate);
                    i.putExtra("time",bookingTime);
                    startActivity(i);
                }else
                {
                    final Dialog dialog = new Dialog(View_Activity.this);
                    dialog.setContentView(R.layout.activity_popup);
                    dialog.setTitle("  Location Permission");
                    final TextView tv  = (TextView) dialog.findViewById(R.id.textView2);

                    Button btncancel = (Button)dialog.findViewById(R.id.button3);
                    Button btnsignin = (Button)dialog.findViewById(R.id.button4);
                    dialog.show();
                    btncancel.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            dialog.dismiss();
                        }
                    });
                    btnsignin.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                           // Toast.makeText(getApplicationContext(), "Location Enabled", Toast.LENGTH_SHORT).show();
                            intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            dialog.dismiss();

                            startActivity(intent1);
                        }
                    });



                }



            }
        });





    }

    public void CheckGpsStatus(){

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);



    }





   /* private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }*/

    public void sendPost()

    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.223.214.194/projects/CabAdmin/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        SharedPreferences sharedPreferences = getSharedPreferences("key",0);

        driverName=sharedPreferences.getString("driverName","");
        vehicleNumber=sharedPreferences.getString("vehicleNumber","");



        String la=String.valueOf(currentLatitude);
        String lo=String.valueOf(currentLongitude);

        Toast.makeText(getApplicationContext(), la+"--"+lo, Toast.LENGTH_LONG).show();


        ApiService calls =retrofit.create(ApiService.class);
        Call<PostLocation> call =calls.savePost(apiKey,la,lo,driverName,vehicleNumber,riderEmail);

        call.enqueue(new Callback<PostLocation>() {
            @Override
            public void onResponse(Call<PostLocation> call, Response<PostLocation> response) {

                Toast.makeText(getApplicationContext(), "Location inserted", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<PostLocation> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error location update", Toast.LENGTH_LONG).show();
            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }


}
