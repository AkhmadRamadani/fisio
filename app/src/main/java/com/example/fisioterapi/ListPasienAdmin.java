package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fisioterapi.adapters.DocterListAdapter;
import com.example.fisioterapi.adapters.PasienListAdapter;
import com.example.fisioterapi.models.UserModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListPasienAdmin extends AppCompatActivity {
    RecyclerView listDoctorRv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef = db.collection("users");
    ArrayList<UserModel> listDoctor = new ArrayList<>();
    PasienListAdapter docterListAdapter;
    TextView tv_list_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pasien_admin);

        listDoctorRv = findViewById(R.id.rv_doctor);

        initAdapter();
        getDoctorList();

    }


    private void initAdapter() {
        docterListAdapter = new PasienListAdapter(ListPasienAdmin.this, listDoctor);
        docterListAdapter.setOnItemClickListener(mOnItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listDoctorRv.setLayoutManager(layoutManager);
        listDoctorRv.setAdapter(docterListAdapter);
    }


    public void getDoctorList() {
        userRef.whereEqualTo("role", "patient").get().addOnSuccessListener(queryDocumentSnapshots -> {
            int length = queryDocumentSnapshots.size();
            Log.d("getDoctorList", "getDoctorList: " + length);

            for (int i = 0; i < length; i++) {
                UserModel userModel = new UserModel(
                        queryDocumentSnapshots.getDocuments().get(i).getId() != null ? queryDocumentSnapshots.getDocuments().get(i).getId() : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("password") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("password") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("umur") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("umur") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("role") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("role") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("gender") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("gender") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("phone") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("phone") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("name") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("name") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("email") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("email") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("address") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("address") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("hospital_id") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("hospital_id") : "",
                        queryDocumentSnapshots.getDocuments().get(i).getString("spreadsheetUrl") != null ? queryDocumentSnapshots.getDocuments().get(i).getString("spreadsheetUrl") : ""

                );
                listDoctor.add(userModel);
            }
            docterListAdapter.notifyDataSetChanged();
        });
    }

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = listDoctorRv.getChildLayoutPosition(view);
            Intent intent = new Intent(ListPasienAdmin.this, DetailPasien.class);
            intent.putExtra("userId", listDoctor.get(itemPosition).getUid());
            intent.putExtra("spreadsheetUrl", listDoctor.get(itemPosition).getSpreadSheetUrl());
//            intent.putExtra("id", listDoctor.get(itemPosition).getId());
            startActivity(intent);

        }
    };

}