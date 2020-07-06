package com.rescreation.btslmvvm.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContactTracingModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "myUid")
    private String myUid;

    @ColumnInfo(name = "contactUid")
    private String contactUid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mobile")
    private String mobile;

    @ColumnInfo(name = "send_time")
    private String send_time;

    @ColumnInfo(name = "rssi")
    private String rssi;

    public ContactTracingModel(String myUid,String contactUid,String name,String mobile,String send_time,String rssi) {
        this.myUid = myUid;
        this.contactUid = contactUid;
        this.name = name;
        this.mobile = mobile;
        this.send_time = send_time;
        this.rssi = rssi;
    }

    public int getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMyUid() {
        return myUid;
    }

    public String getContactUid() {
        return contactUid;
    }

    public String getRssi() {
        return rssi;
    }

    public void setMyUid(String myUid) {
        this.myUid = myUid;
    }

    public void setContactUid(String contactUid) {
        this.contactUid = contactUid;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }
}
