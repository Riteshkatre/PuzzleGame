package com.example.puzzlegame.DataModel;

public class ScoreDataModel {
    private int scoreId;
    private int userId;
    private int moves;
    private int timeTaken;

    public ScoreDataModel(int scoreId, int userId, int moves, int timeTaken) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.moves = moves;
        this.timeTaken = timeTaken;
    }

    public int getScoreId() {
        return scoreId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoves() {
        return moves;
    }

    public int getTimeTaken() {
        return timeTaken;
    }
}
