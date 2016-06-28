package com.cmpe277.varshajayadev.clapapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends Activity implements SensorEventListener{
     SensorManager mSensorManager;
     Sensor mSensor;
     MediaPlayer mPlay;

    public void close(View view)    {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mPlay = MediaPlayer.create(this, R.raw.applause);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            Toast.makeText(getApplicationContext(), "sensor on!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "no sensor", Toast.LENGTH_SHORT).show();
        }
        Log.d("Values","OnCreate is exected!"+mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));

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

    protected void OnResume()   {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Values", "onResume is executed");
    }

    protected void onPause()    {
        super.onPause();
        mSensorManager.unregisterListener(this);
        Log.d("Values", "onPause is executed");
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("Sensorvalues","onSensorChanged is executed !!!");
            if (event.values[0] == 0) {
                mPlay.start();

                Log.d("Values", "Near" + event.values[0]);
                Toast.makeText(getApplicationContext(), "Near", Toast.LENGTH_SHORT).show();
            } else {
                mPlay.start();
                mPlay.pause();
                Log.d("Values", "Far"+ event.values[0]);
                Toast.makeText(getApplicationContext(), "Far", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
