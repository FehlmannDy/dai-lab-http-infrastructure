package com.example.appdai.model;

import java.util.List;

/**
 * Represents a user.
 *
 * <p>A user is composed of the following attributes:</p>
 * <ul>
 *   <li><b>user_id</b>: The unique identifier for the user.</li>
 *   <li><b>username</b>: The name of the user.</li>
 *   <li><b>password</b>: The user's password.</li>
 *   <li><b>is_admin</b>: A boolean indicating whether the user has administrative privileges.</li>
 *   <li><b>photocardList</b>: A list of {@link Photocard} objects associated with the user.</li>
 * </ul>
 */
public class User {

    private int user_id;
    private String username;
    private String password;
    private boolean is_admin;
    private List<Photocard> photocardList;

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Constructor
     *
     * @param usersId ID of the User
     * @param usersName Name of the User
     * @param password Password of the User
     * @param isAdmin Boolean to know if the User is an Admin
     * @param photocardList List of Photocard of the User
     */
    public User(int usersId, String usersName, String password, boolean isAdmin, List<Photocard> photocardList) {
        this.user_id = usersId;
        this.username = usersName;
        this.password = password;
        this.is_admin = isAdmin;
        this.photocardList = photocardList;
    }

    public int getUser_id() {return user_id;}
    public void setUser_id(int user_id) {this.user_id = user_id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public boolean isIs_admin() {return is_admin;}
    public void setIs_admin(boolean isAdmin) {this.is_admin = isAdmin;}

    public List<Photocard> getPhotocardList() {return photocardList;}
    public void setPhotocardList(List<Photocard> photocardList) {this.photocardList = photocardList;}

    /**
     * Provides a string representation of the user.
     *
     * @return a string describing the user with all its attributes
     */
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", is_admin=" + is_admin +
                ", photocardList=" + photocardList +
                '}';
    }
}
