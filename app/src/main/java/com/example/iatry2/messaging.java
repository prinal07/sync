package com.example.iatry2;

import android.provider.Contacts;
import android.provider.ContactsContract;
import android.widget.Adapter;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class  messaging {
    String groupId, groupTitle, groupDescription, timestamp, createdBy;


    public messaging() {
    }


    public messaging(String groupId, String groupTitle, String groupDescription, String timestamp, String createdBy) {
        this.groupId = groupId;
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.timestamp = timestamp;
        this.createdBy = createdBy;

    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) { this.groupDescription = groupDescription; }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }




    }



