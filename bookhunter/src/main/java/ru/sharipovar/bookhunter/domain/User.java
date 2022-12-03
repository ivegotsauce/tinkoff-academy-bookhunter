package ru.sharipovar.bookhunter.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "nick"))
public class User {

    @Id
    private UUID id;
    @NotEmpty
    private String nick;
    @NotEmpty
    private String name;
    @NotEmpty

    private Date dateOfBirth;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty
    private double latitude;
    @NotEmpty
    private double longitude;

    public enum Gender {
        MALE,
        FEMALE,
        HIDDEN
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


}
