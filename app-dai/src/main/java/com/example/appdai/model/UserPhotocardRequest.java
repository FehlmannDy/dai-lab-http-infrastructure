package com.example.appdai.model;

public class UserPhotocardRequest {
    private int userId;
    private int photocardId;
    private boolean have;

    // Getters et setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getPhotocardId() { return photocardId; }
    public void setPhotocardId(int photocardId) { this.photocardId = photocardId; }
    public boolean isHave() { return have; }
    public void setHave(boolean have) { this.have = have; }
}
