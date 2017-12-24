package com.example.shenhaichen.capstone_project_accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.shenhaichen.capstone_project_accountbook.adapter.MainBottomAdapter;
import com.example.shenhaichen.capstone_project_accountbook.bean.AddingBottomItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.setting_recyclerView)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        ArrayList<AddingBottomItem> list = new ArrayList<>();
        list.add(new AddingBottomItem(getString(R.string.change_rate),null,null,R.mipmap.icon_exchange));
        list.add(new AddingBottomItem(getString(R.string.clean),null,null,R.mipmap.icon_clean));

        MainBottomAdapter adapter = new MainBottomAdapter(list);

        adapter.setOnItemClicklistener(new MainBottomAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 0:
                        Intent changeIntent = new Intent(SettingsActivity.this, ChangeActivity.class);
                        startActivity(changeIntent);
                        break;
                    case 1:
                        Intent cleanIntent = new Intent(SettingsActivity.this, CleanActivity.class);
                        startActivity(cleanIntent);
                        break;
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
