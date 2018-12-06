package com.gpsteller.models;

public class LocationDetailList {

    private String locTime,status,speed,mileage,add;

    public LocationDetailList(String locTime, String status, String speed, String mileage, String add) {
        this.locTime = locTime;
        this.status = status;
        this.speed = speed;
        this.mileage = mileage;
        this.add = add;
    }


    public String getLocTime() {
        return locTime;
    }

    public void setLocTime(String locTime) {
        this.locTime = locTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
