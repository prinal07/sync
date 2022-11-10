package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class chatActivity extends AppCompatActivity {

    TextView groupnameclickable;
    RecyclerView messages;
    EditText typesomething;
    ImageButton sendmessage;
    ImageButton back;
    String groupId, ceoID;
    String groupTitle;
    FirebaseAuth auth;
    ImageButton addparticipant;
    String sender;
    String Chatname;
    ArrayList<Chat> list;
    AdapterChat adapterchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        ceoID = getIntent().getStringExtra("ceoID");
        groupnameclickable = findViewById(R.id.groupnameclickable);
        messages = findViewById(R.id.messages);
        sendmessage = findViewById(R.id.sendmessage);
        typesomething = findViewById(R.id.typesomething);
        back = findViewById(R.id.back);
        auth = FirebaseAuth.getInstance();

        messages.setLayoutManager(new LinearLayoutManager(chatActivity.this));

        final String UserUID = getIntent().getStringExtra("UserUID");
        Chatname = getIntent().getStringExtra("Chatname");
        ceoID = getIntent().getStringExtra("ceoID");
        groupnameclickable.setText(Chatname);

        final DatabaseReference addMessage = FirebaseDatabase.getInstance().getReference().child("Chats").child(ceoID).child(UserUID).child("Messages");

        loadChats();



        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String timestamp = String.valueOf(System.currentTimeMillis());
                String message = typesomething.getText().toString();
                typesomething.setText("");
                HashMap<String, String> messages = new HashMap<>();
                messages.put("Sender", auth.getCurrentUser().getUid());
                messages.put("Message", message);
                messages.put("Time", timestamp);

                addMessage.child(timestamp).setValue(messages);

                typesomething.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("position");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue().toString().equals("CEO")) {
                            startActivity(new Intent(chatActivity.this, CeoChats.class).putExtra("ceoID", auth.getCurrentUser().getUid()));
                        }
                        else {
                            startActivity(new Intent(chatActivity.this, dashboard.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }


    private void loadChats() {
        final String UserUID = getIntent().getStringExtra("UserUID").toString();
        list = new ArrayList<>();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Chats").child(ceoID).child(UserUID).child("Messages");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                Log.d("Hi", "No");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Chat model = ds.getValue(Chat.class);
                    list.add(model);
                    Log.d("Debug", "Yes");

                }
                adapterchat = new AdapterChat(list, chatActivity.this);
                messages.setAdapter(adapterchat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}

