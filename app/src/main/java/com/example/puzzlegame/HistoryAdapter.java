package com.example.puzzlegame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder> {
    Context context;
    ArrayList<HistoryDataModel>historyDataModels;
    MyDataBaseHelper dbHandler;



    public HistoryAdapter(Context context, ArrayList<HistoryDataModel> historyDataModels, MyDataBaseHelper dbHandler) {
        this.context = context;
        this.historyDataModels = historyDataModels;
        this.dbHandler = dbHandler; // Initialize dbHandler
    }

    @NonNull
    @Override
    public HistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_item_file, parent, false);
        return new HistoryAdapterViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterViewHolder holder, int position) {

        HistoryDataModel historyDataModel = historyDataModels.get(position);
        holder.name.setText(String.valueOf(historyDataModel.getPlayerName()));
        holder.move.setText(String.valueOf(historyDataModel.getMoves()));
        holder.time.setText(String.valueOf(historyDataModel.getTime())+" "+"Second");
        Glide.with(holder.itemView.getContext())
                .load(historyDataModel.getGetPlayerImage())
                .placeholder(R.drawable.man)
                .error(R.drawable.ic_launcher_background)
                .into(holder.photo);
        dbHandler.getAllScores();


    }

    @Override
    public int getItemCount() {
        return historyDataModels.size();
    }

    public class HistoryAdapterViewHolder extends RecyclerView.ViewHolder {

        CircleImageView photo;
        TextView name,move,time;
        public HistoryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.photo);
            name=itemView.findViewById(R.id.name);
            move=itemView.findViewById(R.id.move);
            time=itemView.findViewById(R.id.time);
        }
    }
}
