package com.example.moviium.Models;

import com.google.firebase.Timestamp;
import com.google.type.DateTime;

public class Comment {
    private String id;
    private String comment;
    private String dateTime;
    private String userEmail;

    public Comment(String comment, String dateTime, String userEmail) {
        this.comment = comment;
        this.dateTime = dateTime;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
