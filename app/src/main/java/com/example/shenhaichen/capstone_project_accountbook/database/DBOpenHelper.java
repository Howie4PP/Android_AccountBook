package com.example.shenhaichen.capstone_project_accountbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String DBNAME = "accountbook.db";

    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table accountbook (id integer primary key AUTOINCREMENT, amount text,"
                + "category text,payment integer, comment text, year text,"
                + "month text, week text, day text, currency text, style Integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}