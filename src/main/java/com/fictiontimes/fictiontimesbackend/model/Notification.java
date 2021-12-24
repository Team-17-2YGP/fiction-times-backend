package com.fictiontimes.fictiontimesbackend.model;

import java.sql.Timestamp;

public class Notification {
    private int notificationId;
    private int userId;
    private String title;
    private String content;
    private String link;
    private boolean isRead;
    private Timestamp timestamp;

    public Notification() {}

    public Notification(int notificationId, int userId, String title, String content, String link, boolean isRead, Timestamp timestamp) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.isRead = isRead;
        this.timestamp = timestamp;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
