package com.example.shenhaichen.capstone_project_accountbook.bean;

import com.example.shenhaichen.capstone_project_accountbook.R;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class InfoSource {

    public static String CURRENCYFORMATE = "￥";

    public static int getCategoryImage(String category) {

        if ("Dinner".equals(category)) {
            return R.mipmap.icon_dinner;
        } else if ("House".equals(category)) {
            return R.mipmap.icon_house;
        } else if ("Public transport".equals(category)) {
            return R.mipmap.icon_public_tran;
        } else if ("Training".equals(category)) {
            return R.mipmap.icon_exercise;
        } else if ("Travel".equals(category)) {
            return R.mipmap.icon_travel;
        } else if ("Entertainment".equals(category)) {
            return R.mipmap.icon_game;
        } else if ("Hairdressing".equals(category)) {
            return R.mipmap.icon_beauty;
        } else if ("Treatment".equals(category)) {
            return R.mipmap.icon_medicine;
        } else if ("Shopping".equals(category)) {
            return R.mipmap.icon_shopping;
        } else if ("Study".equals(category)) {
            return R.mipmap.icon_study;
        }else if ("Empty".equals(category)) {
            return R.mipmap.icon_no_record;
        }else if ("Investment".equals(category)) {
            return R.mipmap.icon_invest;
        }else if ("Salary".equals(category)) {
            return R.mipmap.icon_salary;
        }
        return 0;
    }

    public static String getPayment(String payment) {
        if ("1".equals(payment)) {
            return "Cash";
        } else {
            return "Credit Card";
        }
    }

    public static String changeMonth(int inputValue) {
        String month = null;
        switch (inputValue) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }
        return month;
    }


}

