package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class dashboard extends AppCompatActivity {

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    SimpleDateFormat simpleDateFormat;
    TextView liveTime;
    TextView liveDate;
    String time;
    ImageButton homebtn;
    ImageButton targetsbtn;
    FirebaseAuth auth;
    ImageButton chatbtn;
    ImageButton groupworkbtn;
    TextView accountInfo;

    RecyclerView announcements;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    DatabaseReference ref;
    AdapterAnnouncement adapterAnnouncement;
    ArrayList<Announcements> list;
    String globalCeoID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();
        homebtn= findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);
        accountInfo = findViewById(R.id.accountInfo);

       // key = getIntent().getStringExtra("key");
        String ceoID = getIntent().getStringExtra("ceoID");
        announcements = findViewById(R.id.announcements);
        announcements.setLayoutManager(new LinearLayoutManager(dashboard.this));

        saveData();


        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("ceoID");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                globalCeoID = snapshot.getValue().toString();
                final String key = globalCeoID;

                final DatabaseReference inbox = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Inbox");




                inbox.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list = new ArrayList<>();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            list.add(ds.getValue(Announcements.class));
                        }
                        adapterAnnouncement = new AdapterAnnouncement(list, dashboard.this);
                        announcements.setAdapter(adapterAnnouncement);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("inboxRead", "Failed to read value.", error.toException());
                    }
                });



                accountInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(dashboard.this, settings.class));
                    }
                });


                targetsbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(dashboard.this, employeeSalary.class ).putExtra("key", key));
                    }
                });

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(key);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String Chatname = snapshot.child("name").getValue().toString();

                        chatbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent (dashboard.this, chatActivity.class)
                                        .putExtra("UserUID", auth.getCurrentUser().getUid())
                                        .putExtra("ceoID", key)
                                        .putExtra("Chatname", Chatname));

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                groupworkbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(dashboard.this, CashNCarryEmployee.class).putExtra("key", key));
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
