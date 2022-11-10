package com.example.iatry2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private Button signIN;
    private Button signUP;
    EditText textPass;
    EditText textID;
    private FirebaseAuth mAuth;
    EditText textName;
    private String loggedIn;
    DatabaseReference Users;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Users = FirebaseDatabase.getInstance().getReference().child("Users");

        final EditText Name = findViewById(R.id.textName);
        mAuth = FirebaseAuth.getInstance();
        Button buttonIN = findViewById(R.id.signIN);
        Button buttonUP = findViewById(R.id.signUP);
        final EditText Pass = findViewById(R.id.textPass);
        final EditText Mail = findViewById(R.id.textID);

        loadData();


        buttonIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(Mail.getText().toString()) && !TextUtils.isEmpty(Pass.getText().toString())) {

                    mAuth.signInWithEmailAndPassword(Mail.getText().toString(), Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful()) {
                                final DatabaseReference position = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
                                position.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String positionCheck = dataSnapshot.child("position").getValue().toString();

                                        if (positionCheck.equals("CEO")) {
                                            Intent i = new Intent(LoginActivity.this, ceodashboard.class);
                                            startActivity(i);
                                        } else if (positionCheck.equals("employee")) {
                                            String key = dataSnapshot.child("ceoID").getValue().toString();
                                            Log.d("CHECK", key);
                                            Intent i = new Intent(LoginActivity.this, dashboard.class).putExtra("key", key);
                                            startActivity(i);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Invalid Login Credentials!", Toast.LENGTH_LONG).show();
                                Mail.setText("");
                                Pass.setText("");
                            }
                        }
                        });
                    }
            }
        });


        buttonUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, choosingLogin.class);
                startActivity(i);
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("auto", MODE_PRIVATE);
        loggedIn = sharedPreferences.getString("login", " ");
        if (loggedIn.equals(" ")) {


        } else {
            final DatabaseReference position = FirebaseDatabase.getInstance().getReference().child("Users").child(loggedIn);

            position.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String positionCheck = dataSnapshot.child("position").getValue().toString();

                    if (positionCheck.equals("CEO")) {
                        Intent i = new Intent(LoginActivity.this, ceodashboard.class);
                        startActivity(i);
                    } else if (positionCheck.equals("employee")) {
                        String key = dataSnapshot.child("ceoID").getValue().toString();
                        Intent j = new Intent(LoginActivity.this, dashboard.class);
                        j.putExtra("key", key).putExtra("ceoID", key);
                        Log.d("DEBUG", key);
                        startActivity(j);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}
