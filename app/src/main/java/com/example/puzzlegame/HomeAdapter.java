package com.example.puzzlegame;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {

    private ArrayList<MyDataModel> dataList;
    MyDataBaseHelper myDataBaseHelper;

    public HomeAdapter(ArrayList<MyDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_item_file ,parent,false);
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), GameActivity.class);
                i.putExtra("name",myDataModel.getName());
                i.putExtra("image",myDataModel.getImage());
                v.getContext().startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView playerName;
        CircleImageView photo;
        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.playerName);
            photo=itemView.findViewById(R.id.photo);
        }
    }

    public void setData(ArrayList<MyDataModel> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }
}
