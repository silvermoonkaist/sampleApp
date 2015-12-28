package com.sample.user.sampleapp.chat;

/**
 * @author greg
 * @since 6/21/13
 */
public class ChatRoom {
    private String name;
    private String creator;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private ChatRoom() {
    }

    public ChatRoom(String name, String creator) {
        this.name = name;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }
}
