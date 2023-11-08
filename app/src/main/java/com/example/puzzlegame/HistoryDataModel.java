package com.example.puzzlegame;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class HistoryDataModel implements Parcelable, Serializable {

    int playerId;
    String playerName;

    String getPlayerImage;
    int moves;
    String time;

    protected HistoryDataModel(Parcel in) {
        playerId = in.readInt();
        playerName = in.readString();
        getPlayerImage = in.readString();
        moves = in.readInt();
        time = in.readString();
    }

    public static final Creator<HistoryDataModel> CREATOR = new Creator<HistoryDataModel>() {
        @Override
        public HistoryDataModel createFromParcel(Parcel in) {
            return new HistoryDataModel(in);
        }

        @Override
        public HistoryDataModel[] newArray(int size) {
            return new HistoryDataModel[size];
        }
    };

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGetPlayerImage() {
        return getPlayerImage;
    }

    public void setGetPlayerImage(String getPlayerImage) {
        this.getPlayerImage = getPlayerImage;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public HistoryDataModel(int playerId, String playerName, String getPlayerImage, int moves, String time) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.getPlayerImage = getPlayerImage;
        this.moves = moves;
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(playerId);
        dest.writeString(playerName);
        dest.writeString(getPlayerImage);
        dest.writeInt(moves);
        dest.writeString(time);
    }
}
