package com.example.iatry2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class settings extends AppCompatActivity {

    EditText newMail, newPass, newNumber;
    TextView textlogout;
    Button confirm_button;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        newMail = findViewById(R.id.newMail);
        newPass = findViewById(R.id.newPass);
        textlogout = findViewById(R.id.textlogout);
        confirm_button = findViewById(R.id.confirm);
        newNumber = findViewById(R.id.newNumber);
        mAuth = FirebaseAuth.getInstance();



        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if (!TextUtils.isEmpty(newMail.getText().toString())){
                   mAuth.getCurrentUser().updateEmail(newMail.getText().toString());
                   Toast.makeText(settings.this, "Successful!", Toast.LENGTH_SHORT).show();

                }

               if (!TextUtils.isEmpty(newPass.getText().toString())){
                    mAuth.getCurrentUser().updatePassword(newPass.getText().toString());
                   Toast.makeText(settings.this, "Successful!", Toast.LENGTH_SHORT).show();
                }

               if (!TextUtils.isEmpty(newNumber.getText().toString())){

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("number");
                    ref.setValue(newNumber);
                   Toast.makeText(settings.this, "Successful!", Toast.LENGTH_SHORT).show();
                }

               if (TextUtils.isEmpty(newMail.getText().toString()) &&
                        TextUtils.isEmpty(newPass.getText().toString()) &&
                        TextUtils.isEmpty(newNumber.getText().toString())) {

                   Toast.makeText(settings.this, "Please fill at least 1 of the fields", Toast.LENGTH_SHORT).show();


               }
            }
        });


        textlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(settings.this, LoginActivity.class));
                SharedPreferences sharedPreferences = getSharedPreferences("auto", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", " ");
                editor.apply();


            }
        });









    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("auto", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", mAuth.getCurrentUser().getUid());
        editor.apply();
    }

}