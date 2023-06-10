package com.example.fisioterapi.models;

public class ChatModel {
    private String chat;
    private String sender;
    private String timestamp;
    private String senderName;

    public ChatModel(String chat, String sender, String timestamp) {
        this.chat = chat;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public ChatModel(String chat, String sender, String timestamp, String senderName) {
        this.chat = chat;
        this.sender = sender;
        this.timestamp = timestamp;
        this.senderName = senderName;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
