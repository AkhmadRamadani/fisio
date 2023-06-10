package com.example.fisioterapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fisioterapi.R;
import com.example.fisioterapi.models.HospitalModel;

import java.util.ArrayList;
import java.util.List;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.ViewHolder> {
    ArrayList<HospitalModel> listDoctor;
    private LayoutInflater mInflater;

    private static View.OnClickListener mOnItemClickListener;
    public HospitalListAdapter(Context context, ArrayList<HospitalModel> listDoctor) {
        this.listDoctor = listDoctor;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public HospitalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.hospital_list_card, parent, false);
        return new HospitalListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HospitalListAdapter.ViewHolder holder, int position) {
        holder.name.setText(listDoctor.get(position).getName());
        holder.address.setText(listDoctor.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return listDoctor.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
