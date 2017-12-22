package com.example.shenhaichen.capstone_project_accountbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class SQLiteUtils {
    private DBOpenHelper mHelper;
    private static String TABLENAME = "accountbook";
    boolean flag;
    private SQLiteDatabase database;
    private Cursor cursor;
    private JSONArray jsonarray;

    public SQLiteUtils(Context context) {
        mHelper = new DBOpenHelper(context);
    }

    /**
     * insert data to database
     *
     * @param values
     * @return
     */
    public boolean insert(ContentValues values) {
        database = null;
        try {
            database = mHelper.getWritableDatabase();
            long type = database.insert(TABLENAME, null, values);
            flag = (type >= 0 ? true : false);
        } catch (Exception e) {
        } finally {
            database.close();
        }
        return flag;
    }

    /**
     *  update the date, must enter the limited condition
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean update(ContentValues values, String whereClause,
                          String[] whereArgs) {
        database = null;
        try {
            database = mHelper.getReadableDatabase();
            int i = database.update(TABLENAME, values, whereClause, whereArgs);
            flag = (i >= 0 ? true : false);

        } catch (Exception e) {

        } finally {
            database.close();
        }
        return flag;
    }

    /**
     *  deleting single data from database
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean delete(String whereClause, String[] whereArgs) {

        database = null;
        try {
            database = mHelper.getWritableDatabase();
            int i = database.delete(TABLENAME, whereClause, whereArgs);
            flag = (i > 0 ? true : false);

        } catch (Exception e) {

        } finally {
            database.close();
        }

        return flag;
    }

    /**
     *  Query data which base on the condition
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public String select(String[] columns, String selection,
                         String[] selectionArgs) {
        database = null;
        cursor = null;
        jsonarray = new JSONArray();
//        ArrayList<AccountBookInfo> mList = new ArrayList<>();
        try {


            database = mHelper.getWritableDatabase();
            cursor = database.query(TABLENAME, columns, selection, selectionArgs,
                    null, null, null);
            // get the number of data that we need
            int i = cursor.getColumnCount();
            // before loop, have to let the cursor back to the top, bc sometime it will case error
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                JSONObject jsonObject = new JSONObject();
                for (int x = 0; x < i; x++) {
                    String col_name = cursor.getColumnName(x);
                    String col_value = cursor.getString(x);
                    // base on the name of column to name, then send the value out
                    jsonObject.put(col_name, col_value);
                }
                jsonarray.put(jsonObject);
            }
            return jsonarray.toString();
        } catch (Exception e) {
        } finally {
            database.close();
            if (cursor != null)
                cursor.close();
        }
        return null;
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
