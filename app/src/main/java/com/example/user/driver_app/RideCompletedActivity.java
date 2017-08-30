package com.example.user.driver_app;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 24/8/17.
 */

public class RideCompletedActivity extends AppCompatActivity
{
    private ListView lcustomer;
    String[] sample = new String[]{
            "Nothing to show"

    };
    private int check = 0;
    private String apiKey="mongodb786";
    private ListView ccustomer;
    private List<CompletedDetails> completedDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridecomplete);
        getSupportActionBar().hide();
        ccustomer = (ListView)findViewById(R.id.list_completed);


        completedDetailsList=new ArrayList<>();


        TextView textView=(TextView)findViewById(R.id.completedTextview);

       // Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");
      //  textView.setTypeface(tf);



        getCompletedRide();


    }


    public void getCompletedRide()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.223.214.194/projects/CabAdmin/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service =retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("key",0);
        String veName=sharedPreferences.getString("driverName","");
       // vNumber=sharedPreferences.getString("vehicleNumber","");

        Call<List<CompletedDetails>> call =service.getCompledDetails(apiKey,veName);

        call.enqueue(new Callback<List<CompletedDetails>>() {
            @Override
            public void onResponse(Call<List<CompletedDetails>> call, Response<List<CompletedDetails>> response) {
                if (response.body() == null) {
                    check = 1;
                    final List<String> sampleArrayList = new ArrayList<String>(Arrays.asList(sample));
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RideCompletedActivity.this, android.R.layout.simple_list_item_1, sampleArrayList);
                    ccustomer.setAdapter(adapter);
                } else {
                    List<CompletedDetails> list = response.body();
                    CompletedDetails completed = null;
                 //   Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_SHORT).show();

                    completedDetailsList = list;

                    for (int i = 0; i < list.size(); i++) {
                        completed = new CompletedDetails();

                        String date = list.get(i).getDate();
                        String time = list.get(i).getTime();
                        String pickUp = list.get(i).getPickUp();
                        String vehicle = list.get(i).getVehicle();
                        String riderPhone = list.get(i).getRiderPhone();
                        String email = list.get(i).getEmail();
                        String status = list.get(i).getStatus();
                        String assignDriver = list.get(i).getAssignDriver();

                        completed.setDate(date);
                        completed.setTime(time);
                        completed.setPickUp(pickUp);
                        completed.setVehicle(vehicle);
                        completed.setRiderPhone(riderPhone);
                        completed.setEmail(email);
                        completed.setStatus(status);
                        completed.setAssignDriver(assignDriver);


                    }

                    showCompletedList();
                }
            }

            @Override
            public void onFailure(Call<List<CompletedDetails>> call, Throwable t) {

            }
        });

    }

    public void showCompletedList()
    {
        String[] items =new String[completedDetailsList.size()];

        for(int i=0; i<completedDetailsList.size();i++)
        {
            items[i] =completedDetailsList.get(i).getEmail()+"\n"+completedDetailsList.get(i).getDate()+"\n"+completedDetailsList.get(i).getTime();
        }

        ArrayAdapter adapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_comp_list,items)
        {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textview = (TextView) view;
                Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");

                textview.setTypeface(tf);
                return textview;
            }
        };
        ccustomer.setAdapter(adapter);
    }
}
