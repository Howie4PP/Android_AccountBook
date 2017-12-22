package com.example.shenhaichen.capstone_project_accountbook.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.adapter.DetailAccountAdapter;
import com.example.shenhaichen.capstone_project_accountbook.bean.DetailAccount;
import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class HandleDailyDataUtil implements DetailAccountAdapter.OnItemClickListener {
    private SQLiteUtils sqLiteUtils;
    private Context context;
    private RecyclerView recyclerView;
    private int activityStyle;
    private DetailAccountAdapter adapter;
    private Activity activity;

    public HandleDailyDataUtil(Context context, RecyclerView recyclerView, int activityStyle, Activity activity) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.activityStyle = activityStyle;
        this.activity = activity;
        sqLiteUtils = new SQLiteUtils(context);
        init();
    }

    /**
     * this function is read the data from the database, and depends on the style of activity(day/week/month/year)
     * to set the data into different activity
     */
    public void init() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<DetailAccount> list = new ArrayList<>();
        String dayData = sqLiteUtils.select(new String[]{"id", "amount", "category", "payment",
                "comment", "day", "week", "month", "style", "currency"}, null, null);
        if (!dayData.equals("[]")) {
            try {
                JSONArray dayArray = new JSONArray(dayData);
//                int today = calendar.get(Calendar.DAY_OF_MONTH);
//                System.out.println("WholeData"+dayData);
                // find the data of today then adding into the list
                for (int i = 0; i < dayArray.length(); i++) {
                    JSONObject jsonObject = dayArray.getJSONObject(i);
                    int day = Integer.parseInt(jsonObject.getString("day"));
                    int week = Integer.parseInt(jsonObject.getString("week"));
                    int month = Integer.parseInt(jsonObject.getString("month"));
//                    System.out.println("day:"+day);
                    String category = jsonObject.getString("category");
                    String payment = jsonObject.getString("payment");
                    String style = jsonObject.getString("style");
//                    System.out.println("category"+category);
//                    System.out.println("payment"+jsonObject.getString("payment"));
//                    System.out.println("amount"+jsonObject.getString("amount"));
//                    System.out.println("comment"+jsonObject.getString("comment"));
//                    System.out.println("image"+InfoSource.getCategoryImage(category));

                    switch (activityStyle) {
                        //for day activity
                        case 1:
                            if (month == (calendar.get(Calendar.MONTH) + 1)) {
                                if (day == (calendar.get(Calendar.DAY_OF_MONTH))) {
                                    list.add(new DetailAccount(jsonObject.getString("id"), InfoSource.getCategoryImage(category), category,
                                            InfoSource.getPayment(payment), jsonObject.getString("amount"),
                                            jsonObject.getString("comment"), style, day + "/" + InfoSource.changeMonth(month),
                                            jsonObject.getString("currency")));
                                }
                            }
                            break;
                        // week activity
                        case 2:
//                            System.out.println("this month:"+(calendar.get(Calendar.MONTH) + 1));
//                            System.out.println("this week:"+calendar.get(Calendar.WEEK_OF_MONTH));
                            if (month == (calendar.get(Calendar.MONTH) + 1)) {
                                if (week == (calendar.get(Calendar.WEEK_OF_MONTH))) {
                                    list.add(new DetailAccount(jsonObject.getString("id"), InfoSource.getCategoryImage(category), category,
                                            InfoSource.getPayment(payment), jsonObject.getString("amount"),
                                            jsonObject.getString("comment"), style, day + "/" + InfoSource.changeMonth(month),
                                            jsonObject.getString("currency")));
                                }
                            }

                            break;
                        // month activity
                        case 3:
//                            System.out.println("This month:"+(calendar.get(Calendar.MONTH) + 1));
//                            System.out.println("month:"+month);
                            if (month == (calendar.get(Calendar.MONTH) + 1)) {
                                System.out.println("add in month");
                                list.add(new DetailAccount(jsonObject.getString("id"), InfoSource.getCategoryImage(category), category,
                                        InfoSource.getPayment(payment), jsonObject.getString("amount"),
                                        jsonObject.getString("comment"), style, day + "/" + InfoSource.changeMonth(month),
                                        jsonObject.getString("currency")));
                            }
                            break;
                        // year activity
                        case 4:
                            list.add(new DetailAccount(jsonObject.getString("id"), InfoSource.getCategoryImage(category), category,
                                    InfoSource.getPayment(payment), jsonObject.getString("amount"),
                                    jsonObject.getString("comment"), style, day + "/" + InfoSource.changeMonth(month),
                                    jsonObject.getString("currency")));

                            break;
                    }
                }
                // if no data put into list, then show the hint message
                if (list.isEmpty()){
                    noData(list);
                }
                adapter = new DetailAccountAdapter(list, context);
                adapter.setOnItemClicklistener(this);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * if there is not data, it will show this item
     *
     * @param list
     */
    public void noData(ArrayList<DetailAccount> list) {
        list.add(new DetailAccount(null, InfoSource.getCategoryImage("Empty"), "Oh no, you do not have any record!",
                null, null, null, null, null, null));
        DetailAccountAdapter adapter = new DetailAccountAdapter(list, context);
        recyclerView.setAdapter(adapter);
    }

    /**
     * this dialog show delete the single data
     *
     * @param id
     */
    public void dialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Delete?");
        builder.setTitle("Hint");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (sqLiteUtils.delete("id = ?", new String[]{id})) {
                    Toast.makeText(context, "Successfully delete!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    activity.finish();
                } else {
                    Toast.makeText(context, "Delete failed, try again!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        dialog(id);
    }
}
