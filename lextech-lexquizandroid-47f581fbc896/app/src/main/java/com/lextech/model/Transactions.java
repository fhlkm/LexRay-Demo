package com.lextech.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hanlu Feng on 10/12/2015.
 */
public class Transactions {
    @SerializedName("ID")
    private String mID;
    @SerializedName("description")
    private String mDescription;
    private float price;


    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
