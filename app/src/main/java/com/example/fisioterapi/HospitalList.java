package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fisioterapi.adapters.DocterListAdapter;
import com.example.fisioterapi.adapters.HospitalListAdapter;
import com.example.fisioterapi.models.HospitalModel;
import com.example.fisioterapi.models.UserModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HospitalList extends AppCompatActivity {
    RecyclerView listHospitalRv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference rumahSakitRef = db.collection("rumah_sakit");
    ArrayList<HospitalModel> listHospital = new ArrayList<>();
    HospitalListAdapter hospitalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);


        listHospitalRv = findViewById(R.id.rv_hospital);

        initAdapter();
        getDoctorList();
    }

    private void initAdapter() {
        hospitalListAdapter = new HospitalListAdapter(HospitalList.this, listHospital);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listHospitalRv.setLayoutManager(layoutManager);
        listHospitalRv.setAdapter(hospitalListAdapter);
        hospitalListAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    public void getDoctorList() {
        rumahSakitRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            int length = queryDocumentSnapshots.size();
            Log.d("getDoctorList", "getDoctorList: " + length);

            for (int i = 0; i < length; i++) {
                HospitalModel hospitalModel = new HospitalModel(
                        queryDocumentSnapshots.getDocuments().get(i).getString("name") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("name") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("address") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("address") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("phone") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("phone") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("email") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("email") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getId() != null ? queryDocumentSnapshots.getDocuments().get(i).getId() : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("longitude") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("longitude") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("latitude") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("latitude") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("gmaps_url") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("gmaps_url") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("gform_url") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("gform_url") : ""
                );
                listHospital.add(hospitalModel);
            }

            hospitalListAdapter.notifyDataSetChanged();

        });
    }

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = listHospitalRv.getChildLayoutPosition(view);
            String item = listHospital.get(itemPosition).getName();
//            Toast.makeText(HospitalList.this, item, Toast.LENGTH_LONG).show();
//
            Intent intent = new Intent(HospitalList.this, DetailRumahSakit.class);
            intent.putExtra("hospital_id", listHospital.get(itemPosition).getId());
            intent.putExtra("hospital_name", listHospital.get(itemPosition).getName());
            intent.putExtra("hospital_address", listHospital.get(itemPosition).getAddress());
            intent.putExtra("hospital_phone", listHospital.get(itemPosition).getPhone());
            intent.putExtra("hospital_email", listHospital.get(itemPosition).getEmail());
            intent.putExtra("hospital_longitude", listHospital.get(itemPosition).getLongitude());
            intent.putExtra("hospital_latitude", listHospital.get(itemPosition).getLatitude());
            intent.putExtra("hospital_gmaps_url", listHospital.get(itemPosition).getGmapsUrl());
            intent.putExtra("hospital_gform_url", listHospital.get(itemPosition).getGformUrl());


            startActivity(intent);
//            startActivity(intent);

        }
    };
}