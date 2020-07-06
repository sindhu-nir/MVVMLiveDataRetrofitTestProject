package com.rescreation.btslmvvm.room.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContactTracingNotification {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int push_nt_id ;

    @ColumnInfo(name = "title")
    private String title = "";

    @ColumnInfo(name = "message")
    private String message = "";

    @ColumnInfo(name = "created_at")
    private String created_at = "";

    @ColumnInfo(name = "rssi")
    private String rssi = "";

    public ContactTracingNotification(String title, String message, String created_at,String rssi) {
        this.title = title;
        this.message = message;
        this.created_at = created_at;
        this.rssi = rssi;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    @NonNull
    public int getPush_nt_id() {
        return push_nt_id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setPush_nt_id(@NonNull int push_nt_id) {
        this.push_nt_id = push_nt_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
