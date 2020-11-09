
package com.tp.tableClasses;

public class ZoneBean {
    private int zoneId;
    private String zoneName,zoneDescription,countryName,language_type;

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }
   

       public int getZoneId() {
        return zoneId;
    }
   
    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneDescription() {
        return zoneDescription;
    }

    public void setZoneDescription(String zoneDescription) {
        this.zoneDescription = zoneDescription;
    }  

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    } 
 

    
}
