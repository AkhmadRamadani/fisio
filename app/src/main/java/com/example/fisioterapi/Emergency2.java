package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Timer;
import java.util.TimerTask;

public class Emergency2 extends AppCompatActivity {

    ImageView buttonEmergency;

    FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference buzzerConditionActive = firebaseDatabase.getReference("buzzer_condition_active");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency2);
        buttonEmergency = findViewById(R.id.button_emergency);

        buttonEmergency.setOnClickListener(view -> {
            buzzerConditionActive.setValue(true);
            Toast.makeText(this, "Buzzer Diaktifkan", Toast.LENGTH_SHORT).show();

//            Timer 10 detik untuk matikan buzzer
           new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                     buzzerConditionActive.setValue(false);
                }
              }, 10000);

        });
    }

}