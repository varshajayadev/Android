package com.cmpe277.varshajayadev.androiddatastorage;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public int counter1 = 0;
    public int counter2 = 0;
    public void preferences(View view){
        counter1++;
        Intent intent = new Intent(this, Shared_Preference.class);
        startActivityForResult(intent, 2);
    }
    public void sqlite(View view){
        counter2++;
        Intent intent = new Intent(this, sqlite.class);
        startActivityForResult(intent, 3);
    }
    public void close(View view) {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume()  {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)   {
        super.onActivityResult(requestCode, resultCode, data);
        TextView notifi = (TextView) findViewById(R.id.notification);
        Resources res = getResources();

        if(resultCode == 2) {
            Log.d("ActivityResult","Preference");
            String counter = Integer.toString(counter1);
            String date = data.getStringExtra("date1");
            Log.d("Main", "Date: "+date);
            String notif = String.format(res.getString(R.string.notify1), counter,date);
            notifi.setText(notif);
        }
        else if (resultCode == 3)   {
            Log.d("ActivityResult","SQLite");
            String counter = Integer.toString(counter2);
            String date = data.getStringExtra("date2");
            String notif = String.format(res.getString(R.string.notify2), counter,date);
            notifi.setText(notif);
        }
    }
}
