package com.example.iatry2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class addgroupDialog {

    Activity activity;
    AlertDialog newgroup;
    Button quitdialog;


    addgroupDialog(Activity myActivity) {
        activity = myActivity;
    }


    Button closebutton = quitdialog.findViewById(R.id.quitdialog);

    void startgroupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_addgroup_dialog,null));
        builder.setCancelable(false);
        newgroup = builder.create();
        newgroup .show();

    }


    void dissmissgroupdialog(){
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newgroup.dismiss();

            }
        });


    }


}
