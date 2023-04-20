package com.example.moviium.Models;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    private String id;
    private String movieImage;
    private String movieTitle;
    private String comment;
    private Float myRating, dbRating;
    private String director;

    public float getMyRating() {
        return myRating;
    }

    public void setMyRating(float myRating) {
        this.myRating = myRating;
    }

    public float getDbRating() {
        return dbRating;
    }

    public void setDbRating(float dbRating) {
        this.dbRating = dbRating;
    }

    public String getStoryLine() {
        return storyLine;
    }

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    private String storyLine;
    private List<String> actors;
    private List<String> genres;

    public Movie(){}

    public Movie (String movieImage, String movieTitle, String id, String director) {
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
        this.id = id;
        this.director = director;
    }
    public Movie(String movieImage, String movieTitle, float rating) {
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
        this.comment = comment;
        //this.rating = rating;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
