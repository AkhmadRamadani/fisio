package com.example.fisioterapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fisioterapi.R;
import com.example.fisioterapi.models.ChatModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<ChatModel> chatList;
    LayoutInflater inflater;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatList) {
        this.chatList = chatList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = inflater.inflate(R.layout.chat_card, parent, false);
        return new ViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chat_tv.setText(chatList.get(position).getChat());
        holder.sender_tv.setText(chatList.get(position).getSenderName());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chat_tv, sender_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            chat_tv = itemView.findViewById(R.id.chat_tv);
            sender_tv = itemView.findViewById(R.id.sender_tv);
        }
    }
}
