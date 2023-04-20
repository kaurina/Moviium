package com.example.moviium.Models;

public class PlanToWatch {
    private String movieId;
    private String movieTitle;
    private String id;


    public PlanToWatch(String id, String movieId) {
        this.movieId = movieId;
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
