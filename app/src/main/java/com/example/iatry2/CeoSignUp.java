package com.example.iatry2;

import android.content.ClipData;
import android.content.ClipboardManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class CeoSignUp extends AppCompatActivity {

    Button signIN;
    Button signUP2;
    EditText textPass;
    EditText textID;
    private FirebaseAuth mAuth;
    EditText textName;
    DatabaseReference Users;
    Button codeGenerator;
    EditText businessName;
    ImageButton copyCODE;
    TextView codeTextview;
    String generatedCode;
    Integer n = 1;
    Integer k = 001;
    Integer x;
    Integer y;
    private int MAX_LENGTH = 5;
    public static final String ALLOWED_CHARS = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplayout);

        Users = FirebaseDatabase.getInstance().getReference().child("Users");

        final LoadingDialog loadingDialog = new LoadingDialog(this);

        final EditText Name = findViewById(R.id.textName);
        mAuth = FirebaseAuth.getInstance();
        Button buttonUP2 = findViewById(R.id.signUP2);
        final EditText Pass = findViewById(R.id.textPass);
        final EditText Mail = findViewById(R.id.textID);
        final EditText businessName = findViewById(R.id.businessName);
        final ImageButton copyCODE = findViewById(R.id.copyCODE);
        final TextView codeTextview = findViewById(R.id.codeTextview);

        buttonUP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Mail.getText().toString()) ||
                        TextUtils.isEmpty(Pass.getText().toString()) ||
                        TextUtils.isEmpty(businessName.getText().toString()) ||
                        TextUtils.isEmpty(Name.getText().toString())) {
                    Toast.makeText(CeoSignUp.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                } else {
                    //loadingDialog.startLoadingDialog();
                    mAuth.createUserWithEmailAndPassword(Mail.getText().toString(), Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", Name.getText().toString());
                                hashMap.put("businessName", businessName.getText().toString());
                                hashMap.put("position", "CEO");
                                DatabaseReference signUpRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
                                signUpRef.setValue(hashMap);
                                Toast.makeText(CeoSignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                final Random random = new Random();
                                final StringBuilder sb = new StringBuilder(MAX_LENGTH);
                                for (int i = 0; i < MAX_LENGTH; i++) {
                                    sb.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));

                                }
                                final String randomText = sb.toString();

                                codeTextview.setVisibility(View.VISIBLE);
                                copyCODE.setVisibility(View.VISIBLE);
                                //loadingDialog.dismissDialog();

                                codeTextview.setText(randomText);

                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                ref.child("Code").child(randomText).child("key").setValue(mAuth.getCurrentUser().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String ceoName = dataSnapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("businessName").getValue().toString();
                                        ref.child("Code").child(randomText).child("businessName").setValue(ceoName);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });





                            }
                        }
                    });
                }
            }
        });


        copyCODE.setOnClickListener(new View.OnClickListener() {   // copying code only after it is generated
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("CopiedData", codeTextview.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(CeoSignUp.this, "Copied", Toast.LENGTH_LONG).show();



                Intent h = new Intent(CeoSignUp.this,ceodashboard.class);
                startActivity(h);


            }
        });


    }
}