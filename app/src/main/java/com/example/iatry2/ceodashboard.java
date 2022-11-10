package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ceodashboard extends AppCompatActivity {

    String key;
    DatabaseReference ref;
    FirebaseAuth auth;
    EditText addAnnouncements;
    TextView toolbar;
    ImageButton addAnnouncementsbtn;
    ImageButton homebtn;
    ImageButton targetsbtn;
    ImageButton chatbtn;
    ImageButton groupworkbtn;
    TextView CEOaccountInfo;
    RecyclerView recyclerView;
    RecyclerView announcements;
    ArrayList<Announcements> list;
    AdapterAnnouncement adapterAnnouncement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceodashboard);


        auth = FirebaseAuth.getInstance();
        saveData();
        key = auth.getCurrentUser().getUid();
        addAnnouncements = findViewById(R.id.addAnnouncements);
        addAnnouncementsbtn = findViewById(R.id.addAnouncementbtn);
        homebtn = findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);
        CEOaccountInfo = findViewById(R.id.CEOaccountInfo);

        announcements = findViewById(R.id.announcements);
        announcements.setLayoutManager(new LinearLayoutManager(ceodashboard.this));

        showAnnouncements();

        final DatabaseReference inbox = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Inbox");

        addAnnouncementsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(addAnnouncements.getText().toString())) {
                    final String timestamp = String.valueOf(System.currentTimeMillis());

                    inbox.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("time", timestamp);
                            hashMap.put("message", addAnnouncements.getText().toString());

                            inbox.child(timestamp).setValue(hashMap);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(ceodashboard.this, "Enter something!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final DatabaseReference inboxView = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Inbox");


        inboxView.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    list.add(ds.getValue(Announcements.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("inboxRead", "Failed to read value.", error.toException());
            }
        });


        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ceodashboard.this, CeoChats.class);
                j.putExtra("ceoID", auth.getCurrentUser().getUid().toString());
                startActivity(j);
            }
        });


        groupworkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ceodashboard.this, CashNCarry.class);
                i.putExtra("key", key);
                startActivity(i);

            }
        });


        targetsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ceodashboard.this, CeoWork.class));

            }
        });

        CEOaccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ceodashboard.this, settings.class));
            }
        });



    }
// maybe the stutter is as it updates on data change and not using a method like this.
    private void showAnnouncements() {
        list = new ArrayList<>();
        final DatabaseReference inboxView = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Inbox");
        inboxView.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    list.add(ds.getValue(Announcements.class));

                }


                adapterAnnouncement = new AdapterAnnouncement(list,ceodashboard.this);
                announcements.setAdapter(adapterAnnouncement);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("auto", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", auth.getCurrentUser().getUid());
        editor.apply();
    }

    @Override
    public void onBackPressed() {

    }
}
