package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Edukasi extends AppCompatActivity {

    ImageView edu_1,edu_2, edu_3, edu_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);

        edu_1=findViewById(R.id.edu1);
        edu_2=findViewById(R.id.edu2);
        edu_3=findViewById(R.id.edu3);
        edu_4=findViewById(R.id.edu4);

        edu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl="https://sardjito.co.id/2021/12/31/mengenal-deteksi-dini-gejala-stroke/";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
        edu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl="https://p2ptm.kemkes.go.id/infographic-p2ptm/stroke/pencegahan-stroke-primer";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
        edu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl="https://infografis.okezone.com/detail/776626/4-cara-turunkan-risiko-terkena-stroke";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
        edu_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl="https://flexfreeclinic.com/artikel/detail/549?title=latihan-fisioterapi-pasca-stroke";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
    }
}