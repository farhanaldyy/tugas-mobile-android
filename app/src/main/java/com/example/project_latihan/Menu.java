package com.example.project_latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void menu1(View view) {
        Intent menu1 = new Intent(this, MainActivity.class);
        startActivity(menu1);
    }

    public void menu2(View view) {
        Intent menu2 = new Intent(this, Struktur.class);
        startActivity(menu2);
    }

    public void menu3(View view) {
        Intent menu3 = new Intent(this, Uts.class);
        startActivity(menu3);
    }

    public void menu4(View view) {
        Intent menu4 = new Intent(this, insertData.class);
        startActivity(menu4);
    }

    public void menu5(View view) {
        Intent menu5 = new Intent(this, readData.class);
        startActivity(menu5);
    }
}