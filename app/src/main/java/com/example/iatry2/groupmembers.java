package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class groupmembers extends AppCompatActivity {
    TextView thegroupdescription;
    ImageButton back, searchparticipants;
    RecyclerView membersrecycler;
    String groupId;
    ArrayList<members> memberslist;
    AdapterGroupMembers adapterGroupMembers;
    String name, designation, number, userUID, ceoID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        thegroupdescription = findViewById(R.id.thegroupdescription);
        back = findViewById(R.id.back);
        searchparticipants = findViewById(R.id.searchparticipants);
        membersrecycler = findViewById(R.id.membersrecycler);

        ceoID = getIntent().getStringExtra("ceoID");

        membersrecycler.setLayoutManager(new LinearLayoutManager(groupmembers.this));
        groupId = getIntent().getStringExtra("groupId");


        loadMembers();


    }

    private void loadMembers() {

        memberslist = new ArrayList<>();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Users").child(ceoID).child("Employees");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                memberslist.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    members model = ds.getValue(members.class);
                    memberslist.add(model);
                }

                adapterGroupMembers = new AdapterGroupMembers(memberslist, groupmembers.this);
                membersrecycler.setAdapter(adapterGroupMembers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}







