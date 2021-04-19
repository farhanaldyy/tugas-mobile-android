package com.example.project_latihan;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RequiresPermission.Read;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.model.Progress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class readData extends AppCompatActivity {

    SwipeRefreshLayout srlMain;
    RecyclerView rvMain;
    ArrayList<String> arrayNim, arrayNama, arrayAlamat, arrayHobi;
    ProgressDialog progressDialog;

    RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        srlMain = findViewById(R.id.srlMain);
        rvMain = findViewById(R.id.rvMain);
        progressDialog = new ProgressDialog(this);

        rvMain.hasFixedSize();
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMain.setLayoutManager(layoutManager);
        srlMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srlMain.setRefreshing(false);
            }
        });
        scrollRefresh();
    }

    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Data....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1200);
    }

    void initializeArray(){
        arrayNim = new ArrayList<>();
        arrayNama = new ArrayList<>();
        arrayAlamat = new ArrayList<>();
        arrayHobi = new ArrayList<>();

        arrayNim.clear();
        arrayNama.clear();
        arrayAlamat.clear();
        arrayHobi.clear();
    }

    public void getData(){
        initializeArray();
        AndroidNetworking.get("http://192.168.43.137/Web-learning/ApiConnect/getData.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try {
                            Boolean status = response.getBoolean("status");
                            if (status) {
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);

                                    arrayNim.add(jo.getString("nim"));
                                    arrayNama.add(jo.getString("nama"));
                                    arrayAlamat.add(jo.getString("alamat"));
                                    arrayHobi.add(jo.getString("hobi"));
                                }
                                recycleViewAdapter = new RecycleViewAdapter(readData.this, arrayNim, arrayNama, arrayAlamat, arrayHobi);
                                rvMain.setAdapter(recycleViewAdapter);
                            } else {
                                Toast.makeText(readData.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                                recycleViewAdapter = new RecycleViewAdapter(readData.this, arrayNim, arrayNama, arrayAlamat, arrayHobi);
                                rvMain.setAdapter(recycleViewAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            if (resultCode==RESULT_OK) {
                scrollRefresh();
            } else if (resultCode==RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}