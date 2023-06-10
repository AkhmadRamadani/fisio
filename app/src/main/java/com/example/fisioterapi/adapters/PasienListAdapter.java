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

public class PasienListAdapter extends RecyclerView.Adapter<PasienListAdapter.ViewHolder> {
    ArrayList<UserModel> listDoctor;
    private LayoutInflater mInflater;

    private static View.OnClickListener mOnItemClickListener;
    private static View.OnClickListener setLihatSpreadsheetClickListener;

    public PasienListAdapter(Context context, ArrayList<UserModel> listDoctor) {
        this.listDoctor = listDoctor;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public PasienListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pasien_list_card, parent, false);
        return new PasienListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PasienListAdapter.ViewHolder holder, int position) {
        holder.name.setText(listDoctor.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listDoctor.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public void setLihatSpreadsheetClickListener(View.OnClickListener itemClickListener) {
        setLihatSpreadsheetClickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, tvLihatSpreadsheet;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
