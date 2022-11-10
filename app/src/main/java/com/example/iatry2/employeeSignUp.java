package com.example.iatry2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class employeeSignUp extends AppCompatActivity {
    EditText entercode;
    Button checkCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_sign_up);
        entercode = findViewById(R.id.enterCode);
        checkCode = findViewById(R.id.checkCode);

        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(entercode.getText().toString())){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Code");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(entercode.getText().toString()).exists()){

                                String bName = dataSnapshot.child(entercode.getText().toString()).child("businessName").getValue().toString();
                                final String key = dataSnapshot.child(entercode.getText().toString()).child("key").getValue().toString();
                                final AlertDialog.Builder builder = new AlertDialog.Builder(employeeSignUp.this);
                                builder.setTitle("Are you Sure?");
                                builder.setCancelable(false);
                                builder.setMessage("Do you want to join "+bName);
                                builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(employeeSignUp.this,employeeDetails.class);
                                        i.putExtra("key",key);
                                        startActivity(i);
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else if(!(entercode.getText().toString().length() ==5)){
                    Toast.makeText(employeeSignUp.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(employeeSignUp.this, "Please enter a code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
