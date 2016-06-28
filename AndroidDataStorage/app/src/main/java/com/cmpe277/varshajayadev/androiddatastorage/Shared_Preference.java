package com.cmpe277.varshajayadev.androiddatastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Shared_Preference extends AppCompatActivity {
    public String date1;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    public void close(View view)    {
                finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared__preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText book_name = (EditText)findViewById(R.id.book_name);
        final EditText book_auth = (EditText)findViewById(R.id.book_author);
        final EditText book_desc = (EditText)findViewById(R.id.description);
        Button save = (Button)findViewById(R.id.save);
        final SharedPreferences sharedpreferences = getSharedPreferences("Preference1", Context.MODE_PRIVATE);

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = book_name.getText().toString();
                String auth = book_auth.getText().toString();
                String desc = book_desc.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Book Name",name);
                editor.putString("Book Author", auth);
                editor.putString("Book Description", desc);
                editor.commit();
                date1 = s.format(new Date());
                Intent i = new Intent();
                i.putExtra("date1", date1);
                setResult(2, i);
                Log.d("DateValue", "Date in close: " + date1);
                Toast.makeText(Shared_Preference.this, "Added to folder", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
