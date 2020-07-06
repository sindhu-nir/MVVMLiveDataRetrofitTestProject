package com.rescreation.btslmvvm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class ObjectModel {


    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String content_id = "";

    @ColumnInfo(name = "content_type_id")
    private String content_type_id = "";

    @ColumnInfo(name = "content_title")
    String content_title = "";


    @ColumnInfo(name = "file_url")
    String file_url = "";

    @ColumnInfo(name = "file_type")
    String file_type = "";

    @ColumnInfo(name = "ref_url")
    String ref_url = "";

    @ColumnInfo(name = "icon_url")
    String icon_url = "";

    @ColumnInfo(name = "content_serial")
    String content_serial = "";

    @ColumnInfo(name = "post_date")
    String post_date = "";

    @ColumnInfo(name = "video_url")
    String video_url = "";


    @ColumnInfo(name = "description")
    String description = "";


    public ObjectModel(String content_id,String content_type_id,String content_title, String description, String file_type,
                       String video_url, String ref_url,String content_serial,String post_date,String icon_url,String file_url){


        this.content_id = content_id;
        this.content_type_id = content_type_id;
        this.content_title = content_title;
        this.description = description;
        this.file_type = file_type;
        this.video_url = video_url;
        this.ref_url = ref_url;
        this.content_serial = content_serial;
        this.post_date = post_date;
        this.icon_url = icon_url;
        this.file_url = file_url;


    }


    @NonNull
    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(@NonNull String content_id) {
        this.content_id = content_id;
    }
    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }







    public String getContent_type_id() {
        return content_type_id;
    }

    public void setContent_type_id(String content_type_id) {
        this.content_type_id = content_type_id;
    }


    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }




    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }


    public String getContent_serial() {
        return content_serial;
    }

    public void setContent_serial(String content_serial) {
        this.content_serial = content_serial;
    }


    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getRef_url() {
        return ref_url;
    }

    public void setRef_url(String ref_url) {
        this.ref_url = ref_url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }


}
