package com.example.fisioterapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fisioterapi.R;
import com.example.fisioterapi.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DetailPasienListAdapter extends RecyclerView.Adapter<DetailPasienListAdapter.ViewHolder> {
    ArrayList<String> listDoctor;
    private LayoutInflater mInflater;

    private static View.OnClickListener mOnItemClickListener;
    public DetailPasienListAdapter(Context context, ArrayList<String> listDoctor) {
        this.listDoctor = listDoctor;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public DetailPasienListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_pasien_card, parent, false);
        return new DetailPasienListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailPasienListAdapter.ViewHolder holder, int position) {
        holder.name.setText(listDoctor.get(position));

    }

    @Override
    public int getItemCount() {
        return listDoctor.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
