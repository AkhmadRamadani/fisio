package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Notification_Receiver extends AppCompatActivity {
    String message = "";
    String title = "";
    String type = "";
    String longi = "";
    String lat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        title = bundle.getString("title");
        type = bundle.getString("type");
        longi = bundle.getString("long");
        lat = bundle.getString("lat");

        if (type.equals("emergency")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData((android.net.Uri.parse("https://www.google.com/maps/search/?api=1&query=" + lat + "," + longi)));
            startActivity(intent);

            finish();


        }
    }
}