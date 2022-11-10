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

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ChatHolder> {

    private ArrayList<Chat> chatList;
    private Context context;

    FirebaseAuth auth = FirebaseAuth.getInstance();


    public AdapterChat(ArrayList<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0){

            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_box,parent,false);
            return new ChatHolder(view2);

        }

        else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciever_messages, parent, false);
            return new ChatHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, final int position) {

        Chat model = chatList.get(position);
        holder.receiverMessage.setText(model.getMessage());
        String millis = chatList.get(position).getTime();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(millis));
        String timestamp = DateFormat.format("dd MMM, HH:mm",calendar).toString();
        holder.receiverTime.setText(timestamp);

    }

    @Override
    public int getItemViewType(int pos) {
        if(chatList.get(pos).getSender().equals(auth.getCurrentUser().getUid())){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    static class ChatHolder extends RecyclerView.ViewHolder{
        TextView receiverMessage, receiverTime;
        String sender;


        private ChatHolder(@NonNull View itemView) {
            super(itemView);

            receiverTime = itemView.findViewById(R.id.receiverTime);
             receiverMessage = itemView.findViewById(R.id.receiverMessage);



        }


    }
}
