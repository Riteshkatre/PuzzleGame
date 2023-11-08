package com.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefresh;
    RecyclerView rcv;

    HistoryAdapter historyAdapter;
    MyDataBaseHelper myDataBaseHelper;
    ArrayList<HistoryDataModel> historyDataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        rcv = findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        myDataBaseHelper = new MyDataBaseHelper(this);

        int playerId = 2;

        historyDataModels = (ArrayList<HistoryDataModel>) myDataBaseHelper.getAllHistory(playerId);

        historyAdapter = new HistoryAdapter(this, historyDataModels,myDataBaseHelper);
        rcv.setAdapter(historyAdapter);
    }
}
