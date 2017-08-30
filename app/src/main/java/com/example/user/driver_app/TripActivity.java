package com.example.user.driver_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 21/8/17.
 */

public class TripActivity extends AppCompatActivity implements ListView.OnItemClickListener
{

    private int check=0;
    String[] sample = new String[]{
            "Nothing to show"

    };


    private ListView lcustomer;
    private String apiKey="mongodb786";
    private String veName,vNumber,driverName;
    private List<BookingDetails> bookingDetailsList;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        getSupportActionBar().hide();
        lcustomer = (ListView)findViewById(R.id.list_customer);

        bookingDetailsList=new ArrayList<>();


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");



        SharedPreferences sharedPreferences = getSharedPreferences("key",0);
        veName=sharedPreferences.getString("driverName","");
        vNumber=sharedPreferences.getString("vehicleNumber","");


        Toast.makeText(getApplicationContext(),veName+vNumber,Toast.LENGTH_SHORT).show();

        lcustomer.setOnItemClickListener(this);

        getBookingDetails();


    }



    public void getBookingDetails()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.223.214.194/projects/CabAdmin/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service =retrofit.create(ApiService.class);

        Call<List<BookingDetails>> call =service.getBookingDetails(apiKey,veName);
        call.enqueue(new Callback<List<BookingDetails>>() {
            @Override
            public void onResponse(Call<List<BookingDetails>> call, Response<List<BookingDetails>> response) {
                if (response.body() == null) {
                    check = 1;
                    final List<String> sampleArrayList = new ArrayList<String>(Arrays.asList(sample));
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripActivity.this, android.R.layout.simple_list_item_1, sampleArrayList);
                    lcustomer.setAdapter(adapter);
                } else {
                    List<BookingDetails> list = response.body();
                    BookingDetails bookings = null;
              //      Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_SHORT).show();

                    bookingDetailsList = list;

                    for (int i = 0; i < list.size(); i++) {
                        bookings = new BookingDetails();

                        String date = list.get(i).getDate();
                        String time = list.get(i).getTime();
                        String pickUp = list.get(i).getPickUp();
                        String vehicle = list.get(i).getVehicle();
                        String riderPhone = list.get(i).getRiderPhone();
                        String email = list.get(i).getEmail();
                        String status = list.get(i).getStatus();
                        String assignDriver = list.get(i).getAssignDriver();

                        bookings.setDate(date);
                        bookings.setTime(time);
                        bookings.setPickUp(pickUp);
                        bookings.setVehicle(vehicle);
                        bookings.setRiderPhone(riderPhone);
                        bookings.setEmail(email);
                        bookings.setStatus(status);
                        bookings.setAssignDriver(assignDriver);


                        //  bookingDetailsList.add(bookings);


                    }

                    showBookingList();

                }
            }

            @Override
            public void onFailure(Call<List<BookingDetails>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No scheduled trip ",Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void showBookingList()
    {
        String[] items =new String[bookingDetailsList.size()];

        for(int i=0; i<bookingDetailsList.size();i++)
        {
            items[i] =bookingDetailsList.get(i).getEmail()+"\n"+bookingDetailsList.get(i).getDate()+"\n"+bookingDetailsList.get(i).getTime();
        }

        ArrayAdapter adapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_list,items)
        {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textview = (TextView) view;
                Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/UbuntuLight.ttf");

                textview.setTypeface(tf);
                return textview;
            }
        };

        lcustomer.setAdapter(adapter);






    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Creating an intent
        if (check == 0) {
            Intent intent = new Intent(this, View_Activity.class);

            //Getting the requested book from the list
            BookingDetails bookingDetails = bookingDetailsList.get(position);

            //Adding book details to intent
            intent.putExtra("email", bookingDetails.getEmail());
            intent.putExtra("date", bookingDetails.getDate());
            intent.putExtra("time", bookingDetails.getTime());
            intent.putExtra("pickup", bookingDetails.getPickUp());
            intent.putExtra("vehicle", bookingDetails.getVehicle());
            intent.putExtra("riderphone", bookingDetails.getRiderPhone());


            //Starting another activity to show book details
            startActivity(intent);
        }
        else
        {
            Toast.makeText(TripActivity.this,"Sorry",Toast.LENGTH_LONG).show();
        }
    }







}
