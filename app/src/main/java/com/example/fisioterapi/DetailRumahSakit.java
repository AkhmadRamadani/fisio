package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.fisioterapi.models.HospitalModel;

public class DetailRumahSakit extends AppCompatActivity {
    HospitalModel hospitalModel;
    ImageView imgViewMap, btnReg, btncall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rumah_sakit);

        hospitalModel = new HospitalModel(
                getIntent().getStringExtra("hospital_name"),
                getIntent().getStringExtra("hospital_address"),
                getIntent().getStringExtra("hospital_phone"),
                getIntent().getStringExtra("hospital_email"),
                getIntent().getStringExtra("hospital_id"),
                getIntent().getStringExtra("hospital_longitude"),
                getIntent().getStringExtra("hospital_latitude"),
                getIntent().getStringExtra("hospital_gmaps_url"),
                getIntent().getStringExtra("hospital_gform_url")
        );

        imgViewMap = findViewById(R.id.imageViewMap);
        btnReg = findViewById(R.id.btnreg);
        btncall = findViewById(R.id.btncall);

        btncall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(android.net.Uri.parse("tel:" + hospitalModel.getPhone()));
            startActivity(intent);
        });

        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData((Uri.parse(hospitalModel.getGformUrl())));
            startActivity(intent);
        });

        imgViewMap.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData((Uri.parse(hospitalModel.getGmapsUrl())));
            startActivity(intent);
        });
    }
}