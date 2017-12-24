package com.example.shenhaichen.capstone_project_accountbook.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.adapter.DetailAccountAdapter;
import com.example.shenhaichen.capstone_project_accountbook.bean.DetailAccount;
import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.database.DatabaseContract;
import com.example.shenhaichen.capstone_project_accountbook.database.TaskContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class HandleDailyDataUtil implements DetailAccountAdapter.OnItemClickListener {

    private Context context;
    private RecyclerView recyclerView;
    private int activityStyle;
    private DetailAccountAdapter adapter;
    private Activity activity;
    private InfoSource infoSource;
    private ArrayList<DetailAccount> list;

    public HandleDailyDataUtil(Context context, RecyclerView recyclerView, int activityStyle, Activity activity) {
        this.context = context;
        this.recyclerView = recyclerView;
        //确定是(天/周/月/年)的activity
        this.activityStyle = activityStyle;
        this.activity = activity;
        infoSource = new InfoSource(context);
        init();

    }

    /**
     * 这个方法是读入数据，然后根据要求，设置如天/周/月/年的activity之中
     */
    public void init() {
        Calendar calendar = Calendar.getInstance();
        list = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{DatabaseContract.ACCOUNT_ID, DatabaseContract.ACCOUNT_AMOUNT, DatabaseContract.ACCOUNT_CATEGORY, DatabaseContract.ACCOUNT_PAYMENT,
                        DatabaseContract.ACCOUNT_COMMENT, DatabaseContract.ACCOUNT_DAY, DatabaseContract.ACCOUNT_WEEK, DatabaseContract.ACCOUNT_MONTH, DatabaseContract.ACCOUNT_STYLE, DatabaseContract.ACCOUNT_CURRENCY}, null,
                null, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int month = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_MONTH)));
            int week = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_WEEK)));
            int day = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_DAY)));

            String category = cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_CATEGORY));
            String payment = cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_PAYMENT));
            String style = cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_STYLE));

            switch (activityStyle) {
                //for day activity
                case 1:
                    if (month == (calendar.get(Calendar.MONTH) + 1)) {
                        if (day == (calendar.get(Calendar.DAY_OF_MONTH))) {
                            list.add(new DetailAccount(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)), infoSource.getCategoryImage(category), category,
                                    infoSource.getPayment(payment), cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)),
                                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_COMMENT)), style, day + "日/" + infoSource.changeMonth(month),
                                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY))));
                        }
                    }
                    break;
                // week activity
                case 2:
                    if (month == (calendar.get(Calendar.MONTH) + 1)) {
                        if (week == (calendar.get(Calendar.WEEK_OF_MONTH))) {
                            list.add(new DetailAccount(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)), infoSource.getCategoryImage(category), category,
                                    infoSource.getPayment(payment), cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)),
                                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_COMMENT)), style, day + "日/" + infoSource.changeMonth(month),
                                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY))));
                        }
                    }

                    break;
                // month activity
                case 3:
                    if (month == (calendar.get(Calendar.MONTH) + 1)) {
                        list.add(new DetailAccount(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)), infoSource.getCategoryImage(category), category,
                                infoSource.getPayment(payment), cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_COMMENT)), style, day + "日/" + infoSource.changeMonth(month),
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY))));
                    }
                    break;
                // year activity
                case 4:
                    list.add(new DetailAccount(cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)), infoSource.getCategoryImage(category), category,
                            infoSource.getPayment(payment), cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)),
                            cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_COMMENT)), style, day + "日/" + infoSource.changeMonth(month),
                            cursor.getString(cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY))));

                    break;
            }

        }
        if (list.size() == 0) {
            noData(list);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new DetailAccountAdapter(list, context);
        adapter.setOnItemClicklistener(this);
        recyclerView.setAdapter(adapter);

    }

    /**
     * 如果没数据，就会显示这个
     *
     * @param list
     */
    public void noData(ArrayList<DetailAccount> list) {
        list.add(new DetailAccount(null, infoSource.getCategoryImage("Empty"), context.getString(R.string.no_data),
                null, null, null, null, null, null));
    }

    /**
     * 这个对话框是删除单个数据
     *
     * @param id
     */
    public void dialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.delete) + "?");
        builder.setTitle(context.getString(R.string.hint));
        builder.setPositiveButton(context.getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = TaskContract.TaskEntry.ACCOUNT_BOOK_URI;
                uri = uri.buildUpon().appendPath(id).build();

                int successfullyDelete = context.getContentResolver().delete(uri, null, null);

                if (successfullyDelete != 0) {
                    Toast.makeText(context, context.getString(R.string.success_clean), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    cleanData(id);

                } else {
                    Toast.makeText(context, context.getString(R.string.clean_failed), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * this listener is for RecyclerView of each activity
     *
     * @param view
     * @param detailItem
     */
    @Override
    public void onClick(View view, DetailAccount detailItem) {
        String id = detailItem.getId();
        //点击单个数据，而后进行删除
        dialog(id);
    }

    //删除数据，并更新adapter
    private void cleanData(String id) {
        Iterator<DetailAccount> sListIterator = list.iterator();
        while (sListIterator.hasNext()) {
            DetailAccount e = sListIterator.next();
            if (e.getId().equals(id)) {
                sListIterator.remove();
            }
        }

        if (list.size() == 0) {
            noData(list);
        }
        adapter.setmData(list);
    }
}
