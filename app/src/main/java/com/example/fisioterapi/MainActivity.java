package com.example.fisioterapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisioterapi.auth.AuthService;
import com.example.fisioterapi.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.type.DateTime;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView btn_m1, btn_m2, btn_m3, btn_m4, btn_m5, iconPeople, btnLogout;
    TextView displayName, displayGenderAge;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    //    User data from firebase firestore but using map
    UserModel userModel;

    AuthService authService = new AuthService();

//    DateTime


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_m1 = findViewById(R.id.btnm1);
        btn_m2 = findViewById(R.id.btnm2);
        btn_m3 = findViewById(R.id.btnm3);
        btn_m4 = findViewById(R.id.btnm4);
        btn_m5 = findViewById(R.id.btnm5);
        displayName = findViewById(R.id.displayName);
        displayGenderAge = findViewById(R.id.displayGenderAge);
        iconPeople = findViewById(R.id.icon_people);
        btnLogout = findViewById(R.id.btnLogout);

        btn_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RealtimeSensor.class);
                intent.putExtra("nama", userModel.getName());
                intent.putExtra("umur", userModel.getUmur());
                intent.putExtra("alamat", userModel.getAlamat());
                intent.putExtra("jenisKelamin" , userModel.getGender());
                startActivity(intent);
            }
        });
        btn_m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Edukasi.class));
            }
        });
        btn_m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HospitalList.class));
            }
        });
        btn_m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Emergency.class));
            }
        });
        btn_m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListDoctor.class));
            }
        });

        getUserData();
        displayName.setOnClickListener(view -> {
            getUserData();
        });

        // retrieve fcm token
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> {
            Log.d("FCM Token", "onCreate: " + s);
        });

        btnLogout.setOnClickListener(view -> {
            authService.logout(getApplicationContext());
            startActivity(new Intent(getApplicationContext(), MenuLogin.class));
            finish();
        });

    }

    private void getUserData() {
        db.collection("users").document(fbAuth.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userModel = new UserModel(
                        fbAuth.getUid(),
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
                displayName.setText(userModel.getName());
                displayGenderAge.setText(userModel.getEmail());
            } else {
                Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}