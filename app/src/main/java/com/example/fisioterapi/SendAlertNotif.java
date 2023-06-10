package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fisioterapi.retrofit.RetrofitService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class SendAlertNotif extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;


    ImageView saturasiOksigenBtn, terjatuhBtn, kejangBtn, perawatanDiRmhBtn;
    RetrofitService retrofitService = new RetrofitService();

    String TAG = "SendAlertNotif";
    String latitude, longitude;

    LinearLayout progressBarLayout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert_notif);
        askLocationPermission();

        saturasiOksigenBtn = findViewById(R.id.btnemergency1);
        terjatuhBtn = findViewById(R.id.btnemergency2);
        kejangBtn = findViewById(R.id.btnemergency3);
        perawatanDiRmhBtn = findViewById(R.id.btnemergency4);
        progressBarLayout = findViewById(R.id.progressBarLayout);

        saturasiOksigenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmergencyNotif("Saturasi Oksigen Rendah");
            }
        });

        terjatuhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmergencyNotif("Terjatuh");
            }
        });

        kejangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmergencyNotif("Kejang Syaraf/Otot");
            }
        });

        perawatanDiRmhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmergencyNotif("Perawatan di Rumah");
            }
        });
    }

    private void askLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            try {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(this, "Please turn on your GPS", Toast.LENGTH_SHORT).show();
                } else {
                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (locationGPS != null) {
                        double lat = locationGPS.getLatitude();
                        double longi = locationGPS.getLongitude();
                        latitude = String.valueOf(lat);
                        longitude = String.valueOf(longi);
                    } else {

                        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to find location 2.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEmergencyNotif(String reason) {
        progressBarLayout.setVisibility(View.VISIBLE);
        retrofitService.getRetrofitAPI().sendEmergencyNotif(latitude, longitude, reason, mAuth.getUid()).enqueue(new retrofit2.Callback<Map<String, String>>() {
            @Override
            public void onResponse(retrofit2.Call<Map<String, String>> call, retrofit2.Response<Map<String, String>> response) {
                Log.d("Emergency", "onCall: " + call.request().url());
                Log.d("Emergency", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    Toast.makeText(SendAlertNotif.this, "Berhasil Mengirim Notifikasi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SendAlertNotif.this, "Gagal Mengirim Notifikasi", Toast.LENGTH_SHORT).show();
                }
                progressBarLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(retrofit2.Call<Map<String, String>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SendAlertNotif.this, "Gagal Mengirim Notifikasi", Toast.LENGTH_SHORT).show();
                progressBarLayout.setVisibility(View.GONE);
            }
        });
    }

}