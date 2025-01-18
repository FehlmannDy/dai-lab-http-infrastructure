/**
 * User Class
 * This class is used to create a User object
 * The user is composed of an ID, a name, a password, a boolean to know if the user is an Admin and a list of Photocard
 */
package com.example.appdai.model;

import java.util.List;

public class User {

    private int usersId;
    private String usersName;
    private String password;
    private boolean isAdmin;
    private List<Photocard> photocardList;

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Constructor
     * @param usersId ID of the User
     * @param usersName Name of the User
     * @param password Password of the User
     * @param isAdmin Boolean to know if the User is an Admin
     * @param photocardList List of Photocard of the User
     */
    public User(int usersId, String usersName, String password, boolean isAdmin, List<Photocard> photocardList) {
        this.usersId = usersId;
        this.usersName = usersName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.photocardList = photocardList;
    }

    public int getUsersId() {return usersId;}
    public void setUsersId(int usersId) {this.usersId = usersId;}

    public String getUsersName() {return usersName;}
    public void setUsersName(String usersName) {this.usersName = usersName;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public boolean isAdmin() {return isAdmin;}
    public void setAdmin(boolean isAdmin) {this.isAdmin = isAdmin;}

    public List<Photocard> getPhotocardList() {return photocardList;}
    public void setPhotocardList(List<Photocard> photocardList) {this.photocardList = photocardList;}

    /**
     * toString method
     * This method is used to display the User object
     * @return String
     */
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
