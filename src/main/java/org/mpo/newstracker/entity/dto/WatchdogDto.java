package org.mpo.newstracker.entity.dto;

import org.mpo.newstracker.entity.dao.WatchdogDao;

import java.time.LocalDateTime;

public class WatchdogDto {
    private String keywords;
    private String email;
    private String country;
    private LocalDateTime dateCreated;
    private boolean translate;
    //private int userId;


    public WatchdogDto() {
    }

    public WatchdogDto(WatchdogDao watchdogDao){
        this.keywords = watchdogDao.getKeywords();
        this.email = watchdogDao.getEmail();
        this.country = watchdogDao.getCountry();
        this.dateCreated = watchdogDao.getDateCreated();
        this.translate = watchdogDao.isTranslate();
    }

    public WatchdogDto(String keywords, String email, String country, LocalDateTime dateCreated,boolean translate) {
        this.keywords = keywords;
        this.email = email;
        this.country = country;
        this.dateCreated = dateCreated;
        this.translate = translate;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }
}
