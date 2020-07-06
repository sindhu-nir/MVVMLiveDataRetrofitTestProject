package com.rescreation.btslmvvm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notification {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String push_nt_id = "";

    @ColumnInfo(name = "title")
    private String title = "";

    @ColumnInfo(name = "message")
    private String message = "";

    @ColumnInfo(name = "event_type")
    private String event_type = "";

    @ColumnInfo(name = "event_time")
    private String event_time = "";

    @ColumnInfo(name = "status")
    private String status = "";

    @ColumnInfo(name = "created_at")
    private String created_at = "";

    @ColumnInfo(name = "updated_at")
    private String updated_at = "";


    public Notification(String push_nt_id,String title,String message,String event_type,String event_time,String status,
                        String created_at,String updated_at){

        this.push_nt_id = push_nt_id;
        this.title = title;
        this.message = message;
        this.event_type = event_type;
        this.event_time = event_time;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }

    @NonNull
    public String getPush_nt_id() {
        return push_nt_id;
    }

    public void setPush_nt_id(@NonNull String push_nt_id) {
        this.push_nt_id = push_nt_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


}
