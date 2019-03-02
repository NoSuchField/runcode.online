package com.lacecode.model;

public class Message {

    private int channel;
    private String content;
    private String from;
    private String extra;
    private int project_id;

    public Message() {
    }

    public Message(Message message){
        this.channel = message.getChannel();
        this.content = message.getContent();
        this.from = message.getFrom();
        this.extra = message.getExtra();
        this.project_id=message.getProject_id();
    }

    public Message(int channel, String content, String from,int project_id) {
        this.channel = channel;
        this.content = content;
        this.from = from;
        this.extra = null;
        this.project_id=project_id;
    }

    public Message(int channel, String content, String from, String extra,int  project_id) {
        this.channel = channel;
        this.content = content;
        this.from = from;
        this.extra = extra;
        this.project_id=project_id;
    }

    //Getters and Setters

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getChannel() { return channel; }

    public void setChannel(int channel) { this.channel = channel; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}