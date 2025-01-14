package com.example.appdai.model;

import java.util.Date;

public class Artist {

    private int artistsId;
    private String stageName;
    private Date birthDate;
    private boolean active;
    private boolean proposed;

    public Artist() {
    }

    public Artist(int artistsId, String stageName, Date birthDate, boolean active, boolean proposed) {
        this.artistsId = artistsId;
        this.stageName = stageName;
        this.birthDate = birthDate;
        this.active = active;
        this.proposed = proposed;
    }

    public int getArtistsId() {
        return artistsId;
    }

    public void setArtistsId(int artistsId) {
        this.artistsId = artistsId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isProposed() {
        return proposed;
    }

    public void setProposed(boolean proposed) {
        this.proposed = proposed;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistsId=" + artistsId +
                ", stageName='" + stageName + '\'' +
                ", birthDate=" + birthDate +
                ", active=" + active +
                ", proposed=" + proposed +
                '}';
    }
}
