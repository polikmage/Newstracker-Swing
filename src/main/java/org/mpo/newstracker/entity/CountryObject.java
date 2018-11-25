package org.mpo.newstracker.entity;

public class CountryObject {
    private String country;
    private String hl;
    private String gl;
    private String ceid;
    //private int maxChars;

    public CountryObject() {
    }


    public CountryObject(String country, String hl, String gl, String ceid) {
        this.country = country;
        this.hl = hl;
        this.gl = gl;
        this.ceid = ceid;
        //this.maxChars = maxChars;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getCeid() {
        return ceid;
    }

    public void setCeid(String ceid) {
        this.ceid = ceid;
    }


    @Override
    public String toString() {
        return "CountryObject{" +
                "country='" + country + '\'' +
                ", hl='" + hl + '\'' +
                ", gl='" + gl + '\'' +
                ", ceid='" + ceid + '\'' + '}';
    }
}
