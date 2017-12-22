package com.example.shenhaichen.capstone_project_accountbook.bean;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class AddingTopItem {
    private String itemName;
    private String itemNumber;

    public AddingTopItem(String itemName, String itemNumber) {
        this.itemName = itemName;
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

}
