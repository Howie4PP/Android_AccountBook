package com.example.shenhaichen.capstone_project_accountbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.example.shenhaichen.capstone_project_accountbook.database.SQLiteUtils;
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
    @BindView(R.id.activity_add_btn_more)
    public Button btn_more;
    @BindView(R.id.activity_add_btn_back)
    public Button btn_back;

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
    private SQLiteUtils sqUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        ButterKnife.bind(this);
        btn_save.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        // 得到键盘实例
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        context = this;
        activity = this;
        sqUtils = new SQLiteUtils(this);
        initList();
        initNumberEditText();

    }


    private void initList() {
        categoryList = new ArrayList<>();
        paymentList = new ArrayList<>();
        styleList = new ArrayList<>();
        categoryList.add(new SpinnerItems(R.mipmap.icon_dinner, "Dinner"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_house, "House"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_public_tran, "Public transport"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_exercise, "Training"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_travel, "Travel"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_game, "Entertainment"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_beauty, "Hairdressing"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_medicine, "Treatment"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_shopping, "Shopping"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_study, "Study"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_invest, "Investment"));
        categoryList.add(new SpinnerItems(R.mipmap.icon_salary, "Salary"));

        paymentList.add(new SpinnerItems(R.mipmap.icon_cash, "Cash"));
        paymentList.add(new SpinnerItems(R.mipmap.icon_credit_card, "Credit card"));

        styleList.add(new SpinnerItems(R.mipmap.icon_outcome, "Outcome"));
        styleList.add(new SpinnerItems(R.mipmap.icon_income, "Income"));

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
     * this function is customize a keyboard that use for number edit text.
     */
    public void initNumberEditText() {
        // because of the need, custom a keyboard that i need to enter only number
        // and some simple sign.
        keyBoardUtil = new KeyBoardUtil(activity, numEditText, context, btn_more, btn_save);

        numEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //when user click the edit text, showing the keyboard.
                numEditText.setText("");
                keyBoardUtil.showKeyboard();
                //hide the default keyboard from another edit text.
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                //do not allow to use default keyboard from the system
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

        //add one more listener to catch the content of editText
        numEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // this function is to control the two decimal number,
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
                // after click the button of save, the data will store into the database.
                Calendar calendar = Calendar.getInstance();
//                Toast.makeText(this,"amount:"+numEditText.getText().toString()+",category:"+
//                        categoryName.toLowerCase()+",payment:"+paymentMethod+",comment:"+commentEditText.getText().toString()+
//                        ",year:"+calendar.get(Calendar.YEAR)+",month:"+(calendar.get(Calendar.MONTH)+1)+
//                        ",day:"+calendar.get(Calendar.DAY_OF_MONTH)+",style:"+styleMethod+
//                       ",week:"+ calendar.get(Calendar.WEEK_OF_MONTH), Toast.LENGTH_SHORT).show();

                ContentValues values = new ContentValues();
                if (!"".equals(numEditText.getText().toString())){
                    values.put("amount", numEditText.getText().toString());
                }else {
                    values.put("amount", "0.0");
                }
//                System.out.println(numEditText.getText().toString());

                values.put("category", categoryName);
                // cash is 1, credit card is 0
                if ("Cash".equals(paymentMethod)) {
                    values.put("payment", 1);
                } else {
                    values.put("payment", 0);
                }
                values.put("comment", commentEditText.getText().toString());
                values.put("year", ""+calendar.get(Calendar.YEAR));
                values.put("month", ""+(calendar.get(Calendar.MONTH) + 1));
                values.put("week", ""+calendar.get(Calendar.WEEK_OF_MONTH));
                values.put("day", ""+calendar.get(Calendar.DAY_OF_MONTH));
                values.put("currency", InfoSource.CURRENCYFORMATE);
                // outcome is 1, income is 0
                if ("Outcome".equals(styleMethod)) {
                    values.put("style", 1);
                } else {
                    values.put("style", 0);
                }
                if (sqUtils.insert(values)) {
                    Toast.makeText(this, "Successfully save", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Unsuccessfully save", Toast.LENGTH_SHORT).show();
                }
                this.finish();
                break;
            case R.id.activity_add_btn_more:
                numEditText.setText("");
                commentEditText.setText("");
                break;
            case R.id.activity_add_btn_back:
                finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            // use a value of boolean to control the spinner, could get the default value
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
}
