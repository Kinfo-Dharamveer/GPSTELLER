package com.gpsteller.models;

public class AlertsModels {


    private String srNo;
    private String cat;
    private String type;
    private String vehicle;
    private String time;

    public AlertsModels(String srNo, String cat, String type, String vehicle, String time) {
        this.srNo = srNo;
        this.cat = cat;
        this.type = type;
        this.vehicle = vehicle;
        this.time = time;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
