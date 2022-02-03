package user;

import common.YoolooKarte;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private final String name;
    private YoolooKarte[] sorting;
    private int games_played;
    private int games_won;
    private int highscore;
    private int points_total;
    private final ArrayList<GameResult> game_results = new ArrayList<>();

    public User( String name ) {
        this( name, null );
    }

    public User( String name, YoolooKarte[] sorting ) {
        this.name = name;
        this.sorting = sorting;
        this.games_played = 0;
        this.games_won = 0;
        this.highscore = 0;
        this.points_total = 0;
    }

    public String getName() {
        return this.name;
    }

    public boolean hasSorting() {
        return this.sorting != null && this.sorting.length > 0;
    }

    public YoolooKarte[] getSorting() {
        return this.sorting;
    }

    public int getGamesPlayed() {
        return this.games_played;
    }

    public int getGamesWon() {
        return this.games_won;
    }

    public int getHighscore() {
        return this.highscore;
    }

    public int getPointsTotal() {
        return this.points_total;
    }

    public void setSorting( YoolooKarte[] sorting ) {
        this.sorting = sorting;
    }

    public void setHighscore( int max ) {
        this.highscore = max;
    }

    public void setGamesPlayed( int games_played ) {
        this.games_played = games_played;
    }

    public void incrGamesPlayed( int increment ) {
        this.games_played += increment;
    }

    public void setGamesWon( int games_won ) {
        this.games_won = games_won;
    }

    public void incrGamesWon( int increment ) {
        this.games_won += increment;
    }

    public void setPointsTotal( int total ) {
        this.points_total = total;
    }

    public void incrPointsTotal( int increment ) {
        this.points_total += increment;
    }

    public void addGameResult( GameResult result ) {
        this.game_results.add( result );
    }

    public ArrayList<GameResult> getGameResults() {
        return this.game_results;
    }

}
