package user;

import common.YoolooKarte;

import java.io.Serializable;

public class User implements Serializable {

    private final String name;
    private YoolooKarte[] sorting;
    private int points_max;
    private int points_total;

    public User( String name ) {
        this.name = name;
        this.sorting = null;
        this.points_max = 0;
        this.points_total = 0;
    }

    public User( String name, YoolooKarte[] sorting ) {
        this.name = name;
        this.sorting = sorting;
        this.points_max = 0;
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

    public int getPointsMax() {
        return this.points_max;
    }

    public int getPointsTotal() {
        return this.points_total;
    }

    public void setSorting( YoolooKarte[] sorting ) {
        this.sorting = sorting;
    }

    public void setPointsMax( int max ) {
        this.points_max = max;
    }

    public void incrPointsMax( int increment ) {
        this.points_max += increment;
    }

    public void setPointsTotal( int total ) {
        this.points_total = total;
    }

    public void incrPointsTotal( int increment ) {
        this.points_total += increment;
    }

}
