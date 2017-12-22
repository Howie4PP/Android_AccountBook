package com.example.shenhaichen.capstone_project_accountbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.AddingActivity;
import com.example.shenhaichen.capstone_project_accountbook.DetailActivity;
import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.adapter.MainBottomAdapter;
import com.example.shenhaichen.capstone_project_accountbook.adapter.MainTopAdapter;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingBottomItem;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingTopItem;
import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.database.SQLiteUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class main_fragment extends Fragment implements View.OnClickListener, MainBottomAdapter.OnItemClickListener{

    @BindView(R.id.main_fragment_top_recycler_view)
    public RecyclerView topRecyclerView;
    @BindView(R.id.main_fragment_bottom_recycler_view)
    public RecyclerView bottomRecyclerView;
    @BindView(R.id.main_fragment_time)
    public TextView textTime;
    @BindView(R.id.main_fragment_record)
    public ImageButton btn_record;
    private List<AddingTopItem> topItems;
    private List<AddingBottomItem> bottomItems;
    private MainTopAdapter topAdapter;
    private MainBottomAdapter bottomAdapter;
    private SQLiteUtils sqLiteUtils;
    private Intent startAddIntent;



    public main_fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        //使用butterknife去绑定
        ButterKnife.bind(this,view);
        topItems = new ArrayList<>();
        bottomItems = new ArrayList<>();
        sqLiteUtils = new SQLiteUtils(getActivity());
        btn_record.setOnClickListener(this);
        initRecyclerView();
        updateData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();
    }

    /**
     * update all date after user enter some records, this function will query data from SQlite
     * database.
     */
    private void updateData() {
        // query income and outcome data from the database
        String income = sqLiteUtils.select(new String[]{"amount", "month", "week", "day", "currency"}, "style = ?", new String[]{"0"});
        String outcome = sqLiteUtils.select(new String[]{"amount", "month", "week", "day", "currency"}, "style = ?", new String[]{"1"});

//        System.out.println("this is income" + income);
//        System.out.println("this is out" + outcome);
        Calendar calendar = Calendar.getInstance();
        int thisMonth = (calendar.get(Calendar.MONTH) + 1);
        textTime.setText(thisMonth + "/2017");
        String currencyFormat = null;
        ArrayList<AddingTopItem> newTopList = new ArrayList<>();
        //if the data comes from database is not empty
        if (!outcome.equals("[]") || !income.equals("[]")) {
//                    System.out.println("get the value："+income);
            double totalInValue = 0.0;
            double totalOutValue = 0.0;

            double totalMonthInValue = 0.0;
            double totalMonthOutValue = 0.0;

            double totalWeekInValue = 0.0;
            double totalWeekOutValue = 0.0;

            double totalTodayInValue = 0.0;
            double totalTodayOutValue = 0.0;

            int month = 0;
            int week = 0;
            int day = 0;

            try {

                // get the income data
                JSONArray inArray = new JSONArray(income);
                for (int i = 0; i < inArray.length(); i++) {
                    JSONObject jsonObject = inArray.getJSONObject(i);
                    // this is calculate the total income value of the year
                    totalInValue += Double.parseDouble(jsonObject.getString("amount"));

                    month = Integer.parseInt(jsonObject.getString("month"));
                    week = Integer.parseInt(jsonObject.getString("week"));
                    day = Integer.parseInt(jsonObject.getString("day"));

                    currencyFormat = jsonObject.getString("currency");

                    if (thisMonth == month) {
                        totalMonthInValue += Double.parseDouble(jsonObject.getString("amount"));
                        if (calendar.get(Calendar.WEEK_OF_MONTH) == week) {
                            totalWeekInValue += Double.parseDouble(jsonObject.getString("amount"));
                            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                                totalTodayInValue += Double.parseDouble(jsonObject.getString("amount"));
                            }
                        }
                    }
                }

                //get the outcome data
                JSONArray outArray = new JSONArray(outcome);
                for (int i = 0; i < outArray.length(); i++) {
                    JSONObject jsonObject = outArray.getJSONObject(i);
//                    System.out.println("outcome:" + jsonObject.getString("amount"));
//                    System.out.println("month" + jsonObject.getString("month"));
//                    System.out.println("week" + jsonObject.getString("week"));
//                    System.out.println("day" + jsonObject.getString("day"));

                    totalOutValue += Double.parseDouble(jsonObject.getString("amount"));

                    month = Integer.parseInt(jsonObject.getString("month"));
                    week = Integer.parseInt(jsonObject.getString("week"));
                    day = Integer.parseInt(jsonObject.getString("day"));
                    currencyFormat = jsonObject.getString("currency");

                    if ((calendar.get(Calendar.MONTH) + 1) == month) {
                        totalMonthOutValue += Double.parseDouble(jsonObject.getString("amount"));
                        if (calendar.get(Calendar.WEEK_OF_MONTH) == week) {
                            totalWeekOutValue += Double.parseDouble(jsonObject.getString("amount"));
                            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                                totalTodayOutValue += Double.parseDouble(jsonObject.getString("amount"));
                            }
                        }
                    }
                }

//                System.out.println("this is totalMonthInValue:" + totalMonthInValue);
//                System.out.println("this is totalMonthOutValue:" + totalMonthOutValue);
//                System.out.println("this is totalWeekInValue:" + totalWeekInValue);
//                System.out.println("this is totalWeekOutValue:" + totalWeekOutValue);
//                System.out.println("this is totalInValue:" + totalInValue);
//                System.out.println("this is totalOutvalue:" + totalOutValue);
                newTopList.add(new AddingTopItem("Income", changeFormat(currencyFormat, totalInValue)));
                newTopList.add(new AddingTopItem("Outcome", changeFormat(currencyFormat, totalOutValue)));
                String[][] allValue = {{changeFormat(currencyFormat, totalTodayInValue), changeFormat(currencyFormat, totalTodayOutValue)},
                        {changeFormat(currencyFormat, totalWeekInValue), changeFormat(currencyFormat, totalWeekOutValue)},
                        {changeFormat(currencyFormat, totalMonthInValue), changeFormat(currencyFormat, totalMonthOutValue)},
                        {changeFormat(currencyFormat, totalInValue), changeFormat(currencyFormat, totalOutValue)}};
//                System.out.println("totalMonthOutValue:" + allValue[2][1]);
                // update the total value which shows on the top of main page
                topAdapter.updateData(newTopList);
                // update the detail value which shows on the bottom of the main page
                bottomAdapter.updateData(allValue);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            // if do not have data, it will show the 0 at the interface
            if (currencyFormat == null){
                currencyFormat = InfoSource.CURRENCYFORMATE;
            }
            String[][] allValue = {{changeFormat(currencyFormat, 0.0), changeFormat(currencyFormat, 0.0)},
                    {changeFormat(currencyFormat, 0.0), changeFormat(currencyFormat, 0.0)},
                    {changeFormat(currencyFormat, 0.0), changeFormat(currencyFormat, 0.0)},
                    {changeFormat(currencyFormat, 0.0), changeFormat(currencyFormat, 0.0)}};
            newTopList.add(new AddingTopItem("Income", changeFormat(currencyFormat, 0.0)));
            newTopList.add(new AddingTopItem("Outcome", changeFormat(currencyFormat, 0.0)));
            topAdapter.updateData(newTopList);
            bottomAdapter.updateData(allValue);
        }
    }

    private void initRecyclerView() {
        AddingTopItem item1 = new AddingTopItem("Income", "$ 0.00");
        AddingTopItem item2 = new AddingTopItem("Outcome", "$ 0.00");
        topItems.add(item1);
        topItems.add(item2);
//        topItems[0] = item1;
//        topItems[1] = item2;

        AddingBottomItem dayItem = new AddingBottomItem("Today", "$ 0.0", "$ 0.0", R.mipmap.icon_item_today);
        AddingBottomItem weekItem = new AddingBottomItem("This week", "$ 0.0", "$ 0.0", R.mipmap.icon_item_week);
        AddingBottomItem monthItem = new AddingBottomItem("This month", "$ 0.0", "$ 0.0", R.mipmap.icon_item_month);
        AddingBottomItem yearItem = new AddingBottomItem("This year", "$ 0.0", "$ 0.0", R.mipmap.icon_item_year);

//        bottomItems[0] = dayItem;
//        bottomItems[1] = weekItem;
//        bottomItems[2] = monthItem;
//        bottomItems[3] = yearItem;
        bottomItems.add(dayItem);
        bottomItems.add(weekItem);
        bottomItems.add(monthItem);
        bottomItems.add(yearItem);

        topAdapter = new MainTopAdapter(topItems);
        bottomAdapter = new MainBottomAdapter(bottomItems);
        // use interface to send view and position from the adapter.
        bottomAdapter.setOnItemClicklistener(this);

        topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        topRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        topRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topRecyclerView.setAdapter(topAdapter);

        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bottomRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        bottomRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bottomRecyclerView.setAdapter(bottomAdapter);
    }

    /**
     *  this listener is for the button of adding record
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_fragment_record:
                startAddIntent = new Intent(getActivity(), AddingActivity.class);
                startActivity(startAddIntent);
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {
        switch (position) {
            case 0:
                Intent dayIntent = new Intent(getActivity(), DetailActivity.class);
                dayIntent.putExtra("title","Today");
                dayIntent.putExtra("style",1);
                startActivity(dayIntent);
//                Toast.makeText(getActivity(), "this is day activity", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent weekIntent = new Intent(getActivity(), DetailActivity.class);
                weekIntent.putExtra("title","This week");
                weekIntent.putExtra("style",2);
                startActivity(weekIntent);
//                Toast.makeText(getActivity(), "this is week activity", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Intent monthIntent = new Intent(getActivity(), DetailActivity.class);
                monthIntent.putExtra("title","This week");
                monthIntent.putExtra("style",3);
                startActivity(monthIntent);
//                Toast.makeText(getActivity(), "this is month activity", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent yearIntent = new Intent(getActivity(), DetailActivity.class);
                yearIntent.putExtra("title","This year");
                yearIntent.putExtra("style",4);
                startActivity(yearIntent);
//                Toast.makeText(getActivity(), "this is year activity", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public String changeFormat(String currencyFormat, double value) {
        return currencyFormat +" "+ String.format("%.2f", value);
    }
}
