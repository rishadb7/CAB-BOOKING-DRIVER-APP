package com.example.user.driver_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity
{
    private EditText vnumber,password;
    private Button login;
    private String apiKey="mongodb786";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Drivers> driversList;


    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vnumber = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText4);
        login = (Button)findViewById(R.id.button2);

        password.setVisibility(View.INVISIBLE);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");
        vnumber.setTypeface(tf);
        login.setTypeface(tf);

        driversList=new ArrayList<>();




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        loginDrivers();

            }
        });



    }



    private void loginDrivers()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.223.214.194/projects/CabAdmin/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service =retrofit.create(ApiService.class);



        Call<List<Drivers>> call =service.getDriverDetails(apiKey,vnumber.getText().toString());

        call.enqueue(new Callback<List<Drivers>>() {
            @Override
            public void onResponse(Call<List<Drivers>> call, Response<List<Drivers>> response) {

                List<Drivers> list =response.body();
                Drivers drivers=null;

             //   Toast.makeText(getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                for(int i=0;i<list.size();i++)
                {

                    drivers=new Drivers();

                    String drivername=list.get(i).getDriverName();
                    String vehicleName=list.get(i).getVehicleName();
                    String vehicleNumber=list.get(i).getVehicleNumber();
                    String mobileNumber =list.get(i).getMobileNumber();
                    String address=list.get(i).getAddress();

                    drivers.setDriverName(drivername);
                    drivers.setVehicleName(vehicleName);
                    drivers.setVehicleNumber(vehicleNumber);
                    drivers.setMobileNumber(mobileNumber);
                    drivers.setAddress(address);

                    driversList.add(drivers);


                    sharedPreferences = getApplicationContext().getSharedPreferences("key", 0);
                    editor = sharedPreferences.edit();

                    editor.putString("vehicleNumber",vehicleNumber);
                    editor.putString("driverName",drivername);
                    editor.putString("vehicleName",vehicleName);
                    editor.putString("mobileNumber",mobileNumber);
                    editor.putString("address",address);
                    editor.apply();


                }
            }

            @Override
            public void onFailure(Call<List<Drivers>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }


}


