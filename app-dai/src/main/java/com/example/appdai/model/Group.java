/**
 * Class Group
 * This class is used to represent a group in the system.
 */

package com.example.appdai.model;

import java.util.Date;

public class Group {

    private int groupsId;
    private String groupsName;
    private String gender;
    private Date beginDate;
    private Date disbandDate;
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
     * @param beginDate Date of the group's debut
     */
    public Group(int groupsId, String groupsName, String gender, Date beginDate) {
        this.groupsId = groupsId;
        this.groupsName = groupsName;
        this.gender = gender;
        this.beginDate = beginDate;
        this.disbandDate = null;
        this.proposed = true;
    }

    /**
     * Constructor with disbandDate
     *
     * @param groupsId ID of the group
     * @param groupsName Name of the group
     * @param gender Gender of the group
     * @param beginDate Date of the group's debut
     * @param disbandDate Date of the group's disbandment
     */
    public Group(int groupsId, String groupsName, String gender, Date beginDate, Date disbandDate) {
        this.groupsId = groupsId;
        this.groupsName = groupsName;
        this.gender = gender;
        this.beginDate = beginDate;
        this.disbandDate = disbandDate;
        this.proposed = true;
    }

    // Getters and Setters
    public int getGroupsId() {return groupsId;}
    public void setGroupsId(int groupsId) {this.groupsId = groupsId;}

    public String getGroupsName() {return groupsName;}
    public void setGroupsName(String groupsName) {this.groupsName = groupsName;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public Date getBeginDate() {return beginDate;}
    public void setBeginDate(Date beginDate) {this.beginDate = beginDate;}

    public Date getDisbandDate() {return disbandDate;}
    public void setDisbandDate(Date disbandDate) {this.disbandDate = disbandDate;}

    public boolean isProposed() {return proposed;}
    public void setProposed(boolean proposed) {this.proposed = proposed;}

    /**
     * toString Method
     * Method to display the group's information
     * @return String
     */
    @Override
    public String toString() {
        return "Group{" +
                "groupsId=" + groupsId +
                ", groupsName='" + groupsName + '\'' +
                ", gender=" + gender + '\'' +
                ", beginDate=" + beginDate +
                ", disbandDate=" + disbandDate +
                ", proposed=" + proposed +
                '}';
    }
}

