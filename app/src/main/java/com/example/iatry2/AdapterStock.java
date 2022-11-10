package com.example.iatry2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.StockHolder> {

    private Context context;
    private ArrayList<Stock> list;
    String positionCheck;


    public AdapterStock(ArrayList<Stock> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item,parent,false);
        return new StockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockHolder holder, final int position) {

        holder.stockItemName.setText(list.get(position).getItemName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "Works");
                String name =  list.get(position).getItemName();
                String type =  list.get(position).getItemType();
                String price = list.get(position).getPrice();

                EditStockDialog dialog = new EditStockDialog();
                dialog.viewInfoDialog(name, type, price, context);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final FirebaseAuth auth = FirebaseAuth.getInstance();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Stock")
                                .child(auth.getCurrentUser().getUid()).child(list.get(position).getTimestamp());

                        ref.removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class StockHolder extends RecyclerView.ViewHolder{

        TextView stockItemName, DisplayItemName, itemTypeText, priceText;

        private StockHolder(@NonNull View itemView) {
            super(itemView);

            stockItemName = itemView.findViewById(R.id.stockItemName);



        }
    }



}
