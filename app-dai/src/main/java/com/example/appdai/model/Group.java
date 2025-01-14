package com.example.appdai.model;

import java.util.Date;

public class Group {

    private int groupsId;
    private String groupsName;
    private String gender;
    private String label;
    private Date beginDate;
    private Date disbandDate;
    private boolean proposed;

    public Group() {
    }

    public Group(int groupsId, String groupsName, String gender, String label, Date beginDate, Date disbandDate, boolean proposed) {
        this.groupsId = groupsId;
        this.groupsName = groupsName;
        this.gender = gender;
        this.label = label;
        this.beginDate = beginDate;
        this.disbandDate = disbandDate;
        this.proposed = proposed;
    }

    public int getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(int groupsId) {
        this.groupsId = groupsId;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getDisbandDate() {
        return disbandDate;
    }

    public void setDisbandDate(Date disbandDate) {
        this.disbandDate = disbandDate;
    }

    public boolean isProposed() {
        return proposed;
    }

    public void setProposed(boolean proposed) {
        this.proposed = proposed;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupsId=" + groupsId +
                ", groupsName='" + groupsName + '\'' +
                ", gender=" + gender +
                ", label='" + label + '\'' +
                ", beginDate=" + beginDate +
                ", disbandDate=" + disbandDate +
                ", proposed=" + proposed +
                '}';
    }
}

