package com.example.appdai.model;

import java.util.List;

public class User {

    private int usersId;
    private String usersName;
    private String password;
    private boolean isAdmin;
    private List<Photocard> photocardList;

    public User() {
    }

    public User(int usersId, String usersName, String password, boolean isAdmin, List<Photocard> photocardList) {
        this.usersId = usersId;
        this.usersName = usersName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.photocardList = photocardList;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Photocard> getPhotocardList() {
        return photocardList;
    }

    public void setPhotocardList(List<Photocard> photocardList) {
        this.photocardList = photocardList;
    }

    @Override
    public String toString() {
        return "User{" +
                "usersId=" + usersId +
                ", usersName='" + usersName + '\'' +
                ", password='[PROTECTED]'" +
                ", isAdmin=" + isAdmin +
                ", photocardList=" + photocardList +
                '}';
    }
}
