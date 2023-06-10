package com.example.fisioterapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fisioterapi.adapters.ChatAdapter;
import com.example.fisioterapi.models.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Consul extends AppCompatActivity {
    Button sendButton;
    EditText chatInput;
    RecyclerView chatRv;
    ImageView avatar;
    TextView name;

    DatabaseReference chatRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ArrayList<ChatModel> chatList = new ArrayList<>();
    ChatAdapter chatAdapter;
    boolean isDoctor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul);

        sendButton = findViewById(R.id.send_button);
        chatInput = findViewById(R.id.chat_input);
        chatRv = findViewById(R.id.chat_rv);
        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.doctor_name);

        Intent intent = getIntent();
        String doctorUid = intent.getStringExtra("doctor_id");
        String doctorName = intent.getStringExtra("doctor_name");
        isDoctor = intent.getBooleanExtra("fromDoctor", false);

        name.setText(doctorName);
        if (isDoctor) {

            chatRef = FirebaseDatabase.getInstance().getReference("room_chat").child(mAuth.getUid()).child(doctorUid);
        } else {

            chatRef = FirebaseDatabase.getInstance().getReference("room_chat").child(doctorUid).child(mAuth.getUid());
        }

        Log.d("Consul.java", "onCreate: " + chatRef.toString());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat(view);
            }
        });

        getChatList();
    }

    public void sendChat(View view) {
        String chat = chatInput.getText().toString();
        chatInput.setText("");
        String currentTime = String.valueOf(System.currentTimeMillis());
        ChatModel chatModel = new ChatModel(chat, mAuth.getUid(), currentTime);
        chatRef.push().setValue(chatModel);
    }

    public void initAdapter() {
        chatAdapter = new ChatAdapter(getApplicationContext(), chatList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        chatRv.setAdapter(chatAdapter);
        chatRv.setLayoutManager(layoutManager);
        chatRv.scrollToPosition(chatList.size() - 1);

    }

    private void getChatList() {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String senderName = dataSnapshot.child("sender").getValue().toString().equals(mAuth.getUid()) ? "You" : name.getText().toString();
                    ChatModel chatModel = new ChatModel(
                            dataSnapshot.child("chat").getValue().toString(),
                            dataSnapshot.child("sender").getValue().toString(),
                            dataSnapshot.child("timestamp").getValue().toString(),
                            senderName

                    );
                    chatList.add(chatModel);
                }
                initAdapter();
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}