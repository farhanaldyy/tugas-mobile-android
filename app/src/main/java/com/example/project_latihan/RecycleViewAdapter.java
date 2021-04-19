package com.example.project_latihan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return arrayNim.size();
    }
}
