package com.sample.user.sampleapp.chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author greg
 * @since 6/21/13
 */
public class ChatRoom {
    private String id;
    private String name;
    private String creator;
    private String date;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private ChatRoom() {
    }

    public ChatRoom(String id, String name, String creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.date = dateFormat.format(date);  //2014/08/06 15:59:48
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public String getDate() {
        return date;
    }
}
