package com.example.project_latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.number.IntegerWidth;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Uts extends AppCompatActivity {

    EditText inAbsen, inPartik, inUts, inUas;
    EditText prosesAbsen;
    TextView viewAngka, viewHuruf, viewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uts);
        // id edittext
        inAbsen = findViewById(R.id.inAbsen);
        inPartik = findViewById(R.id.inPraktik);
        inUts = findViewById(R.id.inUts);
        inUas = findViewById(R.id.inUas);

        // id textview
        viewAngka = findViewById(R.id.viewAngka);
        viewHuruf = findViewById(R.id.viewHuruf);
        viewStatus = findViewById(R.id.viewStatus);
    }

    public void cekStatus(View view) {
        // variabel nilai input ke integer
        int absen = Integer.parseInt(inAbsen.getText().toString());
        int praktik = Integer.parseInt(inPartik.getText().toString());
        int uts = Integer.parseInt(inUts.getText().toString());
        int uas = Integer.parseInt(inUas.getText().toString());
        int hasil;
        // mengkalikan nilai variabel sesuai persen
        absen = (int)(absen * 0.15);
        praktik = (int)(praktik * 0.25);
        uts = (int)(uts * 0.25);
        uas = (int)(uas * 0.35);
        // minghitung hasil
        hasil = (int)(absen + praktik + uts + uas);

        if(hasil > 85){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("A");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 80){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("A-");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 75){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("B+");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 70){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("B");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 65){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("B-");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 60){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("C+");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 55){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("C");
            viewStatus.setText("LULUS");
            viewStatus.setBackgroundResource(R.color.green);
        } else if(hasil > 40){
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("D");
            viewStatus.setText("PERBAIKAN");
            viewStatus.setBackgroundResource(R.color.yelow);
        } else {
            viewAngka.setText(Integer.toString(hasil));
            viewHuruf.setText("E");
            viewStatus.setText("MENGULANG");
            viewStatus.setBackgroundResource(R.color.red);
        }
    }
}