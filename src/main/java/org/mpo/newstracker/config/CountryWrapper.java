package org.mpo.newstracker.config;

import org.mpo.newstracker.entity.CountryObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix="google-web-news")
public class CountryWrapper {

    private CountryObject countryObject;
    private Map<String,CountryObject> countryMap = new HashMap<>();

    public CountryWrapper() {
    }

    public Map<String, CountryObject> getCountryMap() {
        return countryMap;
    }

    public void setCountryMap(Map<String, CountryObject> countryMap) {
        this.countryMap = countryMap;
    }


    public CountryObject getCountryObject(String country) {
        String countryUp = country.toUpperCase();
        if (!countryMap.containsKey(countryUp)){
            countryObject = new CountryObject("DOES NOT EXIST","","","");
        }
        return countryMap.get(countryUp);
    }

    /*public void setCountryObject(CountryObject countryObject) {
        this.countryObject = countryObject;
    }*/
}
