package com.cmpe277.varshajayadev.currencyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Varsha on 3/5/2016.
 */
public class MyReceiver extends BroadcastReceiver {

    public static Boolean Flag = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CC", "Intent received");
        Flag = true;
        String amt = intent.getExtras().getString("amount");
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("amount",amt);
        Log.d("CC", "da:" + amt);
        context.startActivity(i);
    }
}
