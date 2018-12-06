package com.gpsteller.models;

public class AlertDetailList {


    private String driverName,alertCat,alertType,Time,status;

    public AlertDetailList(String driverName, String alertCat, String alertType, String time, String status) {
        this.driverName = driverName;
        this.alertCat = alertCat;
        this.alertType = alertType;
        Time = time;
        this.status = status;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAlertCat() {
        return alertCat;
    }

    public void setAlertCat(String alertCat) {
        this.alertCat = alertCat;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
