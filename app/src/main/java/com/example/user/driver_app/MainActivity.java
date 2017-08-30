package com.example.user.driver_app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by user on 24/8/17.
 */

public class MainActivity extends TabActivity {


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
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //setContentView(R.layout.activity_main);



      TabHost mTabHost = getTabHost();

      mTabHost.addTab(mTabHost.newTabSpec("first").setIndicator("Sheduled").setContent(new Intent(this, TripActivity.class)));
      mTabHost.addTab(mTabHost.newTabSpec("second").setIndicator("Completed").setContent(new Intent(this, RideCompletedActivity.class)));
      mTabHost.setCurrentTab(0);


      mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#1988e9"));
      mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#1988e9"));


  }



}