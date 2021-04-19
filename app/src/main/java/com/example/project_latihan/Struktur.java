package com.example.project_latihan;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Struktur extends AppCompatActivity {

    EditText nilai;
    TextView view_hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struktur);

        nilai = findViewById(R.id.nilai);
        view_hasil = findViewById(R.id.view_hasil);
    }

    public void proses(View view) {
        int num = Integer.parseInt(nilai.getText().toString());
        if (num >= 60) {
            view_hasil.setText("LULUS");
            Toast.makeText(this, "Selamat Anda Lulus!", Toast.LENGTH_LONG).show();
            view_hasil.setBackgroundResource(R.color.green);
        } else {
            view_hasil.setText("TIDAK LULUS");
            Toast.makeText(this, "Maaf Anda Tidak Lulus", Toast.LENGTH_LONG).show();
            view_hasil.setBackgroundResource(R.color.red);
        }
    }
}