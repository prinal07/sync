package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class employeeSalary extends AppCompatActivity {
    TextView monthtext, salaryvalue;
    FirebaseAuth auth;
    ImageButton homebtn,chatbtn, groupworkbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_salary);

        monthtext = findViewById(R.id.monthtext);
        salaryvalue = findViewById(R.id.salaryvalue);
        auth = FirebaseAuth.getInstance();
        homebtn = findViewById(R.id.homebtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);

        DatabaseReference check = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("ceoID");
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String key = snapshot.getValue().toString();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Salaries");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            final String nameCheck = ds.child("Employee Name").getValue().toString();
                            final String monthValue = ds.child("Month").getValue().toString();
                            final String salaryValue = ds.child("Salary").getValue().toString();
                            final String timestampValue = ds.child("timestamp").getValue().toString();


                            final String uid = auth.getCurrentUser().getUid();
                            DatabaseReference check = FirebaseDatabase.getInstance().getReference().child("Users");
                            check.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds: snapshot.getChildren()){
                                        String checking = ds.child("name").getValue().toString();

                                        if (nameCheck.equals(checking)){

                                            monthtext.setText(monthValue);
                                            salaryvalue.setText(salaryValue);




                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("name");
                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String ChatName = snapshot.getValue().toString();


                        chatbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j = new Intent(employeeSalary.this, chatActivity.class);
                                j.putExtra("ceoID",key);
                                j.putExtra("UserUID", auth.getCurrentUser().getUid());
                                j.putExtra("Chatname",ChatName);
                                startActivity(j);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                homebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(employeeSalary.this, dashboard.class));
                    }
                });

                groupworkbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(employeeSalary.this, CashNCarryEmployee.class).putExtra("key", key));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });







    }

}