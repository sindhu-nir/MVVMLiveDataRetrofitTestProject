package com.rescreation.btslmvvm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContentTypeModel {


    @NonNull
    @PrimaryKey(autoGenerate = false)
    public int content_type_id;

    @ColumnInfo(name = "content_type_title")
    private String content_type_title;

    @ColumnInfo(name = "file_name")
    private String file_name;

    @ColumnInfo(name = "serial_no")
    private String serial_no;

/*    @ColumnInfo(name = "file_url", typeAffinity = ColumnInfo.BLOB)
    private byte[] file_url;*/



    @ColumnInfo(name = "file_url")
    private String file_url;


    public ContentTypeModel(int content_type_id,String content_type_title, String file_name, String serial_no, String file_url){

        this.content_type_id = content_type_id;
        this.content_type_title = content_type_title;
        this.file_name = file_name;
        this.file_url = file_url;
        this.serial_no = serial_no;


    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_url() {
        return file_url;
    }


    public int getContent_type_id() {
        return content_type_id;
    }

    public void setContent_type_id(int content_type_id) {
        this.content_type_id = content_type_id;
    }

    public String getContent_type_title() {
        return content_type_title;
    }

    public void setContent_type_title(String content_type_title) {
        this.content_type_title = content_type_title;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

/*    public byte[] getFile_url() {
        return file_url;
    }

    public void setFile_url(byte[] file_url) {
        this.file_url = file_url;
    }*/






}
