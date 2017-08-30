package com.example.user.driver_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 28/8/17.
 */

public class Dispatcher extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Class<?> activityClass;
        try
        {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(prefs.getString("lastActivity", LoginActivity.class.getName()));
        } catch (ClassNotFoundException e)
        {
            activityClass = LoginActivity.class;
        }
        Intent i = new Intent(Dispatcher.this, activityClass);
        startActivity(i);
    }
}

