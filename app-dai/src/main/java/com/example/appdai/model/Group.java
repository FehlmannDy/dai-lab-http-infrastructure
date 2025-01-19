/**
 * Class Group
 * This class is used to represent a group in the system.
 */

package com.example.appdai.model;


public class Group {

    private int groups_id;
    private String groups_name;
    private String gender;
    private boolean proposed;

    /**
     * Default constructor
     */
    public Group() {
    }

    /**
     * Constructor without disbandDate
     *
     * @param groupsId ID of the group
     * @param groupsName Name of the group
     * @param gender Gender of the group
     */
    public Group(int groupsId, String groupsName, String gender) {
        this.groups_id = groupsId;
        this.groups_name = groupsName;
        this.gender = gender;
        this.proposed = true;
    }

    // Getters and Setters
    public int getGroups_id() {
        return groups_id;
    }
    public void setGroups_id(int groups_id) {
        this.groups_id = groups_id;
    }

    public String getGroups_name() {
        return groups_name;
    }
    public void setGroups_name(String groups_name) {
        this.groups_name = groups_name;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isProposed() {
        return proposed;
    }
    public void setProposed(boolean proposed) {
        this.proposed = proposed;
    }

    /**
     * toString Method
     * Method to display the group's information
     * @return String
     */
    @Override
    public String toString() {
        return "Group{" +
                "groups_id=" + groups_id +
                ", groups_name='" + groups_name + '\'' +
                ", gender=" + gender + '\'' +
                ", proposed=" + proposed +
                '}';
    }
}

