package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fisioterapi.adapters.DataFisioterapiAdapter;
import com.example.fisioterapi.models.SensorDataModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DetailFisioterapi extends AppCompatActivity {
    DataFisioterapiAdapter dataFisioterapiAdapter;
    RecyclerView recyclerView;

    ArrayList<SensorDataModel> listData = new ArrayList<>();

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("sensors");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fisioterapi);

        recyclerView = findViewById(R.id.rv_detail_pasien);
        String date = getIntent().getStringExtra("date");
        String patientId = getIntent().getStringExtra("patientId");
        initAdapter();
        getData(date, patientId);
    }

    private void initAdapter() {
        dataFisioterapiAdapter = new DataFisioterapiAdapter(this, listData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dataFisioterapiAdapter);
    }

    private void getData(String date, String patientId
    ) {
        collectionReference.whereEqualTo("date", date).whereEqualTo("patientId", patientId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            int length = queryDocumentSnapshots.size();
            for (int i = 0; i < length; i++) {
                String id = queryDocumentSnapshots.getDocuments().get(i).getId();
                String HeartBeat = queryDocumentSnapshots.getDocuments().get(i).getString("HeartBeat");
                String Sp02 = queryDocumentSnapshots.getDocuments().get(i).getString("Sp02");
                String Sudut = queryDocumentSnapshots.getDocuments().get(i).getString("Sudut");
                String date1 = queryDocumentSnapshots.getDocuments().get(i).getString("date");
                String patientId1 = queryDocumentSnapshots.getDocuments().get(i).getString("patientId");
                String outflex_1 = queryDocumentSnapshots.getDocuments().get(i).getString("outflex_1");
                String outflex_2 = queryDocumentSnapshots.getDocuments().get(i).getString("outflex_2");
                String outflex_3 = queryDocumentSnapshots.getDocuments().get(i).getString("outflex_3");
                String outflex_4 = queryDocumentSnapshots.getDocuments().get(i).getString("outflex_4");
                String outflex_5 = queryDocumentSnapshots.getDocuments().get(i).getString("outflex_5");
                String time = queryDocumentSnapshots.getDocuments().get(i).getString("time");
                SensorDataModel sensorDataModel = new SensorDataModel(
                        id,
                        HeartBeat,
                        Sp02,
                        Sudut,
                        time,
                        date1,
                        patientId1,
                        outflex_1,
                        outflex_2,
                        outflex_3,
                        outflex_4,
                        outflex_5
                );
                listData.add(sensorDataModel);
            }
            dataFisioterapiAdapter.notifyDataSetChanged();
        });
    }
}