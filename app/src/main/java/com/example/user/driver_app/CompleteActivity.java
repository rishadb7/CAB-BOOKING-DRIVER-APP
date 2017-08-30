package com.example.user.driver_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 21/8/17.
 */

public class CompleteActivity extends AppCompatActivity

{
    private double latitude,longitude;

    GPSTracker gps;
    final Handler handler = new Handler();
    private static Timer timer = new Timer();
   // final Timer timer = new Timer();
    private String apiKey="mongodb786";

    private ImageView im;
    private TextView textView,textView3;
    private Button completed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);


        completed=(Button)findViewById(R.id.completed);
        im = (ImageView)findViewById(R.id.imageView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView=(TextView)findViewById(R.id.textView);
        textView3=(TextView)findViewById(R.id.textView3);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");
        textView.setTypeface(tf);
        completed.setTypeface(tf);
        textView3.setTypeface(tf);


        gps = new GPSTracker(CompleteActivity.this);


        // check if GPS enabled
        if(gps.canGetLocation()){
            TimerTask doAsynchronousTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {


                                //sendPost();
                                 latitude = gps.getLatitude();
                                 longitude = gps.getLongitude();

                                // \n is for new line
                                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                                 updateLocation();

                            }
                            catch (Exception e)
                            {

                            }


                        }
                    });
                }
            };
            timer.schedule(doAsynchronousTask,0,5000);

             }
             else
                 {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToCompleted();

                new AlertDialog.Builder(CompleteActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Logout")
                        .setMessage("Do you want to logout?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                SharedPreferences sharedPreferences =getSharedPreferences("key",0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                finish();
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();

                                Intent i2 = new Intent(CompleteActivity.this,LoginActivity.class);
                                timer.cancel();
                                startActivity(i2);

                                moveToCompleted();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener()
                        {
                             @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent i = new Intent(CompleteActivity.this,MainActivity.class);
                                timer.cancel();
                                startActivity(i);
                                moveToCompleted();

                            }
                        }).show();



            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void updateLocation()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.223.214.194/projects/CabAdmin/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences sharedPreferences = getSharedPreferences("key",0);

       String driverName=sharedPreferences.getString("driverName","");
       String vehicleNumber=sharedPreferences.getString("vehicleNumber","");


        Intent intent = getIntent();
        String riderEmail=intent.getStringExtra("riderEmail");

        String la=String.valueOf(gps.getLatitude());
        String lo=String.valueOf(gps.getLongitude());

        Toast.makeText(getApplicationContext(), la+lo, Toast.LENGTH_LONG).show();


        ApiService calls =retrofit.create(ApiService.class);
        Call<PostLocation> call =calls.locationUpdates(apiKey,la,lo,driverName,vehicleNumber,riderEmail);

        call.enqueue(new Callback<PostLocation>() {
            @Override
            public void onResponse(Call<PostLocation> call, Response<PostLocation> response) {

                Toast.makeText(getApplicationContext(), "Location updated", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<PostLocation> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error location update", Toast.LENGTH_LONG).show();
            }
        });
    }



    public void moveToCompleted()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.223.214.194/projects/CabAdmin/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService calls =retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("key",0);

        String driverName=sharedPreferences.getString("driverName","");
        String vehicleNumber=sharedPreferences.getString("vehicleNumber","");

        Intent intent = getIntent();
        String riderEmail=intent.getStringExtra("riderEmail");
        String date =intent.getStringExtra("date");
        String time =intent.getStringExtra("time");


        Call<List> call =calls.movetoCompleted(apiKey,driverName,riderEmail,date,time);

        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {

                Toast.makeText(getApplicationContext(), "Move to completed Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Fail move to completed", Toast.LENGTH_LONG).show();
            }
        });

    }



}
