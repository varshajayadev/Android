package com.cmpe277.varshajayadev.androiddatastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Varsha on 5/13/2016.
 */
class MyDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DataStorage2.db";
    public static final String BOOKS_TABLE_NAME = "books";
    public static final String BOOKS_BOOK_ID = "id";
    public static final String BOOKS_BOOK_NAME = "name";
    public static final String BOOKS_BOOK_AUTHOR = "author";
    public static final String BOOKS_BOOK_DESCRIPTION = "description";
    private HashMap hp;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " +BOOKS_TABLE_NAME+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +BOOKS_BOOK_NAME+" text, "+BOOKS_BOOK_AUTHOR+ " text, " +BOOKS_BOOK_DESCRIPTION+" text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BOOKS_TABLE_NAME);
        onCreate(db);
    }

}
