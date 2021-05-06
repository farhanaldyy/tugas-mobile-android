package com.example.project_latihan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.model.Progress;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> arrayNim, arrayNama, arrayAlamat, arrayHobi;
    ProgressDialog progressDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNim, tvNama, tvAlamat, tvHobi;
        public CardView cvMain;

        public MyViewHolder(View view){
            super(view);

            cvMain = itemView.findViewById(R.id.cvMain);
            tvNim = itemView.findViewById(R.id.tvNim);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvHobi = itemView.findViewById(R.id.tvHobi);

            progressDialog = new ProgressDialog(mContext);
        }
    }

    public RecycleViewAdapter(Context mContext,
                              ArrayList<String> arrayNim,
                              ArrayList<String> arrayNama,
                              ArrayList<String> arrayAlamat,
                              ArrayList<String> arrayHobi) {
        super();
        this.mContext = mContext;
        this.arrayNim = arrayNim;
        this.arrayNama = arrayNama;
        this.arrayAlamat = arrayAlamat;
        this.arrayHobi = arrayHobi;
    }


    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.template_rv,parent,false);
        return new RecycleViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.tvNim.setText(arrayNim.get(position));
        holder.tvNama.setText(arrayNama.get(position));
        holder.tvAlamat.setText(arrayAlamat.get(position));
        holder.tvHobi.setText(arrayHobi.get(position));

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,UpdateData.class);
                i.putExtra("nim",arrayNim.get(position));
                i.putExtra("nama",arrayNama.get(position));
                i.putExtra("alamat",arrayAlamat.get(position));
                i.putExtra("hobi",arrayHobi.get(position));
                ((readData)mContext).startActivityForResult(i,1);
            }
        });

        holder.cvMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((readData)mContext)
                        .setMessage("Ingin menghapus nim : "+arrayNim.get(position)+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.setMessage("Menghapus...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                AndroidNetworking.post("http://192.168.43.136/Web-learning/ApiConnect/deleteData.php")
                                        .addBodyParameter("nim",""+arrayNim.get(position))
                                        .setPriority(Priority.MEDIUM)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                progressDialog.dismiss();
                                                try {
                                                    Boolean status = response.getBoolean("status");
                                                    Log.d("status",""+status);
                                                    String result = response.getString("result");
                                                    if(status){
                                                        if(mContext instanceof readData){
                                                            ((readData)mContext).scrollRefresh();
                                                        }
                                                    }else{
                                                        Toast.makeText(mContext, ""+result, Toast.LENGTH_SHORT).show();
                                                    }
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                            @Override
                                            public void onError(ANError anError) {
                                                anError.printStackTrace();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }) .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayNim.size();
    }
}
