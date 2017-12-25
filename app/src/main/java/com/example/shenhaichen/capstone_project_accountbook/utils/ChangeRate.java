package com.example.shenhaichen.capstone_project_accountbook.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.database.DatabaseContract;
import com.example.shenhaichen.capstone_project_accountbook.database.TaskContract;

import java.util.ArrayList;

/**
 * Created by shenhaichen on 25/12/2017.
 */

public class ChangeRate {
    private Context context;
    ArrayList<Double> cnyList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();

    public ChangeRate(Context context) {
        this.context = context;
    }

    public void cnyToSGD(){
        cnyList.clear();
        idList.clear();
        ContentValues contentValues = new ContentValues();
        // 从数据库中取出数据
        Cursor sg_cursor = context.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{DatabaseContract.ACCOUNT_ID, DatabaseContract.ACCOUNT_AMOUNT, DatabaseContract.ACCOUNT_CURRENCY}, null,
                null, null);

        for (sg_cursor.moveToFirst(); !sg_cursor.isAfterLast(); sg_cursor.moveToNext()) {
            double cny =  Double.parseDouble(sg_cursor.getString(sg_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
            if (!"$".equals(sg_cursor.getString(sg_cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY)))) {
                cny /= 4.8;
                cnyList.add(cny);
                idList.add(sg_cursor.getString(sg_cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)));
            }
        }
        System.out.println(cnyList.size());
        // 更新数据
        if (!cnyList.isEmpty()) {
            boolean flag = false;
            for (int x = 0; x < cnyList.size(); x++) {
                contentValues.put(DatabaseContract.ACCOUNT_AMOUNT, cnyList.get(x));
                contentValues.put(DatabaseContract.ACCOUNT_CURRENCY, "$");
                int i = context.getContentResolver().update(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                        contentValues, "id = ?",
                        new String[]{idList.get(x)});
                flag = (i >= 0 ? true : false);
            }
            if (flag){
                InfoSource.CURRENCYFORMATE = "$";
                Toast.makeText(context, context.getString(R.string.success_change), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sgdToCNY(){
        cnyList.clear();
        idList.clear();
        ContentValues cnyValues = new ContentValues();
        // 从数据库中取出数据
        Cursor cny_cursor = context.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{DatabaseContract.ACCOUNT_ID, DatabaseContract.ACCOUNT_AMOUNT, DatabaseContract.ACCOUNT_CURRENCY}, null,
                null, null);

        for (cny_cursor.moveToFirst(); !cny_cursor.isAfterLast(); cny_cursor.moveToNext()) {
            double cny =  Double.parseDouble(cny_cursor.getString(cny_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
            if (!"￥".equals(cny_cursor.getString(cny_cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY)))) {
                cny *= 4.8;
                cnyList.add(cny);
                idList.add(cny_cursor.getString(cny_cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)));
            }
        }



        // 更新数据
        if (!cnyList.isEmpty()) {
            boolean flag = false;
            for (int x = 0; x < cnyList.size(); x++) {
                cnyValues.put(DatabaseContract.ACCOUNT_AMOUNT, cnyList.get(x));
                cnyValues.put(DatabaseContract.ACCOUNT_CURRENCY, "￥");

                int i = context.getContentResolver().update(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                        cnyValues, "id = ?",
                        new String[]{idList.get(x)});
                flag = (i >= 0 ? true : false);
            }
            if (flag){
                InfoSource.CURRENCYFORMATE = "￥";
                Toast.makeText(context, context.getString(R.string.success_change), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
