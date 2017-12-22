package com.example.shenhaichen.capstone_project_accountbook;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.database.SQLiteUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.change_btn_back)
    public Button btn_back;
    @BindView(R.id.change_cny)
    public Button btn_cny;
    @BindView(R.id.change_sg)
    public Button btn_sgd;
    private SQLiteUtils sqLiteUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);
        sqLiteUtils = new SQLiteUtils(this);

        btn_back.setOnClickListener(this);
        btn_cny.setOnClickListener(this);
        btn_sgd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ArrayList<Double> cnyList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        switch (v.getId()) {
            // SGD to CNY
            case R.id.change_cny:
                cnyList.clear();
                idList.clear();
                ContentValues contentValues = new ContentValues();
                // get the data from database
                String currency = sqLiteUtils.select(new String[]{"id", "amount", "currency"}, null, null);
                if (!currency.equals("[]")) {
                    try {
                        JSONArray currencyArray = new JSONArray(currency);
                        for (int i = 0; i < currencyArray.length(); i++) {
                            JSONObject jsonObject = currencyArray.getJSONObject(i);
                            double cny = Double.parseDouble(jsonObject.getString("amount"));
                            if (!"￥".equals(jsonObject.getString("currency"))) {
                                cny *= 4.8;
                                cnyList.add(cny);
                                idList.add(jsonObject.getString("id"));

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // update data! because all number just change from SGD to CNY.
                if (!cnyList.isEmpty()) {
                    for (int x = 0; x < cnyList.size(); x++) {
                        contentValues.put("amount", cnyList.get(x));
                        contentValues.put("currency", "￥");
                        sqLiteUtils.update(contentValues, "id = ?", new String[]{idList.get(x)});
                    }
                    InfoSource.CURRENCYFORMATE = "￥";
                }
                Toast.makeText(this, "Successfully Change", Toast.LENGTH_SHORT).show();
                break;
            // CNY to SGD
            case R.id.change_sg:
                cnyList.clear();
                idList.clear();
                ContentValues sgValues = new ContentValues();
                String currencySg = sqLiteUtils.select(new String[]{"id", "amount", "currency"}, null, null);
                if (!currencySg.equals("[]")) {
                    try {
                        JSONArray currencyArray = new JSONArray(currencySg);
                        for (int i = 0; i < currencyArray.length(); i++) {
                            JSONObject jsonObject = currencyArray.getJSONObject(i);
                            double sgd = Double.parseDouble(jsonObject.getString("amount"));
                            if (!"$".equals(jsonObject.getString("currency"))) {
                                sgd /= 4.8;
                                cnyList.add(sgd);
                                idList.add(jsonObject.getString("id"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // update data! because all number just change from CNY to SGD.
                if (!cnyList.isEmpty()) {
                    for (int x = 0; x < cnyList.size(); x++) {
                        sgValues.put("amount", cnyList.get(x));
                        sgValues.put("currency", "$");
                        sqLiteUtils.update(sgValues, "id = ?", new String[]{idList.get(x)});
                    }
                    InfoSource.CURRENCYFORMATE = "$";
                }
                Toast.makeText(this, "Successfully Change", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_btn_back:
                finish();
                break;
        }

    }
}
