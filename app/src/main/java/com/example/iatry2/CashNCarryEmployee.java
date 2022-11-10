package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CashNCarryEmployee extends AppCompatActivity {
    TextView cashncarry;
    RecyclerView cashncarry_items;
    ImageButton addItems;
    AdapterCashNCarry adapterCashNCarry;
    ArrayList<cashncarry_list> listItems;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
    String name;
    String key;

    ImageButton homebtn, targetsbtn, chatbtn, groupworkbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_n_carry_employee);
        auth = FirebaseAuth.getInstance();
        cashncarry = findViewById(R.id.cashncarry);
        cashncarry_items = findViewById(R.id.cashncarry_items);
        addItems = findViewById(R.id.addItems);
        homebtn = findViewById(R.id.homebtn);
        targetsbtn = findViewById(R.id.targetsbtn);
        chatbtn = findViewById(R.id.chatbtn);
        groupworkbtn = findViewById(R.id.groupworkbtn);

        layoutManager = new LinearLayoutManager(this);
        cashncarry_items.setLayoutManager(layoutManager);

        listItems = new ArrayList<>();

        showList();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("ceoID");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String key = snapshot.getValue().toString();

                addItems.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CashNCarryEmployee.this);
                        final View view = LayoutInflater.from(CashNCarryEmployee.this).inflate(R.layout.activity_adding_item_dialog, null);
                        final EditText editText = view.findViewById(R.id.editText);
                        final Button quitdialog = view.findViewById(R.id.quitdialog);


                        final AlertDialog dialog = builder.create();
                        dialog.setView(view);
                        dialog.show();

                        quitdialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("name");
                                nameRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        name = dataSnapshot.getValue().toString();


                                        final String Item = editText.getText().toString();
                                        final String user = auth.getCurrentUser().getUid().toString();


                                        final DatabaseReference listRef = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Cash N'Carry");

                                        final String Time = String.valueOf(System.currentTimeMillis());
                                        if (!TextUtils.isEmpty(Item)) {

                                            HashMap<String, String> list = new HashMap<>();
                                            list.put("Item", Item);
                                            list.put("Name", name);
                                            list.put("Time", Time);

                                            listRef.child(Time).setValue(list);

                                        }


                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        });

                        DatabaseReference getName = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("name");
                        getName.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final String ChatName = snapshot.getValue().toString();

                                chatbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent j = new Intent(CashNCarryEmployee.this, chatActivity.class);
                                        j.putExtra("ceoID", key);
                                        j.putExtra("UserUID", auth.getCurrentUser().getUid());
                                        j.putExtra("Chatname", ChatName);
                                        startActivity(j);
                                    }
                                });
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
                        startActivity(new Intent(CashNCarryEmployee.this, dashboard.class));
                    }
                });


                targetsbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CashNCarryEmployee.this, employeeSalary.class).putExtra("key", key));
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }




        });

    }
    private void showList() {
        listItems = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("ceoID");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String key = snapshot.getValue().toString();

                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Cash N'Carry");
                dr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listItems.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            cashncarry_list model = ds.getValue(cashncarry_list.class);
                            listItems.add(model);

                        }


                        adapterCashNCarry = new AdapterCashNCarry(CashNCarryEmployee.this, listItems, key);
                        cashncarry_items.setAdapter(adapterCashNCarry);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
