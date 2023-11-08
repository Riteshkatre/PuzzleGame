package com.example.puzzlegame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameActivity extends AppCompatActivity {
    private final int[] tiles = new int[9];
    TextView playerName, moves, time;
    ImageView setting;
    CircleImageView photo;
    int emptyx = 2;
    MyDataModel model;
    int emptyy = 2;
    int stepCount = 0;
    GridLayout group;
    Button[][] buttons;
    Timer timer;
    int timeCount = 0;
    CardView pause;
    boolean isPaused = false;
    MyDataBaseHelper dataBaseHelper;
    Intent i;
    Bundle extras;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        i = getIntent();
        extras = i.getExtras();

        if (extras != null) {
            model = extras.getParcelable("playerModel");

            if (model != null) {
                playerName = findViewById(R.id.playerName);
                moves = findViewById(R.id.moves);

                time = findViewById(R.id.time);
                setting = findViewById(R.id.setting);
                photo = findViewById(R.id.photo);
                pause = findViewById(R.id.pause);

                String playerNameText =model.getName();
                String photoPath = model.getImage();


                dataBaseHelper = new MyDataBaseHelper(this);


                // Set the retrieved data in your TextView and ImageView
                playerName.setText(playerNameText);
                displayImage(this, photo, photoPath);

                loadViews();
                loadNumber();
                genreteNumbers();
                loadDataToView();



                mediaPlayer = MediaPlayer.create(this, R.raw.tone_1);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();

//        if (savedInstanceState != null) {
//            // Restore the saved values
//            timeCount = savedInstanceState.getInt("timeCount", 0);
//            stepCount = savedInstanceState.getInt("stepCount", 0);
//        }

                pause.setOnClickListener(v -> {
                    if (!isPaused) {
                        isPaused = true;
                        if (timer != null) {
                            timer.cancel();
                        }
                        showPauseDialog();
                        mediaPlayer.pause();
                    }

                });

                setting.setOnClickListener(v -> {
                    Intent i = new Intent(GameActivity.this, SettingsActivity.class);
                    startActivity(i);

                });


//        moves.setText(String.valueOf(stepCount));
            }
        }
    }

    private void gameWon() {
        if (dataBaseHelper != null) {
            int userId = 1;
            int moves = stepCount;
            int timeTaken = timeCount;

            dataBaseHelper.addScore(model.getId(), moves, timeTaken);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void onResumeClicked(View view) {
        if (isPaused) {
            isPaused = false;
            loadTimer();
            mediaPlayer.start();
        }
    }

    private void displayImage(Context context, ImageView imageView, String currentPhotoPath) {
        Glide.with(context)
                .load(currentPhotoPath)
                .placeholder(R.drawable.man)
                .error(R.drawable.ic_launcher_background)
                .into(photo);
    }

    private void loadDataToView() {
        for (int i = 0; i < group.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y].setText(String.valueOf(tiles[i]));
            buttons[x][y].setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light));
        }
        buttons[emptyx][emptyy].setText("");
        buttons[emptyx][emptyy].setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
    }

    private void genreteNumbers() {
        int n = 9;
        Random random = new Random();
        for (int i = 0; i < n - 1; i++) {
            tiles[i] = i + 1;
        }
        tiles[n - 1] = 0; // Set the last tile as empty
        int lastIndex = n - 1;

        for (int i = 0; i < n - 1; i++) {
            int randomIndex = random.nextInt(n - 1);
            int temp = tiles[i];
            tiles[i] = tiles[randomIndex];
            tiles[randomIndex] = temp;

            if (tiles[i] == 0) {
                emptyx = i / 3;
                emptyy = i % 3;
            }

            if (tiles[randomIndex] == 0) {
                emptyx = randomIndex / 3;
                emptyy = randomIndex % 3;
            }
        }

        // Check if the initial configuration is solvable, and if not, swap the last two elements
        if (!isSolvable()) {
            int temp = tiles[lastIndex - 1];
            tiles[lastIndex - 1] = tiles[lastIndex - 2];
            tiles[lastIndex - 2] = temp;
        }
    }

    private boolean isSolvable() {
        int countInversion = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i] && tiles[i] != 0 && tiles[j] != 0) {
                    countInversion++;
                }
            }
        }
        return countInversion % 2 == 0;
    }

    private void loadNumber() {
        for (int i = 0; i < 9; i++) {
            tiles[i] = i + 1;
        }
    }

    private void loadTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setTime(timeCount);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void setTime(int timeCount) {
        int second = timeCount % 60;
        int hour = timeCount / 3600;
        int minute = (timeCount - hour * 3600) / 60;
        time.setText(String.format("%02d:%02d:%02d", hour, minute, second));
    }

    private void loadViews() {
        group = findViewById(R.id.group);
        moves = findViewById(R.id.moves);
        time = findViewById(R.id.time);
//        loadTimer();
        buttons = new Button[3][3];
        for (int i = 0; i < group.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y] = (Button) group.getChildAt(i);
        }
    }

    public void buttonClick(View view) {
        if (buttons != null){
            Button button = (Button) view;
        String tag = button.getTag().toString();
        int x = Character.getNumericValue(tag.charAt(0)); // Extract row from the tag
        int y = Character.getNumericValue(tag.charAt(1)); // Extract column from the tag

        // Rest of your code remains the same
        if ((Math.abs(emptyx - x) == 1 && emptyy == y) || (Math.abs(emptyy - y) == 1 && emptyx == x)) {
            buttons[emptyx][emptyy].setText(button.getText().toString());
            buttons[emptyx][emptyy].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_background));
            emptyx = x;
            emptyy = y;
            stepCount++;
            moves.setText(String.valueOf(stepCount));
            if (timer == null) {
                loadTimer();
            }
        }
        }
        checkWin();
    }


    private void checkWin() {
        boolean isWin = true;
        for (int i = 0; i < group.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            String buttonText = buttons[x][y].getText().toString();

            if (!buttonText.isEmpty()) {
                int value = Integer.parseInt(buttonText);
                if (value != 0 && value != i + 1) {
                    isWin = false;
                    break;
                }
            }
        }

        if (isWin) {
            timer.cancel();
            gameWon();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Congratulations!!!");
            builder.setCancelable(false);
            builder.setMessage("You won in\n " + stepCount + " moves \n " + time.getText() + " time.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    Intent intent = new Intent(GameActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    /*@Override
    public void onBackPressed() {
        if (isPaused) {
            onResumeClicked(null); // Resume the game if paused
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit Game ??");
            builder.setCancelable(false);
            builder.setMessage("Are you sure you want to quit the game and go to the home page?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (timer != null) {
                        timer.cancel();
                    }


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
    }*/

    private void showPauseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Paused");
        builder.setMessage("The game is paused.");
        builder.setCancelable(false);
        builder.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onResumeClicked(null); // Resume the game
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //for screen roatetion
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("timeCount", timeCount);
        outState.putInt("stepCount", stepCount);
    }


}
