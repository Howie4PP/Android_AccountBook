package com.example.shenhaichen.capstone_project_accountbook.bean;

import android.content.Context;

import com.example.shenhaichen.capstone_project_accountbook.R;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class InfoSource {

    public static String CURRENCYFORMATE = "￥";

    private Context context;

    public InfoSource(Context context) {
        this.context = context;
    }

    public int getCategoryImage(String category) {

        if (context.getString(R.string.dinner).equals(category)) {
            return R.mipmap.icon_dinner;
        } else if (context.getString(R.string.house).equals(category)) {
            return R.mipmap.icon_house;
        } else if (context.getString(R.string.public_transport).equals(category)) {
            return R.mipmap.icon_public_tran;
        } else if (context.getString(R.string.training).equals(category)) {
            return R.mipmap.icon_exercise;
        } else if (context.getString(R.string.travel).equals(category)) {
            return R.mipmap.icon_travel;
        } else if (context.getString(R.string.entertainment).equals(category)) {
            return R.mipmap.icon_game;
        } else if (context.getString(R.string.hairdressing).equals(category)) {
            return R.mipmap.icon_beauty;
        } else if (context.getString(R.string.treatment).equals(category)) {
            return R.mipmap.icon_medicine;
        } else if (context.getString(R.string.shopping).equals(category)) {
            return R.mipmap.icon_shopping;
        } else if (context.getString(R.string.study).equals(category)) {
            return R.mipmap.icon_study;
        }else if (context.getString(R.string.empty).equals(category)) {
            return R.mipmap.icon_no_record;
        }else if (context.getString(R.string.investment).equals(category)) {
            return R.mipmap.icon_invest;
        }else if (context.getString(R.string.salary).equals(category)) {
            return R.mipmap.icon_salary;
        }
        return 0;
    }

    public  String getPayment(String payment) {
        if ("1".equals(payment)) {
            return context.getString(R.string.cash);
        } else if ("0".equals(payment)){
            return context.getString(R.string.credit_card);
        }else {
            return context.getString(R.string.xuni);
        }
    }

    public String changeMonth(int inputValue) {
        String month = null;
        switch (inputValue) {
            case 1:
                month = context.getString(R.string.jan);
                break;
            case 2:
                month = context.getString(R.string.feb);
                break;
            case 3:
                month = context.getString(R.string.mar);
                break;
            case 4:
                month = context.getString(R.string.apr);
                break;
            case 5:
                month = context.getString(R.string.may);
                break;
            case 6:
                month = context.getString(R.string.jun);
                break;
            case 7:
                month = context.getString(R.string.jul);
                break;
            case 8:
                month = context.getString(R.string.aug);
                break;
            case 9:
                month = context.getString(R.string.sep);
                break;
            case 10:
                month = context.getString(R.string.oct);
                break;
            case 11:
                month = context.getString(R.string.nov);
                break;
            case 12:
                month = context.getString(R.string.dec);
                break;
        }
        return month;
    }


}

