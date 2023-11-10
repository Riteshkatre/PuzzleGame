package com.example.puzzlegame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.puzzlegame.DataModel.MyDataModel;
import com.example.puzzlegame.MyDataBaseHelper;
import com.example.puzzlegame.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {

    private final ArrayList<MyDataModel> dataList;
    Context context;
    MyDataBaseHelper myDataBaseHelper;
    PlayerItemInterface playerItemInterface;

    public HomeAdapter(Context context, ArrayList<MyDataModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setPlayerItemInterface(PlayerItemInterface playerItemInterface) {
        this.playerItemInterface = playerItemInterface;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_item_file, parent, false);
        return new HomeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        myDataBaseHelper = new MyDataBaseHelper(holder.itemView.getContext());
        MyDataModel myDataModel = dataList.get(position);
        holder.playerName.setText(myDataModel.getName());
//        holder.highScore.setText(myDataModel.g());
        Glide.with(holder.itemView.getContext())
                .load(myDataModel.getImage())
                .placeholder(R.drawable.man)
                .error(R.drawable.ic_launcher_background)
                .into(holder.photo);

        holder.Score.setText(String.valueOf(myDataModel.getId()));


        holder.play.setOnClickListener(v -> playerItemInterface.onPlayClicked(myDataModel));
        holder.view.setOnClickListener(v -> {
            playerItemInterface.onViewClicked(myDataModel);
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(ArrayList<MyDataModel> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }

    public interface PlayerItemInterface {
        void onPlayClicked(MyDataModel model);

        void onViewClicked(MyDataModel model);
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView playerName, Score;
        CircleImageView photo;


        Button play, view;

        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            photo = itemView.findViewById(R.id.photo);
            play = itemView.findViewById(R.id.play);
            view = itemView.findViewById(R.id.view);
            Score = itemView.findViewById(R.id.Score);
        }
    }
}
