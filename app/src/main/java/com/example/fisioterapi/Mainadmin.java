package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fisioterapi.auth.AuthService;
import com.google.firebase.auth.FirebaseAuth;

public class Mainadmin extends AppCompatActivity {
    ImageView btn_ser, dp_1, adminIcPeople, btnLogout;
    AuthService authService = new AuthService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainadmin);

        dp_1 = findViewById(R.id.dp1);
        adminIcPeople = findViewById(R.id.admin_ic_people);
        btnLogout = findViewById(R.id.btnLogout);

        dp_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListPasienAdmin.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authService.logout(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), MenuLogin.class));
                finish();
            }
        });

    }
}