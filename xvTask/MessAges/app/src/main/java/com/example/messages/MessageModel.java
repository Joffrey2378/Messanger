package com.example.messages;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class MessageModel {
    private String image;
    private String name;
    private String message;
    private Date timestamp;
    private String location;

    public MessageModel() {
    }

    public MessageModel(String image, String name, String message, String location) {
        this.image = image;
        this.name = name;
        this.message = message;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
