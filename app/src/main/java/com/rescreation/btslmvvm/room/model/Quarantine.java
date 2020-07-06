package com.rescreation.btslmvvm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quarantine {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String sq_id;

    @ColumnInfo(name = "quarantine_no")
    private String quarantine_no = "";

    @ColumnInfo(name = "start_date")
    private String start_date = "";

    @ColumnInfo(name = "end_date")
    private String end_date = "";

    @ColumnInfo(name = "latitude")
    private String latitude = "";

    @ColumnInfo(name = "longitude")
    private String longitude = "";

    @ColumnInfo(name = "status")
    private String status = "";

    @ColumnInfo(name = "day_remain")
    private String day_remain = "";

    @ColumnInfo(name = "icon_url")
    private String icon_url = "";



    public Quarantine(String sq_id,String quarantine_no,String start_date,String end_date,String latitude,String longitude,
                      String status,String day_remain,String icon_url){

        this.sq_id = sq_id;
        this.quarantine_no = quarantine_no;
        this.start_date = start_date;
        this.end_date = end_date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.day_remain = day_remain;
        this.icon_url = icon_url;


    }


    @NonNull
    public String getSq_id() {
        return sq_id;
    }

    public void setSq_id(@NonNull String sq_id) {
        this.sq_id = sq_id;
    }

    public String getQuarantine_no() {
        return quarantine_no;
    }

    public void setQuarantine_no(String quarantine_no) {
        this.quarantine_no = quarantine_no;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }


    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDay_remain() {
        return day_remain;
    }

    public void setDay_remain(String day_remain) {
        this.day_remain = day_remain;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }


}
