package com.rescreation.btslmvvm.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LocationModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id = 0;



    @ColumnInfo(name = "latitude")
    private double latitude = 0.0;



    @ColumnInfo(name = "longitude")
    private double longitude = 0.0;

    public LocationModel(double latitude,double longitude){

        this.latitude = latitude;
        this.longitude = longitude;


    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
