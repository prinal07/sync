package com.example.iatry2;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterMessaging extends RecyclerView.Adapter<AdapterMessaging.MessagingHolder> {

    private ArrayList<messaging> messagingList;
    private Context context;


    public AdapterMessaging(ArrayList<messaging> messagingList, Context context) {
        this.messagingList = messagingList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_box,parent,false);
        return new MessagingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagingHolder holder, final int position) {

        holder.userName.setText(messagingList.get(position).getGroupTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, chatActivity.class);
                i.putExtra("ceoID", messagingList.get(position).getCreatedBy());
                i.putExtra("groupId", messagingList.get(position).getGroupId());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return messagingList.size();
    }
    static class MessagingHolder extends RecyclerView.ViewHolder{

        TextView userName;

        private MessagingHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);

        }
    }
}
