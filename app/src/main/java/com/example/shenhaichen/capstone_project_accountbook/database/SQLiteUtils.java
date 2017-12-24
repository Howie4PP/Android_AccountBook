package com.example.shenhaichen.capstone_project_accountbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class SQLiteUtils {
    private DBOpenHelper mHelper;
    private static String TABLENAME = "accountbook";
    private SQLiteDatabase database;

    public SQLiteUtils(Context context) {
        mHelper = new DBOpenHelper(context);
    }

    /**
     *  clean all data
     */
    public void clean(){
        database = null;
        database = mHelper.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        mHelper.onCreate(database);
        database.close();
    }

}
