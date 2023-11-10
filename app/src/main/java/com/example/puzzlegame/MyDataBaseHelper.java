package com.example.puzzlegame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.puzzlegame.DataModel.HistoryDataModel;
import com.example.puzzlegame.DataModel.MyDataModel;
import com.example.puzzlegame.DataModel.ScoreDataModel;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "Puzzle";

    private static final String TABLE_NAME = "Users";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_IMAGE = "Image";

    /// Score Table.......................
    private static final String TABLE_SCORE = "Scores";
    private static final String KEY_SCORE_ID = "Id";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_MOVES = "Moves";
    private static final String KEY_TIME = "Time_Taken";


    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT)";

        db.execSQL(query);

        // For Score Table
        String CREATE_TABLE_SCORE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_SCORE_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_MOVES + " INTEGER,"
                + KEY_TIME + " INTEGER,"
                + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_NAME + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_TABLE_SCORE);
    }


    public void addNewUser(String name, String image) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_IMAGE, image);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Fre Score Table..........................
    public void addScore(int userId, int moves, int timeTaken) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_MOVES, moves);
        values.put(KEY_TIME, timeTaken);
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }


    public int updateData(int id, String name, String image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_NAME, name);
        values.put(KEY_IMAGE, image);

        return db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<MyDataModel> getAllData() {
        ArrayList<MyDataModel> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(KEY_IMAGE));

                MyDataModel data = new MyDataModel(id, name, image);
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    //For Score Table..................................
    public ArrayList<ScoreDataModel> getAllScores(int Limit) {
        ArrayList<ScoreDataModel> scoreList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_MOVES + " ASC LIMIT " + Limit, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int scoreId = cursor.getInt(cursor.getColumnIndex(KEY_SCORE_ID));
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
                @SuppressLint("Range") int move = cursor.getInt(cursor.getColumnIndex(KEY_MOVES));
                @SuppressLint("Range") int time = cursor.getInt(cursor.getColumnIndex(KEY_TIME));

                ScoreDataModel scoreData = new ScoreDataModel(scoreId, userId, move, time);
                scoreList.add(scoreData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return scoreList;
    }

 //FOR TOPSCORER............................





    //FOR HISRORY...............

    public List<HistoryDataModel> getAllHistory(int playerId) {
        List<HistoryDataModel> historyDataModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                TABLE_NAME + "." + KEY_ID + ", " +
                TABLE_NAME + "." + KEY_NAME + ", " +
                TABLE_NAME + "." + KEY_IMAGE + ", " +
                TABLE_SCORE + "." + KEY_MOVES + ", " +
                TABLE_SCORE + "." + KEY_TIME +
                " FROM " + TABLE_SCORE +
                " INNER JOIN " + TABLE_NAME +
                " ON " + TABLE_SCORE + "." + KEY_USER_ID + " = " + TABLE_NAME + "." + KEY_ID +
                " WHERE " + TABLE_SCORE + "." + KEY_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(playerId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                @SuppressLint("Range")  String userName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                @SuppressLint("Range")    String image = cursor.getString(cursor.getColumnIndex(KEY_IMAGE));
                @SuppressLint("Range")  int moves = cursor.getInt(cursor.getColumnIndex(KEY_MOVES));
                @SuppressLint("Range")  String timeTaken = cursor.getString(cursor.getColumnIndex(KEY_TIME));

                HistoryDataModel historyDataModel = new HistoryDataModel(userId, userName, image, moves, timeTaken);
                historyDataModelList.add(historyDataModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return historyDataModelList;
    }


}
