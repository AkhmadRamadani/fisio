package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisioterapi.auth.AuthService;
import com.example.fisioterapi.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Maindoctor extends AppCompatActivity {

    ImageView btn_con,btnLogout;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserModel userModel;
    AuthService authService = new AuthService();
    TextView displayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindoctor);

        btn_con = findViewById(R.id.btncon);
        displayName = findViewById(R.id.displayName);
        btnLogout = findViewById(R.id.btnLogout);

        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListPasien.class));
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

        getUserData();

    }

    private void getUserData() {
        db.collection("users").document(fAuth.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userModel = new UserModel(
                        fAuth.getUid(),
                        task.getResult().get("password") != null ? task.getResult().get("password").toString() : "",
                        task.getResult().get("umur") != null ? task.getResult().get("umur").toString() : "",
                        task.getResult().get("role") != null ? task.getResult().get("role").toString() : "",
                        task.getResult().get("gender") != null ?
                                task.getResult().get("gender").toString() : "",
                        task.getResult().get("phone") != null ? task.getResult().get("phone").toString() : "",
                        task.getResult().get("name") != null ? task.getResult().get("name").toString() : "",
                        task.getResult().get("email") != null ? task.getResult().get("email").toString() : "",
                        task.getResult().get("alamat") != null ? task.getResult().get("alamat").toString() : "",
                        task.getResult().get("hospital_id") != null ? task.getResult().get("hospital_id").toString() : ""

                );
                displayName.setText("Hai " + userModel.getName());
            } else {
                Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}