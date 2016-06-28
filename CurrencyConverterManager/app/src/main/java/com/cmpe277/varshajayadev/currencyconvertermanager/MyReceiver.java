package com.cmpe277.varshajayadev.currencyconvertermanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Varsha on 3/2/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CCM", "Intent received");
        String da = intent.getExtras().getString("Dollar_amount");
        String conversion = intent.getExtras().getString("convert_to");
        Intent i = new Intent(context, Manager.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("CCM", "Intent:" + i);
        i.putExtra("dollars",da);
        i.putExtra("convert_to", conversion);
        Log.d("CCM", "da:" + da);
        context.startActivity(i);
    }
}
