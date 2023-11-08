package com.example.puzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton btnadd;

    RecyclerView rcv;

    CardView cv;


    HomeAdapter adapter;
    MyDataBaseHelper dbHandler;

    SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnadd = findViewById(R.id.btnAdd);
        rcv = findViewById(R.id.rcv);
        cv = findViewById(R.id.cv);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        dbHandler = new MyDataBaseHelper(this);

        ArrayList<MyDataModel> dataList = dbHandler.getAllData();


        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(HomeActivity.this, dataList);
        adapter.setPlayerItemInterface(model -> {
            Intent i = new Intent(HomeActivity.this, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("playerModel", model);
            i.putExtras(bundle);
            startActivity(i);
        });
        rcv.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(() -> refreshData());


        btnadd.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
        });

        cv.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "high score", Toast.LENGTH_SHORT).show());
    }

    private void refreshData() {

        ArrayList<MyDataModel> updatedDataList = dbHandler.getAllData();
        adapter.setData(updatedDataList);
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

   /* @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
*/
}