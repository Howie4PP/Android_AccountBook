package com.example.shenhaichen.capstone_project_accountbook;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.database.DatabaseContract;
import com.example.shenhaichen.capstone_project_accountbook.database.TaskContract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.change_to_cny)
    public Button btn_cny;
    @BindView(R.id.change_to_sg)
    public Button btn_sgd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);

        btn_cny.setOnClickListener(this);
        btn_sgd.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        ArrayList<Double> cnyList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        switch (v.getId()) {
            // CNY to SGD
            case R.id.change_to_sg:
                cnyList.clear();
                idList.clear();
                ContentValues contentValues = new ContentValues();
                // 从数据库中取出数据
                Cursor sg_cursor = this.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                        new String[]{DatabaseContract.ACCOUNT_ID, DatabaseContract.ACCOUNT_AMOUNT, DatabaseContract.ACCOUNT_CURRENCY}, null,
                        null, null);

                for (sg_cursor.moveToFirst(); !sg_cursor.isAfterLast(); sg_cursor.moveToNext()) {
                    double cny =  Double.parseDouble(sg_cursor.getString(sg_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                    if (!"$".equals(sg_cursor.getString(sg_cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY)))) {
                        cny *= 4.8;
                        cnyList.add(cny);
                        idList.add(sg_cursor.getString(sg_cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)));
                    }
                }
                // 更新数据
                if (!cnyList.isEmpty()) {
                    boolean flag = false;
                    for (int x = 0; x < cnyList.size(); x++) {
                        contentValues.put(DatabaseContract.ACCOUNT_AMOUNT, cnyList.get(x));
                        contentValues.put(DatabaseContract.ACCOUNT_CURRENCY, "$");
                        int i = this.getContentResolver().update(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                                contentValues, "id = ?",
                                new String[]{idList.get(x)});
//                        sqLiteUtils.update(contentValues, "id = ?", );
                       flag = (i >= 0 ? true : false);
                    }
                    if (flag){
                        InfoSource.CURRENCYFORMATE = "$";
                        Toast.makeText(this, getString(R.string.success_change), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            // SGD to CNY
            case R.id.change_to_cny:
                cnyList.clear();
                idList.clear();
                ContentValues cnyValues = new ContentValues();
                // 从数据库中取出数据
                Cursor cny_cursor = this.getContentResolver().query(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                        new String[]{DatabaseContract.ACCOUNT_ID, DatabaseContract.ACCOUNT_AMOUNT, DatabaseContract.ACCOUNT_CURRENCY}, null,
                        null, null);

                for (cny_cursor.moveToFirst(); !cny_cursor.isAfterLast(); cny_cursor.moveToNext()) {
                    double cny =  Double.parseDouble(cny_cursor.getString(cny_cursor.getColumnIndex(DatabaseContract.ACCOUNT_AMOUNT)));
                    if (!"￥".equals(cny_cursor.getString(cny_cursor.getColumnIndex(DatabaseContract.ACCOUNT_CURRENCY)))) {
                        cny /= 4.8;
                        cnyList.add(cny);
                        idList.add(cny_cursor.getString(cny_cursor.getColumnIndex(DatabaseContract.ACCOUNT_ID)));
                    }
                }

                // 更新数据
                if (!cnyList.isEmpty()) {
                    boolean flag = false;
                    for (int x = 0; x < cnyList.size(); x++) {
                        cnyValues.put(DatabaseContract.ACCOUNT_AMOUNT, cnyList.get(x));
                        cnyValues.put(DatabaseContract.ACCOUNT_CURRENCY, "￥");
                        int i = this.getContentResolver().update(TaskContract.TaskEntry.ACCOUNT_BOOK_URI,
                                cnyValues, "id = ?",
                                new String[]{idList.get(x)});
//                        sqLiteUtils.update(contentValues, "id = ?", );
                        flag = (i >= 0 ? true : false);
                    }
                    if (flag){
                        InfoSource.CURRENCYFORMATE = "￥";
                        Toast.makeText(this, getString(R.string.success_change), Toast.LENGTH_SHORT).show();
                    }
                }
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
