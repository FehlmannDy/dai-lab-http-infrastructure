package com.example.appdai.model;

/**
 * Represents a group.
 *
 * A group has the following attributes:
 *
 * <ul>
 *   <li><b>groups_id</b>: The unique identifier of the group.</li>
 *   <li><b>groups_name</b>: The name of the group.</li>
 *   <li><b>gender</b>: Gender indicating if is a boys-bands or girls-bands.</li>
 *   <li><b>proposed</b>: A boolean indicating if the group is proposed by a user or not.</li>
 * </ul>
 */
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

    /**
     * Getters and Setters
     */

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
     * Provides a string representation of the group.
     *
     * @return a string describing the group with all its attributes
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

