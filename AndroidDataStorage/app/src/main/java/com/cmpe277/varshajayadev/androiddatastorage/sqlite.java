package com.cmpe277.varshajayadev.androiddatastorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class sqlite extends AppCompatActivity implements View.OnClickListener {

    MyDbHelper mydbhelper;
    SQLiteDatabase mDb;
    EditText book_name;
    EditText book_auth;
    EditText book_desc;
    ListView mList;

    public String date2;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");
    public void close(View view)    {
                finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        book_name = (EditText)findViewById(R.id.book_name);
        book_auth = (EditText)findViewById(R.id.book_author);
        book_desc = (EditText)findViewById(R.id.description);
        Button save = (Button)findViewById(R.id.save);
        mList = (ListView)findViewById(R.id.list);
        save.setOnClickListener(this);
        mydbhelper = new MyDbHelper(this);
        Log.d("DB","onCreate");
    }

    @Override
    public void onResume()  {
        super.onResume();
        mDb = mydbhelper.getWritableDatabase();
        Log.d("DB","onResume");
    }

    @Override
    public void onPause()   {
        super.onPause();
        mDb.close();
    }
    @Override
    public void onClick(View v) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mydbhelper.BOOKS_BOOK_NAME, book_name.getText().toString());
        contentValues.put(mydbhelper.BOOKS_BOOK_AUTHOR, book_auth.getText().toString());
        contentValues.put(mydbhelper.BOOKS_BOOK_DESCRIPTION, book_desc.getText().toString());
        mDb.insert(mydbhelper.BOOKS_TABLE_NAME, null, contentValues);
        Intent i = new Intent();
        date2 = s.format(new Date());
        i.putExtra("date2", date2);
        setResult(3, i);
        Toast.makeText(sqlite.this, "Added to database", Toast.LENGTH_SHORT).show();



    }

}
