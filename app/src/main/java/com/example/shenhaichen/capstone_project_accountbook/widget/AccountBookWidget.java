package com.example.shenhaichen.capstone_project_accountbook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.example.shenhaichen.capstone_project_accountbook.MainActivity;
import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.database.DatabaseContract;
import com.example.shenhaichen.capstone_project_accountbook.database.TaskContract;

/**
 * Implementation of App Widget functionality.
 */
public class AccountBookWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        double totalInValue = 0.00;
        double totalOutValue = 0.00;

        Cursor income_cursor = context.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{"amount"}, "style = ?",
                new String[]{"0"}, null);

        Cursor outcome_cursor = context.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{"amount"}, "style = ?",
                new String[]{"1"}, null);


        for (income_cursor.moveToFirst(); !income_cursor.isAfterLast(); income_cursor.moveToNext()) {
            totalInValue += Double.parseDouble(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
        }

        for (outcome_cursor.moveToFirst(); !outcome_cursor.isAfterLast(); outcome_cursor.moveToNext()) {
            totalOutValue += Double.parseDouble(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.account_book_widget);
        views.setTextViewText(R.id.appwidget_text_income, totalInValue + "");
        views.setTextViewText(R.id.appwidget_text_outcome, totalOutValue + "");

        //打开程序主页面
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pengIntent = PendingIntent.getActivity(context,
                0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text_outcome, pengIntent);
        views.setOnClickPendingIntent(R.id.appwidget_text_income, pengIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

