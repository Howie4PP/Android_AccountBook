package com.example.shenhaichen.capstone_project_accountbook.bean;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class AddingBottomItem {
    private String itemName;
    private String itemNumberUp;
    private String itemNumberDown;
    private int imgId;

    public AddingBottomItem(String itemName, String itemNumberUp, String itemNumberDown, int imgId) {
        this.itemName = itemName;
        this.itemNumberUp = itemNumberUp;
        this.itemNumberDown = itemNumberDown;
        this.imgId = imgId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumberUp() {
        return itemNumberUp;
    }

    public void setItemNumberUp(String itemNumberUp) {
        this.itemNumberUp = itemNumberUp;
    }

    public String getItemNumberDown() {
        return itemNumberDown;
    }

    public void setItemNumberDown(String itemNumberDown) {
        this.itemNumberDown = itemNumberDown;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

}
