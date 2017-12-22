package com.example.shenhaichen.capstone_project_accountbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.R;
import com.example.shenhaichen.capstone_project_accountbook.adapter.MainBottomAdapter;
import com.example.shenhaichen.capstone_project_accountbook.adapter.MainTopAdapter;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingBottomItem;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingTopItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class main_fragment extends Fragment{

    @BindView(R.id.main_fragment_top_recycler_view)
    private RecyclerView topRecyclerView;
    @BindView(R.id.main_fragment_bottom_recycler_view)
    private RecyclerView bottomRecyclerView;
    private List<AddingTopItem> topItems;
    private List<AddingBottomItem> bottomItems;
    private ImageButton btn_record;
    private MainTopAdapter topAdapter;
    private MainBottomAdapter bottomAdapter;
//    private SQLiteUtils sqLiteUtils;
    private Intent startAddIntent;
    private TextView textTime;


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

        return view;
    }
}
