package com.example.shenhaichen.capstone_project_accountbook;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shenhaichen.capstone_project_accountbook.utils.ChangeRate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.change_to_cny)
    public Button btn_cny;
    @BindView(R.id.change_to_sg)
    public Button btn_sgd;
    private ChangeRate mChangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);

        btn_cny.setOnClickListener(this);
        btn_sgd.setOnClickListener(this);

        mChangeRate = new ChangeRate(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // CNY to SGD
            case R.id.change_to_sg:
                mChangeRate.cnyToSGD();
                break;
            // SGD to CNY
            case R.id.change_to_cny:
                mChangeRate.sgdToCNY();
                break;
        }
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
