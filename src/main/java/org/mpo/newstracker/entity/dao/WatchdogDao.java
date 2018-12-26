package org.mpo.newstracker.entity.dao;

import org.mpo.newstracker.entity.dto.WatchdogDto;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name="watchdog")
public class WatchdogDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "watchdog_id")
    private int id;

    private String keywords;
    private String email;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    private String country;

    private boolean translate;

    public WatchdogDao() {
    }

    public WatchdogDao(String keywords, String email, String country, boolean translate) {
        //this.id = id;
        this.keywords = keywords;
        this.email = email;
        this.dateCreated = LocalDateTime.now();//LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.country = country;
        this.translate = translate;
    }

    public WatchdogDao(WatchdogDto watchdogDto){
        this.keywords = watchdogDto.getKeywords();
        this.email = watchdogDto.getEmail();
        this.dateCreated = LocalDateTime.now();//LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.country=watchdogDto.getCountry();
        this.translate = watchdogDto.isTranslate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "WatchdogDao{" +
                "id=" + id +
                ", keywords='" + keywords + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                ", dateCreated=" + dateCreated +
                ", country='" + country + '\'' +
                ", translate=" + translate +
                '}';
    }
}
