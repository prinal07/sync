package com.example.iatry2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddStockItemPage extends AppCompatActivity {

    EditText editTextName, editTextPrice;
    Spinner spinner;
    private List<String> fields = new ArrayList<>();
    FirebaseAuth auth;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_item_page);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        create = findViewById(R.id.create);

        spinner = findViewById(R.id.spinner);
        auth = FirebaseAuth.getInstance();


        fields.add("Alcohol");
        fields.add("Confectionery");  //sweets
        fields.add("Drinks");
        fields.add("Grocery");
        fields.add("Sandwiches");
        fields.add("Car Care");
        fields.add("Cigarettes");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner,R.id.spinner_item,fields);
        spinner.setAdapter(adapter);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String timestamp = String.valueOf(System.currentTimeMillis());
                String ceoID = auth.getCurrentUser().getUid();
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Stock").child(ceoID).child(timestamp);

                final String name = editTextName.getText().toString();
                final String type = spinner.getSelectedItem().toString();
                final String price = editTextPrice.getText().toString();


                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("ItemName",name);
                        hashMap.put("ItemType", type);
                        hashMap.put("Price", price);
                        hashMap.put("timestamp", timestamp);

                        ref.setValue(hashMap);

                Toast.makeText(AddStockItemPage.this, "Item Added", Toast.LENGTH_LONG).show();


            }
        });




    }
}