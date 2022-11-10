package com.example.iatry2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditStockDialog{

    Activity activity;
    AlertDialog itemInfo;
    Context context;
    TextView DisplayItemName, itemTypeText, priceText;


    public void viewInfoDialog(String name, String type, String price, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_edit_stock_dialog, null);
        DisplayItemName = view.findViewById(R.id.DisplayItemName);
        itemTypeText = view.findViewById(R.id.itemTypeText);
        priceText = view.findViewById(R.id.priceText);

        builder.setView(view);
        DisplayItemName.setText(name);
        itemTypeText.setText(type);
        priceText.setText(price);
        builder.setCancelable(true);
        itemInfo = builder.create();
        itemInfo.show();


    }

}