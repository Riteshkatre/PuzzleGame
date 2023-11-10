package com.example.puzzlegame.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.puzzlegame.Adapter.HistoryAdapter;
import com.example.puzzlegame.DataModel.HistoryDataModel;
import com.example.puzzlegame.DataModel.MyDataModel;
import com.example.puzzlegame.MyDataBaseHelper;
import com.example.puzzlegame.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
//    SwipeRefreshLayout swipeRefresh;
    RecyclerView rcv;

    HistoryAdapter historyAdapter;
    Bundle extras;
    MyDataModel model;
    MyDataBaseHelper myDataBaseHelper;
    ArrayList<HistoryDataModel> historyDataModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        extras = getIntent().getExtras();

        if (extras != null) {

            model = extras.getParcelable("historyModel");

            if (model != null) {
//                swipeRefresh = findViewById(R.id.swipeRefresh);
                rcv = findViewById(R.id.rcv);
                rcv.setLayoutManager(new LinearLayoutManager(this));

                myDataBaseHelper = new MyDataBaseHelper(this);
                historyDataModels = new ArrayList<>();

                historyDataModels = (ArrayList<HistoryDataModel>) myDataBaseHelper.getAllHistory(model.getId());


                historyAdapter = new HistoryAdapter(this, historyDataModels, myDataBaseHelper);
                rcv.setAdapter(historyAdapter);
            }

        }


    }
}
