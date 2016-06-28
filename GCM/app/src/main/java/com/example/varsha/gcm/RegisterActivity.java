package com.example.varsha.gcm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnGCMRegister;
    Button btnChat;
    GoogleCloudMessaging gcm;
    Context context;
    String regId;
    String toEventId;
    String mPhoneNumber;
    RequestQueue queue;

    // final String GOOGLE_PROJECT_ID = "sender_id";
    public static final String REG_ID = "regId";
    private static final String  APP_VERSION = "appVersion";
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static final String TAG = "REGISTER ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            context = getApplicationContext();
            queue = Volley.newRequestQueue(this);

            sharedPreferences = getSharedPreferences("Hello", Context.MODE_PRIVATE);
            mPhoneNumber = sharedPreferences.getString("phone", "");

        /*btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
        btnGCMRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(regId))   {

                    regId = registerGCM();
                    Log.d(TAG,"GCM Registered");
                    Intent i = new Intent(getApplicationContext(),
                            MapsActivity.class);
                    Log.d(TAG,
                            "Intent sent to MapsActivity");
                    startActivity(i);

                }else   {
                    Toast.makeText(getApplicationContext(),"Already registered with GCM Server!", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        btnChat = (Button) findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (TextUtils.isEmpty(regId)) {
                    /*Toast.makeText(getApplicationContext(), "RegId is empty!",
                            Toast.LENGTH_LONG).show();*/
                    regId = registerGCM();
                    Log.d(TAG,"GCM Registered");
                    Intent i = new Intent(getApplicationContext(),
                            ChatActivity.class);
                    Log.d(TAG,
                            "Started chat with new regID");
                    try {
                        postRegId();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(i);
                    finish();
                } else {
                    //messageSender.sendMessage(data,gcm);
                    Intent i = new Intent(getApplicationContext(),
                            ChatActivity.class);
                    Log.d(TAG,
                            "Started Chat with existing regID");
                    startActivity(i);
                    finish();
                    /*Log.d(TAG, "onClick of Login: After finish.");
                    sendMessage("REGISTER");*/
                }
            }
        });

    }


    public String registerGCM() {
        gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId(context);
        if (TextUtils.isEmpty(regId))   {
            registerInBackground();
            Log.d("RegisterActivity",
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            Toast.makeText(getApplicationContext(),
                    "RegId already available. RegId: " + regId,
                    Toast.LENGTH_LONG).show();
            Log.d(TAG,"RegId"+regId);
        }
        return regId;

    }

    private String getRegistrationId(Context context)   {
        final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty())   {
            Log.i(TAG,"Registration not found");
            return "";

        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion)    {
            Log.i(TAG, "App version changed");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context)   {
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(getString(R.string.gcm_sender_id));
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;
                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("RegisterActivity", "Error: " + msg);
                }
                Log.d("RegisterActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();
            }
        }.execute();
    }

    private void storeRegistrationId(Context context, String regId) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version" +appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }

    public void postRegId() throws JSONException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("eventId", "2");
        params.put("number", mPhoneNumber);
        params.put("regId", regId);

        String url = "http://52.53.190.143:3000/register";
        //Log.d("POST", "SenderNumber" + mPhoneNumber);
        Log.d("POST", "registerID" + regId);
        Log.d("POST", "RegId sent");
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


}

