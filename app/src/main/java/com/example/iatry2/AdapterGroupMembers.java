package com.example.iatry2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AdapterGroupMembers extends RecyclerView.Adapter<AdapterGroupMembers.MembersHolder> {

    private ArrayList<members> membersArrayList;
    private Context context;
    String userUID;
    String ceoID;
    String name;
    String designation;
    String number;
    String groupId;
    String timestamp;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    public AdapterGroupMembers(ArrayList<members> membersArraylist, Context context) {
        this.membersArrayList = membersArraylist;
        this.context = context;

    }

    @NonNull
    @Override
    public MembersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_box, parent, false);
        return new MembersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MembersHolder holder, final int position) {

        members m = membersArrayList.get(position);

        holder.userName.setText( membersArrayList.get(position).getName());

        Log.d("name", membersArrayList.get(position).getName());

        userUID = membersArrayList.get(position).getUserUID();
        ceoID = auth.getCurrentUser().getUid();
        DatabaseReference chat = FirebaseDatabase.getInstance().getReference().child("Chats").child(ceoID).child(userUID);
        HashMap details = new HashMap();
        details.put("ceoID", ceoID);
        details.put("UserUID", userUID);
        //chat.setValue(details);    //this is resetting the chat value, which overrides the messages.


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUID = membersArrayList.get(position).getUserUID();
                ceoID = auth.getCurrentUser().getUid();

                Log.d("ceoID", ceoID);


                Intent i = new Intent(context, chatActivity.class).putExtra("Chatname", membersArrayList.get(position)
                        .getName()).putExtra("UserUID", membersArrayList.get(position).getUserUID())
                        .putExtra("ceoID", ceoID);

                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return membersArrayList.size();
    }

    static class MembersHolder extends RecyclerView.ViewHolder {
        TextView userName;

        private MembersHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);

        }
    }
}
