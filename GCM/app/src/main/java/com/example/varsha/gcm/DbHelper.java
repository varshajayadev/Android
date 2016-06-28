package com.example.varsha.gcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Varsha on 5/9/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "chatapp1.db";
    public static final String TABLE_NAME = "chats";
    public static final String EVENT_ID = "eventid";
    public static final String NUMBER = "number";
    public static final String CHATMESSAGE = "chatmessage";
    String eventId = "2";
    ArrayList<String> list ;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                /*"create table " +TABLE_NAME+ " (_id TEXT PRIMARY KEY, " +NUMBER+" text, "+CHATMESSAGE+ " text);"  );*/
                "create table chats (_id INTEGER PRIMARY KEY AUTOINCREMENT, eventid TEXT, number TEXT, chatmessage TEXT, left TEXT);");
        Log.d("DbHelper", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    /*public void insertChat(String eventId, String number, String chatMessage, String left)    {
        //String currentDBPath = "/data/"+getApplicationInfo().packageName+"/databases/db_name";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventid", eventId);
        contentValues.put("number", number);
        contentValues.put("chatmessage", chatMessage);
        contentValues.put("left", left);
        db.insert("chats", null, contentValues);
    }*/

    public Cursor getData(String eventId)   {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from chats where eventid="+eventId+"",null);
        return res;
    }

    public ArrayList<String> getChatMessages() {

        String selectQuery = "SELECT  * FROM chats WHERE eventid=" + eventId;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);

        list = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndex("number")) + "-" + cursor.getString(cursor.getColumnIndex("chatmessage")) + "-" + cursor.getString(cursor.getColumnIndex("left"));
                list.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
