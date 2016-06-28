package com.kknc.varshajayadev.kknc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    private GoogleApiClient client;
    ArrayList<String> eventname = new ArrayList<String>();
    ArrayList<String> eventplacename = new ArrayList<String>();
    ArrayList<String> eventlattitude = new ArrayList<String>();
    ArrayList<String> eventlongitude = new ArrayList<String>();
    ArrayList<String> eventdatetime = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        callbackManager = CallbackManager.Factory.create();
        final SharedPreferences sharedpreferences = getSharedPreferences("Access_token_Preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Access_Token",AccessToken.getCurrentAccessToken().toString());
        editor.commit();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                AccessToken.setCurrentAccessToken(currentAccessToken);
                String stored_Access_Token = sharedpreferences.getString("Access_Token", "");
                if(stored_Access_Token.equals(AccessToken.getCurrentAccessToken().toString()))    {

                    Log.d("MainActivity","AccessToken matches!");

                } else {
                    Log.d("MainActivity","AccessTokens does not match!");
                }
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        List<String> permissionNeeds = Arrays.asList("user_events");
        LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /* make the API call */
                new GraphRequest(
                        accessToken,
                        "/41850518295/events",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                            /* handle the result */
                                try {

                                    JSONObject json = new JSONObject(response.getRawResponse());
                                    JSONArray data = json.getJSONArray("data");

                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject events = data.getJSONObject(i);
                                        String event_name = events.getString("name").toString();
                                        eventname.add(event_name);
                                        Log.d("JSON", "evename string: " + event_name);
                                        String datetime = events.getString("start_time").toString();
                                        eventdatetime.add(datetime);

                                        JSONObject event_place = events.getJSONObject("place");
                                        String event_place_name = event_place.getString("name").toString();
                                        eventplacename.add(event_place_name);
                                        Log.d("JSON", "eveplacename string: " + event_place_name);
                                        JSONObject event_location = event_place.getJSONObject("location");
                                        String event_lati = event_location.getString("latitude").toString();
                                        eventlattitude.add(event_lati);
                                        String event_long = event_location.getString("longitude").toString();
                                        eventlongitude.add(event_long);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("ARList", "eventname" + eventname.get(1));
                                Log.d("ARList", "eventplacename" + eventplacename.get(1));
                                Log.d("ARList", "eventdatetime" + eventdatetime.get(1));
                                Log.d("ARList", "eventlat" + eventlattitude.get(1));
                                Log.d("ARList", "eventlong" + eventlongitude.get(1));
                                Intent intent = new Intent(MainActivity.this, EventsActivity.class);
                                intent.putStringArrayListExtra("event_name", eventname);
                                intent.putStringArrayListExtra("event_place_name", eventplacename);
                                intent.putStringArrayListExtra("event_datetime", eventdatetime);
                                intent.putStringArrayListExtra("event_latitude", eventlattitude);
                                intent.putStringArrayListExtra("event_longitude", eventlongitude);
                                startActivity(intent);

                            }
                        }
                ).executeAsync();
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.kknc.varshajayadev.kknc/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.kknc.varshajayadev.kknc/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
