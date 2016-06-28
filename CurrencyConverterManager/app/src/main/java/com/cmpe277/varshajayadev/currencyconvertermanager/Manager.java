package com.cmpe277.varshajayadev.currencyconvertermanager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Manager extends AppCompatActivity

{
    private String dollar_amt;
    private String convert_to;

    public void convert(View view)  {
        double dollar = Double.parseDouble(dollar_amt);
        double amount=0;
        if(convert_to.equals("Indian Rupees"))  {
            amount = dollar * 67.00;
        }
        else if (convert_to.equals("British Pound"))    {
            amount = dollar * 0.70;
        }
        else if (convert_to.equals("Euro"))     {
            amount = dollar * 0.91;
        }
        else if (convert_to.equals("Australian Dollars"))   {
            amount = dollar * 1.34;
        }
        Log.d("Amount", "Value:" + amount);
        String amnt = Double.toString(amount);
        Log.d("CC", "Intent sent");
        Intent in = new Intent();
        in.setAction("com.cmpe277.varshajayadev.BroadcastReceiverCC");
        in.putExtra("amount", amnt);
        sendBroadcast(in);

    }
     public void close(View view)   {
         finish();
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("CCM", "onCreate");
        Intent intent = getIntent();
        dollar_amt = intent.getStringExtra("dollars");
        Log.d("Dollar_amount", "da:" + dollar_amt);
        TextView dollars = (TextView) findViewById(R.id.dollar_amount);
        dollars.setText(dollar_amt);
        convert_to = intent.getStringExtra("convert_to");
        TextView convert = (TextView) findViewById(R.id.convert_to);
        convert.setText(convert_to);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manager, menu);
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


    // Called before subsequent visible lifetimes
    // for an activity process.
    @Override
    public void onRestart(){
        super.onRestart();
        // Load changes knowing that the Activity has already
        // been visible within this process.
        Log.d("Value:", "CCM: Restart");


    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        // Apply any required UI change now that the Activity is visible.
        Log.d("Value:", "CCM: Start");


    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        // Resume any paused UI updates, threads, or processes required
        // by the Activity but suspended when it was inactive.
        Log.d("Value:", "CCM: Resume");


    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate and
        // onRestoreInstanceState if the process is
        // killed and restarted by the run time.
        Log.d("Value:", "CCM: Save");
        super.onSaveInstanceState(savedInstanceState);

    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground Activity.
        Log.d("Value:","CCM: Pause");
        super.onPause();
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Activity isn't visible.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        Log.d("Value:","CCM: Stop");
        super.onStop();
    }

    // Sometimes called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        // Clean up any resources including ending threads,
        // closing database connections etc.
        Log.d("Value:","CCM: Destroy");
        super.onDestroy();
    }
}
