package com.example.fisioterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Service extends AppCompatActivity {
    ImageView button_call, btn_reg, rs_1, rs_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        button_call = findViewById(R.id.btncall);
        btn_reg = findViewById(R.id.btnreg);
        rs_1 = findViewById(R.id.rs1);
        rs_2 = findViewById(R.id.rs2);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl = "https://forms.gle/aw5RZhrco7TbBkEu9";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(wpurl)));
                startActivity(intent);
            }
        });
        rs_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl = "https://www.google.co.id/maps/place/FISIOTERAPI+BEDAH+DEMPO+(Ftr.+Wiek+Israwan,+Sst.Ft./@-7.9526466,112.581562,13z/data=!4m10!1m2!2m1!1sklinik+fisipterapi+dempo!3m6!1s0x2dd629df1e659b3b:0x66b44a7e2bbb075d!8m2!3d-7.9695596!4d112.6211056!15sChhrbGluaWsgZmlzaXB0ZXJhcGkgZGVtcG-SAQZkb2N0b3LgAQA!16s%2Fg%2F11h1dfnybb";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
        rs_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl = "https://goo.gl/maps/ikPmwBkuEkyhDZdB7";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);

            }
        });
    }

    public void panggil(View view) {
        Uri nomor = Uri.parse("tel:119");
        Intent panggil = new Intent(Intent.ACTION_DIAL, nomor);
        startActivity(panggil);
    }
}