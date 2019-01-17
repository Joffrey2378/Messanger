package com.example.messages;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class MessageModel {
    private String image;
    private String name;
    private String message;
    private String uid;
    private Date timestamp;

    public MessageModel() {
    }

    public MessageModel(String image, String name, String message, String uid) {
        this.image = image;
        this.name = name;
        this.message = message;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
