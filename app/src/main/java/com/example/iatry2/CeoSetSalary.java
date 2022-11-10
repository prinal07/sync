package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CeoSetSalary extends AppCompatActivity {

    Spinner employeeSelection, month;
    private List<String> fields = new ArrayList<>();
    private List<String> months = new ArrayList<>();
    FirebaseAuth auth;
    EditText salaryAmount;
    Button addSalary;
    ImageButton homebtn, targetsbtn, chatbtn, groupworkbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceo_set_salary);

        auth = FirebaseAuth.getInstance();
        employeeSelection = findViewById(R.id.employeeSelection);
        month = findViewById(R.id.month);
        salaryAmount = findViewById(R.id.salaryAmount);
        addSalary = findViewById(R.id.addSalary);

        homebtn = findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);


        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, R.id.spinner_item, fields);
        //employeeSelection.setAdapter(adapter);

        final DatabaseReference employeeName = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Employees");


        Query query = employeeName.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String namess = dataSnapshot1.child("name").getValue(String.class);
                    fields.add(namess);

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CeoSetSalary.this, R.layout.spinner_layout, R.id.spinner_item, fields);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_layout);

                employeeSelection.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CeoSetSalary.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_layout,R.id.spinner_item,months);
        month.setAdapter(adapter);


        final DatabaseReference salary = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Salaries");

      addSalary.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final String timestamp = String.valueOf(System.currentTimeMillis());
              // changing the location of the TIMESTAMP declaration is causing it to return to homepage after onClick is done

              salary.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {


                      String employeename = employeeSelection.getSelectedItem().toString();
                      String selectedmonth = month.getSelectedItem().toString();

                      HashMap hashMap = new HashMap();
                      hashMap.put("Employee Name", employeename);
                      hashMap.put("Month", selectedmonth);
                      hashMap.put("Salary", salaryAmount.getText().toString());
                      hashMap.put("timestamp", timestamp);

                      Log.d("hashmap", hashMap.toString());
                      Log.d("timestamp",timestamp);

                      salary.child(timestamp).setValue(hashMap);


                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });




          }
      });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeoSetSalary.this, ceodashboard.class));

            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(CeoSetSalary.this, CeoChats.class);
                j.putExtra("ceoID", auth.getCurrentUser().getUid().toString());
                startActivity(j);
            }
        });


        groupworkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CeoSetSalary.this, CashNCarry.class);
                i.putExtra("key", auth.getCurrentUser().getUid().toString());
                startActivity(i);

            }
        });

        targetsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeoSetSalary.this, CeoWork.class));
            }
        });




    }


}














































































