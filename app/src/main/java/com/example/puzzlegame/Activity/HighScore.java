package com.example.puzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.puzzlegame.Adapter.HighScoreAdapter;
import com.example.puzzlegame.DataModel.ScoreDataModel;
import com.example.puzzlegame.MyDataBaseHelper;
import com.example.puzzlegame.R;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {
    private RecyclerView rcv;
    private HighScoreAdapter highscoreAdapter;
    private MyDataBaseHelper databaseHandler;

    ArrayList<ScoreDataModel> scoreModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        rcv=findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        databaseHandler = new MyDataBaseHelper(this);

        databaseHandler=new MyDataBaseHelper(HighScore.this);
        scoreModel = databaseHandler.getAllScores(10);
        highscoreAdapter = new HighScoreAdapter(HighScore.this,scoreModel);

        rcv.setAdapter(highscoreAdapter);
    }
}