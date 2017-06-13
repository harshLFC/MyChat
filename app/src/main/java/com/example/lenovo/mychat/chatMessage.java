package com.example.lenovo.mychat;

/**
 * Created by lenovo on 13-06-2017.
 */

public class chatMessage {
    String name;
    String message;

    public chatMessage() {

    }

    public chatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
