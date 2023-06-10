package com.example.fisioterapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fisioterapi.R;
import com.example.fisioterapi.models.SensorDataModel;
import com.example.fisioterapi.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DataFisioterapiAdapter extends RecyclerView.Adapter<DataFisioterapiAdapter.ViewHolder> {
    ArrayList<SensorDataModel> listDoctor;
    private LayoutInflater mInflater;

    private static View.OnClickListener mOnItemClickListener;
    public DataFisioterapiAdapter(Context context, ArrayList<SensorDataModel> listDoctor) {
        this.listDoctor = listDoctor;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public DataFisioterapiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.data_fisioterapi_card, parent, false);
        return new DataFisioterapiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataFisioterapiAdapter.ViewHolder holder, int position) {
        holder.time.setText(listDoctor.get(position).getTime());
        holder.date.setText(listDoctor.get(position).getDate());
        holder.heartBeat.setText(listDoctor.get(position).getHeartBeat());
        holder.sp02.setText(listDoctor.get(position).getSp02());
        holder.sudut.setText(listDoctor.get(position).getSudut());
        holder.outflex1.setText(listDoctor.get(position).getOutflex1());
        holder.outflex2.setText(listDoctor.get(position).getOutflex2());
        holder.outflex3.setText(listDoctor.get(position).getOutflex3());
        holder.outflex4.setText(listDoctor.get(position).getOutflex4());
        holder.outflex5.setText(listDoctor.get(position).getOutflex5());


    }

    @Override
    public int getItemCount() {
        return listDoctor.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, date, heartBeat, sp02, sudut,
                outflex1, outflex2, outflex3, outflex4, outflex5;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tvTime);
            date = itemView.findViewById(R.id.tvDate);
            heartBeat = itemView.findViewById(R.id.tvHeartbeat);
            sp02 = itemView.findViewById(R.id.tvSp02);
            sudut = itemView.findViewById(R.id.tvSudut);
            outflex1 = itemView.findViewById(R.id.tvOutflex1);
            outflex2 = itemView.findViewById(R.id.tvOutflex2);
            outflex3 = itemView.findViewById(R.id.tvOutflex3);
            outflex4 = itemView.findViewById(R.id.tvOutflex4);
            outflex5 = itemView.findViewById(R.id.tvOutflex5);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
