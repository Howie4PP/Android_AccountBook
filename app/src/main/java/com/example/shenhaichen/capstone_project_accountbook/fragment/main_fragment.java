package com.example.shenhaichen.capstone_project_accountbook.fragment;

import android.content.Intent;
import android.database.Cursor;
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
import com.example.shenhaichen.capstone_project_accountbook.database.DatabaseContract;
import com.example.shenhaichen.capstone_project_accountbook.database.SQLiteUtils;
import com.example.shenhaichen.capstone_project_accountbook.database.TaskContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class main_fragment extends Fragment implements View.OnClickListener, MainBottomAdapter.OnItemClickListener {

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
    public static final String TAG = main_fragment.class.getSimpleName();


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
        ButterKnife.bind(this, view);
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
     * 在用户输入数据后，更新所有数据，并从数据库中得到最新的数据
     */
    private void updateData() {
        // 从数据库中查询收入和支出
        Cursor income_cursor = getContext().getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{"amount", "month", "week", "day", "currency"}, "style = ?",
                new String[]{"0"}, null);

        Cursor outcome_cursor = getContext().getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                new String[]{"amount", "month", "week", "day", "currency"}, "style = ?",
                new String[]{"1"}, null);

        Calendar calendar = Calendar.getInstance();
        int thisMonth = (calendar.get(Calendar.MONTH) + 1);
        textTime.setText(thisMonth + "/2017");
        String currencyFormat = null;
        ArrayList<AddingTopItem> newTopList = new ArrayList<>();
        //if the data comes from database is not empty

        if (currencyFormat == null){
                currencyFormat = InfoSource.CURRENCYFORMATE;
            }

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

        //查询收入情况
        for (income_cursor.moveToFirst(); !income_cursor.isAfterLast(); income_cursor.moveToNext()) {
            totalInValue += Double.parseDouble(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
            month = Integer.parseInt(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_MONTH)));
            week = Integer.parseInt(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_WEEK)));
            day = Integer.parseInt(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_DAY)));
            currencyFormat = income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY));

            if (thisMonth == month) {
                totalMonthInValue += Double.parseDouble(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                if (calendar.get(Calendar.WEEK_OF_MONTH) == week) {
                    totalWeekInValue += Double.parseDouble(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                    if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                        totalTodayInValue += Double.parseDouble(income_cursor.getString(income_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                    }
                }
            }

        }

        //查询支出情况

        for (outcome_cursor.moveToFirst(); !outcome_cursor.isAfterLast(); outcome_cursor.moveToNext()) {
            totalOutValue += Double.parseDouble(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
            month = Integer.parseInt(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_MONTH)));
            week = Integer.parseInt(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_WEEK)));
            day = Integer.parseInt(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_DAY)));
            currencyFormat = outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY));

            if ((calendar.get(Calendar.MONTH) + 1) == month) {
                totalMonthOutValue += Double.parseDouble(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                if (calendar.get(Calendar.WEEK_OF_MONTH) == week) {
                    totalWeekOutValue += Double.parseDouble(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                    if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                        totalTodayOutValue += Double.parseDouble(outcome_cursor.getString(outcome_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                    }
                }
            }

        }
        newTopList.add(new AddingTopItem("收入", changeFormat(currencyFormat, totalInValue)));
        newTopList.add(new AddingTopItem("支出", changeFormat(currencyFormat, totalOutValue)));
        String[][] allValue = {{changeFormat(currencyFormat, totalTodayInValue), changeFormat(currencyFormat, totalTodayOutValue)},
                {changeFormat(currencyFormat, totalWeekInValue), changeFormat(currencyFormat, totalWeekOutValue)},
                {changeFormat(currencyFormat, totalMonthInValue), changeFormat(currencyFormat, totalMonthOutValue)},
                {changeFormat(currencyFormat, totalInValue), changeFormat(currencyFormat, totalOutValue)}};

        // 更新上下两个list的数据
        topAdapter.updateData(newTopList);
        bottomAdapter.updateData(allValue);

    }

    /**
     * 初始化控件和adapter
     */
    private void initRecyclerView() {
        AddingTopItem item1 = new AddingTopItem("收入", "$ 0.00");
        AddingTopItem item2 = new AddingTopItem("支出", "$ 0.00");
        topItems.add(item1);
        topItems.add(item2);

        AddingBottomItem dayItem = new AddingBottomItem("今天", "$ 0.0", "$ 0.0", R.mipmap.icon_item_today);
        AddingBottomItem weekItem = new AddingBottomItem("本周", "$ 0.0", "$ 0.0", R.mipmap.icon_item_week);
        AddingBottomItem monthItem = new AddingBottomItem("本月", "$ 0.0", "$ 0.0", R.mipmap.icon_item_month);
        AddingBottomItem yearItem = new AddingBottomItem("本年", "$ 0.0", "$ 0.0", R.mipmap.icon_item_year);

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
     * this listener is for the button of adding record
     *
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
                dayIntent.putExtra("title", "今天");
                dayIntent.putExtra("style", 1);
                startActivity(dayIntent);
//                Toast.makeText(getActivity(), "this is day activity", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent weekIntent = new Intent(getActivity(), DetailActivity.class);
                weekIntent.putExtra("title", "本周");
                weekIntent.putExtra("style", 2);
                startActivity(weekIntent);
//                Toast.makeText(getActivity(), "this is week activity", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Intent monthIntent = new Intent(getActivity(), DetailActivity.class);
                monthIntent.putExtra("title", "本月");
                monthIntent.putExtra("style", 3);
                startActivity(monthIntent);
//                Toast.makeText(getActivity(), "this is month activity", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent yearIntent = new Intent(getActivity(), DetailActivity.class);
                yearIntent.putExtra("title", "本年");
                yearIntent.putExtra("style", 4);
                startActivity(yearIntent);
//                Toast.makeText(getActivity(), "this is year activity", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public String changeFormat(String currencyFormat, double value) {
        return currencyFormat + " " + String.format("%.2f", value);
    }
}
