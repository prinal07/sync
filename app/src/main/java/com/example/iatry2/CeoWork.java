package com.example.iatry2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class CeoWork extends AppCompatActivity {
    CardView employeeCard, debtCard, stockCard;
    ImageButton homebtn, targetsbtn, chatbtn, groupworkbtn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceo_work);

        homebtn = findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);

        employeeCard = findViewById(R.id.employeeCard);
        stockCard = findViewById(R.id.stockCard);
        auth = FirebaseAuth.getInstance();


        employeeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeoWork.this, CeoSetSalary.class));


            }
        });


        stockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CeoWork.this, StockManagement.class));

            }
        });


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CeoWork.this, ceodashboard.class));

            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(CeoWork.this, CeoChats.class);
                j.putExtra("ceoID", auth.getCurrentUser().getUid().toString());
                startActivity(j);
            }
        });


        groupworkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CeoWork.this, CashNCarry.class);
                i.putExtra("key", auth.getCurrentUser().getUid().toString());
                startActivity(i);

            }
        });


    }

}