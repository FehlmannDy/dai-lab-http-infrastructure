/**
 * Artist Class
 * This class is used to represent an artist in the system.
 * An artist has an id, a stage name, a birthdate, an active status and a proposed status.
 * The proposed status is used to know if the artist is proposed by a user or not.
 */
package com.example.appdai.model;

import java.util.Date;

public class Artist {

    private int artistsId;
    private String stageName;
    private Date birthDate;
    private boolean active;
    private boolean proposed;

    /**
     * Default constructor
     */
    public Artist() {
    }

    /**
     * Constructor with all the attributes except the proposed status
     * @param artistsId the id of the artist
     * @param stageName the stage name of the artist
     * @param birthDate the birthdate of the artist
     * @param active the active status of the artist
     */
    public Artist(int artistsId, String stageName, Date birthDate, boolean active) {
        this.artistsId = artistsId;
        this.stageName = stageName;
        this.birthDate = birthDate;
        this.active = active;
        this.proposed = true;
    }

    // Getters and Setters
    public int getArtistsId() {return artistsId;}
    public void setArtistsId(int artistsId) {this.artistsId = artistsId;}

    public String getStageName() {return stageName;}
    public void setStageName(String stageName) {this.stageName = stageName;}

    public Date getBirthDate() {return birthDate;}
    public void setBirthDate(Date birthDate) {this.birthDate = birthDate;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    public boolean isProposed() {return proposed;}
    public void setProposed(boolean proposed) {this.proposed = proposed;}

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
