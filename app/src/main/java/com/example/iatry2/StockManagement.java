package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.common.collect.Comparators;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class StockManagement extends AppCompatActivity {

    ImageButton addItem, homebtn,targetsbtn,chatbtn, groupworkbtn;
    RecyclerView stockRecyclerView;
    FirebaseAuth auth;
    ArrayList<Stock> listItems;
    RecyclerView.LayoutManager layoutManager;
    AdapterStock adapterStock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_management);

        addItem = findViewById(R.id.addItem);
        stockRecyclerView = findViewById(R.id.stockRecyclerView);
        auth = FirebaseAuth.getInstance();
        layoutManager = new LinearLayoutManager(this);
        stockRecyclerView.setLayoutManager(layoutManager);

        homebtn = findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);



        listItems = new ArrayList<>();

        showList();


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StockManagement.this, AddStockItemPage.class);
                startActivity(i);


            }
        });


    }

    private void showList() {
        final String key = auth.getCurrentUser().getUid();
        listItems = new ArrayList<>();

        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Stock").child(key);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                listItems.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Stock  model = ds.getValue(Stock.class);
                    listItems.add(model);

                }

                adapterStock = new AdapterStock(listItems, StockManagement.this);
                stockRecyclerView.setAdapter(adapterStock);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StockManagement.this, ceodashboard.class));

            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(StockManagement.this, CeoChats.class);
                j.putExtra("ceoID", auth.getCurrentUser().getUid().toString());
                startActivity(j);
            }
        });


        groupworkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StockManagement.this, CashNCarry.class);
                i.putExtra("key", auth.getCurrentUser().getUid().toString());
                startActivity(i);

            }
        });

        targetsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StockManagement.this, CeoWork.class));
            }
        });
    }


}