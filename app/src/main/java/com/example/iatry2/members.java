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

public class  members {
    String name, number, position, timestamp, ceoID, userUID, groupId, designation;

    public members() {
    }

    public members(String name, String number, String position, String timestamp, String ceoID,
                   String userUID, String designation, String groupId) {
        this.name = name;
        this.position = position;
        this.number = number;
        this.timestamp = timestamp;
        this.ceoID = ceoID;
        this.userUID = userUID;
        this.designation = designation;
        this.groupId = groupId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) { this.position = position; }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getCeoID() {
        return ceoID;
    }

    public void setCeoID(String createdBy) { this.ceoID = ceoID; }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) { this.userUID = userUID; }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) { this.designation = designation; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) { this.number = number; }





}



