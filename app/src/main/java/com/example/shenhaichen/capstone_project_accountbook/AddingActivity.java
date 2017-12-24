package com.example.shenhaichen.capstone_project_accountbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shenhaichen.capstone_project_accountbook.adapter.SpinnerAdapter;
import com.example.shenhaichen.capstone_project_accountbook.bean.InfoSource;
import com.example.shenhaichen.capstone_project_accountbook.bean.SpinnerItems;
import com.example.shenhaichen.capstone_project_accountbook.database.DatabaseContract;
import com.example.shenhaichen.capstone_project_accountbook.database.TaskContract;
import com.example.shenhaichen.capstone_project_accountbook.utils.KeyBoardUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    @BindView(R.id.activity_add_input)
    public EditText numEditText;
    @BindView(R.id.activity_add_comment)
    public EditText commentEditText;
    @BindView(R.id.activity_add_btn_category)
    public Spinner categorySpinner;
    @BindView(R.id.activity_add_btn_payment)
    public Spinner paymentSpinner;
    @BindView(R.id.activity_add_btn_style)
    public Spinner styleSpinner;
    @BindView(R.id.activity_add_btn_save)
    public Button btn_save;
    @BindView(R.id.activity_add_btn_clean)
    public Button btn_more;

    private SpinnerAdapter categoryAdapter;
    private SpinnerAdapter paymentAdapter;
    private SpinnerAdapter accountStyleAdapter;

    private List<SpinnerItems> categoryList;
    private List<SpinnerItems> paymentList;
    private List<SpinnerItems> styleList;

    private boolean category_selected = false;
    private boolean payment_selected = false;
    private boolean style_selected = false;
    private String categoryName;
    private String paymentMethod;
    private String styleMethod;
    private Context context;
    private Activity activity;

    private KeyBoardUtil keyBoardUtil;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        ButterKnife.bind(this);
        btn_save.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        // 得到键盘实例
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        context = this;
        activity = this;
        initList();
        initNumberEditText();
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initList() {
        categoryList = new ArrayList<>();
        paymentList = new ArrayList<>();
        styleList = new ArrayList<>();
        categoryList.add(new SpinnerItems(R.mipmap.icon_dinner, getString(R.string.dinner)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_shopping, getString(R.string.shopping)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_house, getString(R.string.house)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_public_tran, getString(R.string.public_transport)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_study, getString(R.string.study)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_exercise, getString(R.string.training)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_travel, getString(R.string.travel)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_game, getString(R.string.entertainment)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_beauty, getString(R.string.hairdressing)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_medicine, getString(R.string.treatment)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_invest, getString(R.string.investment)));
        categoryList.add(new SpinnerItems(R.mipmap.icon_salary, getString(R.string.salary)));

        paymentList.add(new SpinnerItems(R.mipmap.icon_cash, getString(R.string.cash)));
        paymentList.add(new SpinnerItems(R.mipmap.icon_credit_card, getString(R.string.credit_card)));
        paymentList.add(new SpinnerItems(R.mipmap.icon_xuni, getString(R.string.xuni)));

        styleList.add(new SpinnerItems(R.mipmap.icon_outcome, getString(R.string.outcome)));
        styleList.add(new SpinnerItems(R.mipmap.icon_income, getString(R.string.income)));

        categoryAdapter = new SpinnerAdapter(this, categoryList);
        paymentAdapter = new SpinnerAdapter(this, paymentList);
        accountStyleAdapter = new SpinnerAdapter(this, styleList);
        categorySpinner.setAdapter(categoryAdapter);
        paymentSpinner.setAdapter(paymentAdapter);
        styleSpinner.setAdapter(accountStyleAdapter);

        categorySpinner.setOnItemSelectedListener(this);
        paymentSpinner.setOnItemSelectedListener(this);
        styleSpinner.setOnItemSelectedListener(this);
    }

    /**
     * 创建一个自定义键盘用于输入金额
     */
    public void initNumberEditText() {
        //由于需要，所以自定义一个键盘只输入数字和简单符号
        keyBoardUtil = new KeyBoardUtil(activity, numEditText, context, btn_more, btn_save);

        numEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当用户点击editText的时候，展示键盘
                numEditText.setText("");
                keyBoardUtil.showKeyboard();
                //隐藏默认键盘
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                //不允许使用默认键盘
                numEditText.setInputType(InputType.TYPE_NULL);
                return false;
            }
        });
        commentEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyBoardUtil.hideKeyboard();
                return false;
            }
        });

        //多加一个listener去监听输入的情况
        numEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // 控制2位小数位
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_add_btn_save:
                // 储存输入信息
                Calendar calendar = Calendar.getInstance();

                ContentValues values = new ContentValues();
                if (!"".equals(numEditText.getText().toString())){
                    values.put(DatabaseContract.ACCOUNT_AMOUNT, numEditText.getText().toString());
                }else {
                    values.put(DatabaseContract.ACCOUNT_AMOUNT, getString(R.string.zero_zero));
                }

                values.put(DatabaseContract.ACCOUNT_CATEGORY, categoryName);
                // 信用卡是0，现金是1，虚拟支付是2
                if (getString(R.string.credit_card).equals(paymentMethod)) {
                    values.put(DatabaseContract.ACCOUNT_PAYMENT, 0);
                } else if (getString(R.string.cash).equals(paymentMethod)){
                    values.put(DatabaseContract.ACCOUNT_PAYMENT, 1);
                }else {
                    values.put(DatabaseContract.ACCOUNT_PAYMENT, 2);
                }
                values.put(DatabaseContract.ACCOUNT_COMMENT, commentEditText.getText().toString());
                values.put(DatabaseContract.ACCOUNT_YEAR, ""+calendar.get(Calendar.YEAR));
                values.put(DatabaseContract.ACCOUNT_MONTH, ""+(calendar.get(Calendar.MONTH) + 1));
                values.put(DatabaseContract.ACCOUNT_WEEK, ""+calendar.get(Calendar.WEEK_OF_MONTH));
                values.put(DatabaseContract.ACCOUNT_DAY, ""+calendar.get(Calendar.DAY_OF_MONTH));
                values.put(DatabaseContract.ACCOUNT_CURRENCY, InfoSource.CURRENCYFORMATE);
                // 支出是1, 收入是0
                if (getString(R.string.outcome).equals(styleMethod)) {
                    values.put(DatabaseContract.ACCOUNT_STYLE, 1);
                } else {
                    values.put(DatabaseContract.ACCOUNT_STYLE, 0);
                }
                //存入数据库
                Uri uri = getContentResolver().insert(TaskContract.TaskEntry.ACCOUNT_BOOK_URI, values);
                boolean successfullyInsert = (uri.toString() != null);
                if (successfullyInsert) {
                    Toast.makeText(this, getString(R.string.success_save), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.save_failed), Toast.LENGTH_SHORT).show();
                }
                this.finish();
                break;
            case R.id.activity_add_btn_clean:
                numEditText.setText("");
                commentEditText.setText("");
                break;
        }
    }

    /**
     * 得到不同的spinner的值
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            // 使用boolean去得到默认的值
            case R.id.activity_add_btn_category:
                TextView categoryText = view.findViewById(R.id.spinner_item);
                if (category_selected) {
                    categoryName = categoryText.getText().toString();
                } else {
                    categoryName = categoryText.getText().toString();
                    category_selected = true;
                }
                break;
            case R.id.activity_add_btn_payment:
                TextView paymentText = view.findViewById(R.id.spinner_item);
                if (payment_selected) {
                    paymentMethod = paymentText.getText().toString();
                } else {
                    paymentMethod = paymentText.getText().toString();
                    payment_selected = true;
                }
                break;
            case R.id.activity_add_btn_style:
                TextView styleText = view.findViewById(R.id.spinner_item);
                if (style_selected) {
                    styleMethod = styleText.getText().toString();
                } else {
                    styleMethod = styleText.getText().toString();
                    style_selected = true;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
