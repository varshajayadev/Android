package com.kknc.varshajayadev.kknc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
private TextView event_1;
private TextView event_2;
private TextView event_3;
private TextView event_4;
private TextView event_5;
    ArrayList<String> event_name_list = new ArrayList<String>();
    ArrayList<String> event_place_list = new ArrayList<String>();
    ArrayList<String> event_latitude_list = new ArrayList<String>();
    ArrayList<String> event_longitude_list = new ArrayList<String>();
    ArrayList<String> event_datetime_list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        event_1 = (TextView) findViewById(R.id.event1);
        event_2 = (TextView) findViewById(R.id.event2);
        event_3 = (TextView) findViewById(R.id.event3);
        event_4 = (TextView) findViewById(R.id.event4);
        event_5 = (TextView) findViewById(R.id.event5);
        Intent i = getIntent();
        event_name_list = i.getStringArrayListExtra("event_name");
        event_place_list = i.getStringArrayListExtra("event_place_name");
        event_datetime_list = i.getStringArrayListExtra("event_datetime");
        event_latitude_list = i.getStringArrayListExtra("event_latitude");
        event_longitude_list = i.getStringArrayListExtra("event_longitude");


        String name_1 = event_name_list.get(0);
        String place_1 = event_place_list.get(0);
        String datetime_1 = event_datetime_list.get(0);
        DateTime datetime1 = new DateTime(datetime_1);
        String date1 = datetime1.toString("MMM dd yyyy", Locale.US);
        String time1 = datetime1.toString("hh:mm a", Locale.US);
        StringBuilder builder1 = new StringBuilder();
        builder1.append(name_1 + "\n").append(date1 + " at ").append(time1 + "\n").append(place_1);
        event_1.setText(builder1.toString());

        String name_2 = event_name_list.get(1);
        String place_2 = event_place_list.get(1);
        String datetime_2 = event_datetime_list.get(1);
        DateTime datetime2 = new DateTime(datetime_2);
        String date2 = datetime2.toString("MMM dd yyyy", Locale.US);
        String time2 = datetime2.toString("hh:mm a", Locale.US);
        StringBuilder builder2 = new StringBuilder();
        builder2.append(name_2 + "\n").append(date2 + " at ").append(time2 + "\n").append(place_2);
        event_2.setText(builder2.toString());

        String name_3 = event_name_list.get(2);
        String place_3 = event_place_list.get(2);
        String datetime_3 = event_datetime_list.get(2);
        DateTime datetime3 = new DateTime(datetime_3);
        String date3 = datetime3.toString("MMM dd yyyy", Locale.US);
        String time3 = datetime3.toString("hh:mm a", Locale.US);
        StringBuilder builder3 = new StringBuilder();
        builder3.append(name_3 + "\n").append(date3 + " at ").append(time3 + "\n").append(place_3);
        event_3.setText(builder3.toString());

        String name_4 = event_name_list.get(3);
        String place_4 = event_place_list.get(3);
        String datetime_4 = event_datetime_list.get(3);
        DateTime datetime4 = new DateTime(datetime_4);
        String date4 = datetime4.toString("MMM dd yyyy", Locale.US);
        String time4 = datetime4.toString("hh:mm a", Locale.US);
        StringBuilder builder4 = new StringBuilder();
        builder4.append(name_4 + "\n").append(date4 + " at ").append(time4 + "\n").append(place_4);
        event_4.setText(builder4.toString());

        String name_5 = event_name_list.get(4);
        String place_5 = event_place_list.get(4);
        String datetime_5 = event_datetime_list.get(4);
        DateTime datetime5 = new DateTime(datetime_5);
        String date5 = datetime5.toString("MMM dd yyyy", Locale.US);
        String time5 = datetime5.toString("hh:mm a", Locale.US);
        StringBuilder builder5 = new StringBuilder();
        builder5.append(name_5 + "\n").append(date5 + " at ").append(time5 + "\n").append(place_5);
        event_5.setText(builder5.toString());
    }

    public void directions_1(View view)   {
        Double latt =  Double.parseDouble(event_latitude_list.get(0));
        Double longi = Double.parseDouble(event_longitude_list.get(0));
        show_directions(latt,longi);
    }
    public void directions_2(View view)   {
        Double latt =  Double.parseDouble(event_latitude_list.get(1));
        Double longi = Double.parseDouble(event_longitude_list.get(1));
        show_directions(latt,longi);
    }
    public void directions_3(View view)   {
        Double latt =  Double.parseDouble(event_latitude_list.get(2));
        Double longi = Double.parseDouble(event_longitude_list.get(2));
        show_directions(latt,longi);
    }
    public void directions_4(View view)   {
        Double latt =  Double.parseDouble(event_latitude_list.get(3));
        Double longi = Double.parseDouble(event_longitude_list.get(3));
        show_directions(latt,longi);
    }
    public void directions_5(View view)   {
        Double latt =  Double.parseDouble(event_latitude_list.get(4));
        Double longi = Double.parseDouble(event_longitude_list.get(4));
        show_directions(latt,longi);
    }
    public void show_directions(double latitude, double longitude)   {
        Uri gmmIntentUri = Uri.parse(String.format("google.navigation:q=%f,%f",latitude,longitude));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
