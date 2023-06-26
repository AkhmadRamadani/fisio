package com.example.fisioterapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RealtimeSensor extends AppCompatActivity {

    private TextView nilai_1, nilai_2, nilai_3, nilai_4, nama, umur, alamat, jenisKelamin, timerValue,
            nilaiFingerCurve2, nilaiFingerCurve3, nilaiFingerCurve4, nilaiFingerCurve5;

    private Firebase mRef1, mRef2, mRef3, mRef4, mRef5, mRef6, mRef7, mRef8;
    private Button btn_4, btn_5, btn_6;
    boolean bound;
    private Handler handler;
    private Runnable runnable;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    CountDownTimer countDownTimer;
    DatabaseReference databaseReference = firebaseDatabase.getReference("sensor").child(mAuth.getUid());
    DatabaseReference sensorDBReff = firebaseDatabase.getReference("sensor");

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    ArrayList<Map<String, String>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_sensor);

        nilai_1 = (TextView) findViewById(R.id.nilai1);
        nilai_2 = (TextView) findViewById(R.id.nilai2);
        nilai_3 = (TextView) findViewById(R.id.nilai3);
        nilai_4 = (TextView) findViewById(R.id.nilai4);
        nilaiFingerCurve2 = (TextView) findViewById(R.id.nilaifingercurve2);
        nilaiFingerCurve3 = (TextView) findViewById(R.id.nilaifingercurve3);
        nilaiFingerCurve4 = (TextView) findViewById(R.id.nilaifingercurve4);
        nilaiFingerCurve5 = (TextView) findViewById(R.id.nilaifingercurve5);


        mRef1 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/HeartBeat");
        mRef2 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/Sp02");
        mRef3 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/Sudut");
        mRef4 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/outflex_1");
        mRef5 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/outflex_2");
        mRef6 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/outflex_3");
        mRef7 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/outflex_4");
        mRef8 = new Firebase("https://mikrosuhu-default-rtdb.firebaseio.com/sensor/outflex_5");

        btn_4 = findViewById(R.id.btn4);
        btn_5 = findViewById(R.id.btn5);
        nama = findViewById(R.id.nama);
        umur = findViewById(R.id.umur);
        alamat = findViewById(R.id.alamat);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        timerValue = findViewById(R.id.timerValue);

        nama.setText(getIntent().getStringExtra("nama"));
        umur.setText(getIntent().getStringExtra("umur"));
        alamat.setText(getIntent().getStringExtra("alamat"));
        jenisKelamin.setText(getIntent().getStringExtra("jenisKelamin"));
        btn_6 = findViewById(R.id.btn6);

        sensorDBReff.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    nilai_1.setText(snapshot.child("HeartBeat").getValue().toString());
                    nilai_2.setText(snapshot.child("Sp02").getValue().toString());
                    nilai_3.setText(snapshot.child("Sudut").getValue().toString());
                    nilai_4.setText(snapshot.child("outflex_1").getValue().toString());
                    nilaiFingerCurve2.setText(snapshot.child("outflex_2").getValue().toString());
                    nilaiFingerCurve3.setText(snapshot.child("outflex_3").getValue().toString());
                    nilaiFingerCurve4.setText(snapshot.child("outflex_4").getValue().toString());
                    nilaiFingerCurve5.setText(snapshot.child("outflex_5").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownStart();
            }
        });

        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownStop();
            }
        });

        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    public void setTimerValue(String timerValue) {
        this.timerValue.setText(timerValue);
    }

    public void countDownStart() {
        countDownTimer = new CountDownTimer(15000 * 60, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Date date = new Date(millisUntilFinished);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
                String formattedDate = simpleDateFormat.format(date);
                setTimerValue(formattedDate);
                btn_4.setVisibility(View.GONE);
                btn_6.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                setTimerValue("00:00:00");
                btn_4.setVisibility(View.VISIBLE);
                btn_6.setVisibility(View.GONE);
            }
        }.start();

    }

    public void countDownStop() {
        countDownTimer.cancel();
        setTimerValue("00:00:00");
        btn_4.setVisibility(View.VISIBLE);
        btn_6.setVisibility(View.GONE);
//        uploadToFirestore();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void uploadData() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        String timeUploaded = timerValue.getText().toString();

        Map<String, String> data = new HashMap<>();
        data.put("HeartBeat", nilai_1.getText().toString());
        data.put("Sp02", nilai_2.getText().toString());
        data.put("Sudut", nilai_3.getText().toString());
        data.put("outflex_1", nilai_4.getText().toString());
        data.put("outflex_2", nilaiFingerCurve2.getText().toString());
        data.put("outflex_3", nilaiFingerCurve3.getText().toString());
        data.put("outflex_4", nilaiFingerCurve4.getText().toString());
        data.put("outflex_5", nilaiFingerCurve5.getText().toString());
        data.put("time", timeUploaded);
        data.put("patientId", mAuth.getUid());
        data.put("date", formattedDate);
        firestore.collection("sensors").add(data);


        Toast.makeText(this, "Data berhasil diupload", Toast.LENGTH_SHORT).show();
    }

    private void uploadToFirestore(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        ArrayList<String> listReffId = new ArrayList<>();
//        upload list to firestore
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> data = list.get(i);
            String timeUploaded = data.get("time");

            firestore.collection("sensors").add(data);
        }

        list.clear();
//        deleteDataFromRealtimeDB();
    }

    private void deleteDataFromRealtimeDB(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        databaseReference.child(formattedDate).removeValue();
    }
}