package com.example.varsha.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //ShareExternalServer appUtil;
    String regId;
    AsyncTask shareRegidTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //appUtil = new ShareExternalServer();
        regId = getIntent().getStringExtra("regId");

        final Context context = this;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                //String result = appUtil.shareRegIdWithAppServer(context, regId);
                //return result;
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
               // shareRegidTask = null;
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();
            }

        }.execute();

    }
}
