package com.example.iatry2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class AdapterAnnouncement extends RecyclerView.Adapter<AdapterAnnouncement.AnnouncementsHolder> {

    private Context context;
    private ArrayList<Announcements> list;
    String positionCheck;


    public AdapterAnnouncement(ArrayList<Announcements> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AnnouncementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_message,parent,false);
        return new AnnouncementsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementsHolder holder, final int position) {


        holder.message.setText(list.get(position).getMessage());
        final String time = list.get(position).getTime();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));
        String timestamp = DateFormat.format("dd MMM yyyy, HH:mm",calendar).toString();
        holder.time.setText(timestamp);


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DatabaseReference positionRef = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("position");

                positionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       positionCheck = dataSnapshot.getValue().toString();

                        if(positionCheck.equals("CEO")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete Item?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference inboxView = FirebaseDatabase.getInstance().getReference().child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Inbox")
                                            .child(time);
                                    inboxView.removeValue();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class AnnouncementsHolder extends RecyclerView.ViewHolder{

        TextView message,time;

        private AnnouncementsHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.annMessage);
            time = itemView.findViewById(R.id.annTime);
        }
    }
}
