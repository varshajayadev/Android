package com.example.varsha.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Varsha on 5/3/2016.
 */
public class GCMNotificationIntentService extends IntentService {

    public  static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    public static final String TAG = "GCMNotificationIntentServ";
    private SharedPreferences sharedPreferences;
    String mPhoneNumber;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        sharedPreferences = getSharedPreferences("Hello", Context.MODE_PRIVATE);
        mPhoneNumber = sharedPreferences.getString("phone", "");
        if(!extras.isEmpty())   {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))   {

                sendNotification("send error: "+ extras.toString());
                Log.d(TAG, "onHandleIntent - Error ");

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))    {
                sendNotification("Deleted messages on server: "+ extras.toString());

            }else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))    {

                if(mPhoneNumber.equalsIgnoreCase(extras.get("number").toString()))    {
                    Log.d(TAG,"Error response for converting");
                }else   {
                    sendNotification("NEW MESSAGE" + extras.get("msg"));
                }
                sendNotification("NEW MESSAGE" + extras.get("msg"));
                Log.i(TAG, "SERVER_MESSAGE: " + extras.toString());
                Log.d(TAG, "onHandleIntent - CHAT ");
                Intent chatIntent = new Intent("com.example.varsha.gcm.chatmessage");

                    chatIntent.putExtra("msg",extras.get("msg").toString());
                    chatIntent.putExtra("number",extras.get("number").toString());
                    chatIntent.putExtra("eventId",extras.get("eventId").toString());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(chatIntent);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    private  void sendNotification(String msg)  {
        Log.d(TAG, "Preparing to send notification...: " + msg);
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.mipmap.gcm_cloud)
                .setContentTitle("GCM Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d(TAG, "Notification sent successfully.");
    }
}
