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

public class UpdateData extends AppCompatActivity {

    EditText edit_nim, edit_nama, edit_alamat, edit_hobi;
    String nim, nama, alamat, hobi;
    Button btn_submit;
    ProgressDialog progressDialog;
    private Object Bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        edit_nim = findViewById(R.id.edit_nim);
        edit_nama = findViewById(R.id.edit_nama);
        edit_alamat = findViewById(R.id.edit_alamat);
        edit_hobi = findViewById(R.id.edit_hobi);
        btn_submit = findViewById(R.id.btn_submit);

        progressDialog = new ProgressDialog(this);

       getDataIntent();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Menambahkan data....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                nim = edit_nim.getText().toString();
                nama = edit_nama.getText().toString();
                alamat = edit_alamat.getText().toString();
                hobi = edit_hobi.getText().toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       validasiData();
                    }
                }, 1000);
            }
        });
    }

    void getDataIntent(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            edit_nim.setText(bundle.getString("nim"));
            edit_nama.setText(bundle.getString("nama"));
            edit_alamat.setText(bundle.getString("alamat"));
            edit_hobi.setText(bundle.getString("hobi"));
        } else {
            edit_nim.setText("");
            edit_nama.setText("");
            edit_alamat.setText("");
            edit_hobi.setText("");
        }
    }

    void validasiData() {
        if(nim.equals("") || nama.equals("") || alamat.equals("") || hobi.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(UpdateData.this, "Periksa Kembali data yang anda masukan!", Toast.LENGTH_SHORT).show();
        } else {
            editData();
        }
    }

    void editData() {
        AndroidNetworking.post("http://192.168.43.137/Web-Learning/ApiConnect/updateData.php")
                .addBodyParameter("nim",""+nim)
                .addBodyParameter("nama", ""+nama)
                .addBodyParameter("alamat", ""+alamat)
                .addBodyParameter("hobi", ""+hobi)
                .setTag("Update Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("responEdit",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            if (status) {
                                new AlertDialog.Builder(UpdateData.this)
                                        .setMessage("Berhasil Mengupdate Data")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = getIntent();
                                                setResult(RESULT_OK,i);
                                                UpdateData.this.finish();
                                            }
                                        })
                                        .show();
                            } else {
                                new AlertDialog.Builder(UpdateData.this)
                                        .setMessage("Gagal Mengupdate Data!")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}