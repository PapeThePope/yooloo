package user;

import common.YoolooKarte;

import java.io.Serializable;
import java.util.ArrayList;

public class GameResult implements Serializable {

    private final YoolooKarte[] sorting;
    private final ArrayList<Boolean> stitches_results;

    public GameResult( YoolooKarte[] sorting, ArrayList<Boolean> stitches_results ) {
        this.sorting = sorting;
        this.stitches_results = stitches_results;
    }

    public YoolooKarte[] getSorting() {
        return this.sorting;
    }

    public ArrayList<Boolean> getStitchesResults() {
        return stitches_results;
    }

}
