package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class employeeDetails extends AppCompatActivity {

    EditText employeeEmailId, employeePass, employeeName;
    String key;
    Button employeebtn;
    DatabaseReference ref;
    FirebaseAuth auth;
    EditText employeeNum;
    Spinner spinner;
    private List<String> fields = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        employeeEmailId = findViewById(R.id.employeeEmailID);
        employeePass = findViewById(R.id.employeePass);
        employeeName = findViewById(R.id.employeeName);
        employeebtn = findViewById(R.id.employeeSignUpbtn);
        spinner = findViewById(R.id.field);
        employeeNum = findViewById(R.id.employeeNum);

        key = getIntent().getStringExtra("key");
        auth = FirebaseAuth.getInstance();
        fields.add("Post Office");
        fields.add("Cashier and Shelves");
        fields.add("Garage Duty");
        fields.add("Cash & Carry");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner,R.id.spinner_item,fields);
        spinner.setAdapter(adapter);


        employeebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(employeeEmailId.getText().toString()) ||
                        TextUtils.isEmpty(employeePass.getText().toString())||
                        TextUtils.isEmpty(employeeNum.getText().toString())||
                        TextUtils.isEmpty(employeeName.getText().toString())) {
                    Toast.makeText(employeeDetails.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                } else {
                    //loadingDialog.startLoadingDialog();
                    auth.createUserWithEmailAndPassword(employeeEmailId.getText().toString(), employeePass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userUId = auth.getCurrentUser().getUid();
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", employeeName.getText().toString());
                                hashMap.put("number",employeeNum.getText().toString());
                                hashMap.put("designation",spinner.getSelectedItem().toString());
                                hashMap.put("ceoID",key);
                                hashMap.put("position", "employee");
                                hashMap.put("userUID", userUId);
                                DatabaseReference signUpRef = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
                                signUpRef.setValue(hashMap);
                                DatabaseReference employeeList = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Employees");
                                employeeList.child(auth.getCurrentUser().getUid()).setValue(hashMap);

                                Toast.makeText(employeeDetails.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                                Intent j = new Intent(employeeDetails.this,dashboard.class);
                                j.putExtra("key",key);
                                startActivity(j);




                            }
                        }
                    });
                }
            }
        });


    }
}
