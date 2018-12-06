package com.gpsteller.models;

public class LocationCountList {


    private String LicePlate;
    private String LocaCount;

    public LocationCountList(String licePlate, String locaCount) {
        LicePlate = licePlate;
        LocaCount = locaCount;
    }

    public String getLicePlate() {
        return LicePlate;
    }

    public void setLicePlate(String licePlate) {
        LicePlate = licePlate;
    }

    public String getLocaCount() {
        return LocaCount;
    }

    public void setLocaCount(String locaCount) {
        LocaCount = locaCount;
    }
}
