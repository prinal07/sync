package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CeoChats extends AppCompatActivity {

    String key, ceoID;
    DatabaseReference ref;
    FirebaseAuth auth;

    RecyclerView chatRecycler;
    ArrayList<members> userList;
    AdapterGroupMembers adapterGroupMembers;
    ImageButton homebtn, targetsbtn, chatbtn, groupworkbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceo_chats);

        auth = FirebaseAuth.getInstance();
        key = getIntent().getStringExtra("key");
        ceoID = getIntent().getStringExtra("ceoID");
        chatRecycler = findViewById(R.id.chatRecycler);

        homebtn = findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);

        chatRecycler.setLayoutManager(new LinearLayoutManager(CeoChats.this));
        loadMembers();



        final DatabaseReference groups = FirebaseDatabase.getInstance().getReference().child("Chats");


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeoChats.this, ceodashboard.class));

            }
        });

        groupworkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CeoChats.this, CashNCarry.class);
                i.putExtra("key", auth.getCurrentUser().getUid().toString());
                startActivity(i);

            }
        });

        targetsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeoChats.this, CeoWork.class));
            }
        });


    }

    private void loadMembers() {

        userList = new ArrayList<>();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Users").child(ceoID).child("Employees");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    members model = ds.getValue(members.class);
                    userList.add(model);
                }

                adapterGroupMembers = new AdapterGroupMembers(userList, CeoChats.this);
                chatRecycler.setAdapter(adapterGroupMembers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
