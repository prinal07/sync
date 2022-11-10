package com.example.iatry2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class choosingLogin extends AppCompatActivity {

    Button signIN;
    Button signUP;
    EditText textPass;
    EditText textID;
    private FirebaseAuth mAuth;
    EditText textName;
    DatabaseReference Users;
    Button codeGenerator;
    EditText businessName;
    ImageButton employeeTag;
    ImageButton ceoTag;
    ImageView lanyard1;
    ImageView lanyard2;
    TextView textCEO;
    TextView textEMPLOYEE;
    ImageView clickable1;
    ImageView clickable2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_login);

        Users= FirebaseDatabase.getInstance().getReference().child("Users");

        final EditText Name = findViewById(R.id.textName);
        mAuth = FirebaseAuth.getInstance();
        final EditText Pass = findViewById(R.id.textPass);
        final EditText Mail = findViewById(R.id.textID);
        EditText businessName = findViewById(R.id.businessName);
        Button employeeTag = findViewById(R.id.employeeTag);
        Button ceoTag = findViewById(R.id.ceoTag);

        ceoTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(choosingLogin.this, CeoSignUp.class);
                startActivity(i);
            }
        });


        employeeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(choosingLogin.this,employeeSignUp.class);
                startActivity(i);
            }
        });



    }
}