package com.example.iatry2;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class adding_item_dialog {

    Activity activity;
    AlertDialog dialog;

    adding_item_dialog(Activity myActivity) {
        activity = myActivity;
    }

    void startItemDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_adding_item_dialog,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

    }

    void dismissDialog(){
        dialog.dismiss();
    }


}
