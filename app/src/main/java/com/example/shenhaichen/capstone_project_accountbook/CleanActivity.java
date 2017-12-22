package com.example.shenhaichen.capstone_project_accountbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.database.SQLiteUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CleanActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.clean_back)
    public Button btn_back;
    @BindView(R.id.btn_clean)
    public Button btn_clean;

    private SQLiteUtils sqLiteUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        ButterKnife.bind(this);
        sqLiteUtils = new SQLiteUtils(this);
        btn_back.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clean:
                // clean data
                sqLiteUtils.clean();
                InfoSource.CURRENCYFORMATE = "$";
                Toast.makeText(this,"Successfully clean",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clean_back:
                finish();
                break;
        }
    }
}
