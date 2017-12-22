package com.example.shenhaichen.capstone_project_accountbook.bean;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class SpinnerItems {
    private int image;
    private String itemName;

    public SpinnerItems(int image, String itemName) {
        this.image = image;
        this.itemName = itemName;
    }

    public int getImage() {
        return image;
    }

    public String getItemName() {
        return itemName;
    }
}
