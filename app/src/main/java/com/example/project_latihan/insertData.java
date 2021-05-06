package com.example.project_latihan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class insertData extends AppCompatActivity {

    EditText editNim, editNama, editAlamat, editHobi;
    String nim, nama, alamat, hobi;
    Button btnSubmit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        editNim = findViewById(R.id.editNim);
        editNama = findViewById(R.id.editNama);
        editAlamat = findViewById(R.id.editAlamat);
        editHobi = findViewById(R.id.editHobi);
        btnSubmit = findViewById(R.id.btnSubmit);

        progressDialog = new ProgressDialog(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Menambahkan Data.....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                nim = editNim.getText().toString();
                nama = editNama.getText().toString();
                alamat = editAlamat.getText().toString();
                hobi = editHobi.getText().toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validasiData();
                    }
                }, 1000);
            }
        });
    }

    void validasiData(){
        if (nim.equals("") || nama.equals("") || alamat.equals("") || hobi.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Periksa Kembali data yang anda masukan!", Toast.LENGTH_SHORT).show();
        } else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("http://192.168.43.136/Web-Learning/ApiConnect/insertData.php")
                .addBodyParameter("nim", nim)
                .addBodyParameter("nama", nama)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("hobi", hobi)
                .setPriority(Priority.MEDIUM)
                .setTag("Insert Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                                boolean status = response.getBoolean("status");
                                String pesan = response.getString("result");
                                Toast.makeText(insertData.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status", ""+status);
                            if(status) {
                                new AlertDialog.Builder(insertData.this)
                                        .setMessage("Berhasil Menambahkan Data!")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = getIntent();
                                                setResult(RESULT_OK,i);
                                                insertData.this.finish();
                                            }
                                        }).show();
                            } else {
                                new AlertDialog.Builder(insertData.this)
                                        .setMessage("Gagal Menambahkan Data!")
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(insertData.this, "Gagal!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}