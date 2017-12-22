package com.example.shenhaichen.capstone_project_accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shenhaichen.capstone_project_accountbook.database.HandleDailyDataUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_activity_recyclerView)
    public RecyclerView dayRecyclerView;
    @BindView(R.id.detail_activity_btn_back)
    public Button btn_back;
    @BindView(R.id.detail_activity_title)
    public TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int style = intent.getIntExtra("style",0);

        titleText.setText(title);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new HandleDailyDataUtil(this,dayRecyclerView,style,this);
    }
}
