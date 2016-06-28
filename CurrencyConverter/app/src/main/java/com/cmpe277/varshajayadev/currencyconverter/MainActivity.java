package com.cmpe277.varshajayadev.currencyconverter;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public String dollars_amt;
    public String convert_to;
    public String amount;
    public void broadcast(View view){

        Log.d("CC", "Intent sent");
        Intent intent = new Intent();
        intent.setAction("com.cmpe277.varshajayadev.BroadcastReceiverCCM");
        Log.d("Inside intent", "Intent:" + intent);
        RadioButton ir = (RadioButton) findViewById(R.id.indian_rupees);
        RadioButton bp = (RadioButton) findViewById(R.id.british_pound);
        RadioButton eu = (RadioButton) findViewById(R.id.euro);
        RadioButton ad = (RadioButton) findViewById(R.id.australian_dollars);

                if(ir.isChecked()) {
                    intent.putExtra("convert_to","Indian Rupees");
                    convert_to="Indian Rupees";
                }
                else if(bp.isChecked()) {
                    intent.putExtra("convert_to","British Pound");
                    convert_to="British Pound";
                }
                else if (eu.isChecked())    {
                    intent.putExtra("convert_to","Euro");
                    convert_to="Euro";
                }
                else if (ad.isChecked()) {
                    intent.putExtra("convert_to", "Australian Dollars");
                    convert_to="Australian Dollars";
                }

        EditText dollars = (EditText)findViewById(R.id.dollar_amount);
        dollars_amt = dollars.getText().toString();

        intent.putExtra("Dollar_amount",dollars_amt);
        Log.d("value","CC:DOllar"+dollars_amt);
        sendBroadcast(intent);

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
        Button reset = (Button) findViewById(R.id.reset);
        final EditText enter_dollars = (EditText)findViewById(R.id.dollar_amount);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_dollars.setText("");
            }
        });

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

    @Override
    public void onResume()  {
        super.onResume();
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        Log.d("amount", "da:" + amount);

        TextView notification_text = (TextView) findViewById(R.id.notification);
        Resources res = getResources();
        String notif = String.format(res.getString(R.string.notify), amount);
        if(MyReceiver.Flag) {
            notification_text.setText(notif);
        }
        else
        {
            notification_text.setText("");
        }
    }
}
