package com.example.iatry2;

import androidx.recyclerview.widget.RecyclerView;

public class Stock {
    String ItemName, ItemType, Price, timestamp;


    public Stock() {

    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Stock(String itemName, String itemType, String price) {
        ItemName = itemName;
        ItemType = itemType;
        Price = price;




    }
}