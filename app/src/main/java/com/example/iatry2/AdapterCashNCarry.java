package com.example.iatry2;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterCashNCarry extends RecyclerView.Adapter<AdapterCashNCarry.ViewHolder>{

    private Context context;
    private String key;

    private ArrayList<cashncarry_list> listItems;

    public AdapterCashNCarry(Context context, ArrayList<cashncarry_list> listItems, String key) {
        this.context = context;
        this.listItems = listItems;
        this.key = key;
    }






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cashncarrylist, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        cashncarry_list listItem = listItems.get(position);

        holder.Item.setText(listItems.get(position).getItem());
        final String time = listItems.get(position).getTime();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));
        String timestamp = DateFormat.format("dd MMM yyyy, HH:mm",calendar).toString();
        holder.Time.setText(timestamp);
        holder.Name.setText(listItems.get(position).getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("Cash N'Carry")
                                .child(time);
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
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Item, Time, Name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Time = itemView.findViewById(R.id.timeAdded);
            Item = itemView.findViewById(R.id.addedItem);
            Name = itemView.findViewById(R.id.cashncarrylist_name);

        }
    }
}