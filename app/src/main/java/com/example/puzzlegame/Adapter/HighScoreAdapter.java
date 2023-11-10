package com.example.puzzlegame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puzzlegame.DataModel.ScoreDataModel;
import com.example.puzzlegame.MyDataBaseHelper;
import com.example.puzzlegame.R;

import java.util.ArrayList;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    Context context;

    public HighScoreAdapter(Context context, ArrayList<ScoreDataModel> scoreModelList) {
        this.context = context;
        ScoreModelList = scoreModelList;
    }

    ArrayList<ScoreDataModel> ScoreModelList;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDataBaseHelper dataBaseHelper =new MyDataBaseHelper(context);
        ScoreDataModel highScoreModel= ScoreModelList.get(position);
        holder.tx_User_id.setText(String.valueOf(highScoreModel.getUserId()));
        holder.tx_Time.setText(String.valueOf(highScoreModel.getTimeTaken()));
        holder.tx_Moves.setText(String.valueOf(highScoreModel.getMoves()));

    }

    @Override
    public int getItemCount() {
        return ScoreModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tx_User_id,tx_Time,tx_Moves;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_User_id=itemView.findViewById(R.id.tx_User_id);
            tx_Time=itemView.findViewById(R.id.tx_Time);
            tx_Moves=itemView.findViewById(R.id.tx_Moves);
        }
    }
}
