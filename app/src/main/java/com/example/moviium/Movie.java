package com.example.moviium;

import java.io.Serializable;

public class Movie implements Serializable {
    private String movieImage;
    private String movieTitle;
    private String comment;
    private int rating;

    public Movie(String movieImage, String movieTitle, String comment, int rating) {
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
        this.comment = comment;
        this.rating = rating;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
