package com.example.fisioterapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisioterapi.adapters.DetailPasienListAdapter;
import com.example.fisioterapi.adapters.HospitalListAdapter;
import com.example.fisioterapi.models.HospitalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailPasien extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("sensors");
    ArrayList<String> listData = new ArrayList<>();
    DetailPasienListAdapter hospitalListAdapter;
    String userId;
    TextView tvLihatSpreadsheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pasien);
        tvLihatSpreadsheet = findViewById(R.id.tvLihatSpreadsheet);
        userId = getIntent().getStringExtra("userId");
        String spreadsheetUrl = getIntent().getStringExtra("spreadsheetUrl").isEmpty() ? null : getIntent().getStringExtra("spreadsheetUrl");
        if (spreadsheetUrl != null) {
            tvLihatSpreadsheet.setVisibility(View.VISIBLE);
            tvLihatSpreadsheet.setOnClickListener(v -> {
                intentToSpreadsheet(spreadsheetUrl);
            });
        }else {
            tvLihatSpreadsheet.setOnClickListener(v -> {
                Toast.makeText(this, "Spreadsheet tidak tersedia", Toast.LENGTH_SHORT).show();
            });
        }
//        collectionReference = ;
        recyclerView = findViewById(R.id.rv_detail_pasien);
        initAdapter();
        getData(userId);
    }


    private void intentToSpreadsheet(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData((Uri.parse(url)));
        startActivity(intent);

    }
    private void getData(String userId) {
        collectionReference.whereEqualTo("patientId", userId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            int length = queryDocumentSnapshots.size();
//            group by date
            HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
            for (int i = 0; i < length; i++) {
                String date = queryDocumentSnapshots.getDocuments().get(i).getString("date");
                if (hashMap.containsKey(date)) {
                    ArrayList<String> list = hashMap.get(date);
                    list.add(queryDocumentSnapshots.getDocuments().get(i).getString("value"));
                    hashMap.put(date, list);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(queryDocumentSnapshots.getDocuments().get(i).getString("value"));
                    hashMap.put(date, list);
                }
            }
            for (String key : hashMap.keySet()) {
                listData.add(key);
            }
            hospitalListAdapter.notifyDataSetChanged();
        });
    }


    private void initAdapter() {
        hospitalListAdapter = new DetailPasienListAdapter(DetailPasien.this, listData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(hospitalListAdapter);
        hospitalListAdapter.setOnItemClickListener(mOnItemClickListener);
    }


    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            String date = listData.get(itemPosition);
            Intent intent = new Intent(DetailPasien.this, DetailFisioterapi.class);
            intent.putExtra("patientId", userId);
            intent.putExtra("date", date);
            startActivity(intent);
        }
    };


}