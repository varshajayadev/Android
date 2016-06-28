package com.example.varsha.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    GoogleCloudMessaging gcm;
    Intent intent;
    RequestQueue queue;

    String toEventId;
    String mPhoneNumber;
    DbHelper db;
    SQLiteDatabase myDb;
    ArrayList<String> list;
    private NotificationManager mNotificationManager;
    private SharedPreferences sharedPreferences;
    public  static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        queue = Volley.newRequestQueue(this);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        intent = new Intent(this, GCMNotificationIntentService.class);
        sharedPreferences = getSharedPreferences("Hello", Context.MODE_PRIVATE);
        mPhoneNumber = sharedPreferences.getString("phone", "");
        //registerReceiver(broadcastReceiver, new IntentFilter("com.example.varsha.gcm.chatmessage"));

        listView = (ListView) findViewById(R.id.listView1);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        db = new DbHelper(this);
        list = new ArrayList<String>();
        list =db.getChatMessages();

        for( String j : list) {
            String[] data;
            data = j.split("-");

            if (j.contains("true")) { //change this to true

                Log.d(TAG,"FromDB "+data[0]+data[1]+data[2]);
                chatArrayAdapter.add(new ChatMessage(true, data[1], data[0]));

            }else   {

                chatArrayAdapter.add(new ChatMessage(false, data[1], data[0]));

            }
        }


        chatText = (EditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver(){

            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

    }

    @Override
    protected void onPause()    {
        super.onPause();
        sendNotification("");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        myDb.close();

    }

    @Override
    public void onStart()   {
        super.onStart();
    }
    @Override
    public  void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("com.example.varsha.gcm.chatmessage"));
        myDb = db.getWritableDatabase();
    }
    private boolean sendChatMessage() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(db.EVENT_ID, "2");
        contentValues.put(db.NUMBER, mPhoneNumber);
        contentValues.put(db.CHATMESSAGE, chatText.getText().toString() );
        contentValues.put("left", "false");
        myDb.insert(db.TABLE_NAME, null, contentValues);
        Toast.makeText(ChatActivity.this, "Added to database", Toast.LENGTH_SHORT).show();
        try {
            postChatMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        chatText.setText("");
        return true;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "OnReceive: "+intent.getStringExtra("msg"));

            ContentValues contentValues = new ContentValues();
            contentValues.put(db.EVENT_ID, intent.getStringExtra("eventId"));
            contentValues.put(db.NUMBER, intent.getStringExtra("number"));
            contentValues.put(db.CHATMESSAGE, intent.getStringExtra("msg") );

            if(mPhoneNumber.equalsIgnoreCase(intent.getStringExtra("number")))    {

                chatArrayAdapter.add(new ChatMessage(false, intent.getStringExtra("msg"), intent.getStringExtra("number") ));
                contentValues.put("left", "false");

            }else   {

                chatArrayAdapter.add(new ChatMessage(true, intent.getStringExtra("msg"), intent.getStringExtra("number") ));
                contentValues.put("left", "true");
            }
            /*chatArrayAdapter.add(new ChatMessage(true, intent.getStringExtra("msg"), intent.getStringExtra("number") ));
            contentValues.put("left", "true");*/
            myDb.insert(db.TABLE_NAME, null, contentValues);
        }
    };

    @Override
    protected void onDestroy()  {
        super.onDestroy();
    }
    public void postChatMessage() throws JSONException {
        //TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        //mPhoneNumber = tMgr.getLine1Number();
        Map<String, String> params = new HashMap<String, String>();
        params.put("eventId", "2");
        params.put("number", mPhoneNumber);
        params.put("msg", chatText.getText().toString());

        String url = "http://52.53.190.143:3000/msg";
        Log.d("POST", "SenderNumber" + mPhoneNumber);
        Log.d("POST", "ChatMessage sent"+params);
        Log.d("POST", chatText.getText().toString());
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", "Response received: " + response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error: " + error);
            }
        });
        queue.add(postRequest);
    }

    private  void sendNotification(String msg)  {
        Log.d(TAG, "Preparing to send notification...: " + msg);
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.mipmap.gcm_cloud)
                .setContentTitle("Chat Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d(TAG, "Notification sent successfully.");
    }

}
