package com.example.iatry2;

import androidx.recyclerview.widget.RecyclerView;

public class cashncarry_list {
    String Item, Name, Time;


    public cashncarry_list() {

    }

    public cashncarry_list(String Item, String Name, String Time) {
        this.Item = Item;
        this.Name = Name;
        this.Time = Time;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getItem() {
        return Item;
    }

    public String deleteItem() {
        Item = "";
        return "";
    }

    public String editItem() {
        this.Item = Item;
        return Item;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = Time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}


