package com.rescreation.btslmvvm.room.dao;



import com.rescreation.btslmvvm.room.model.LocationModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM LocationModel")
    List<LocationModel> getAllLocation();

    @Insert
    void insert(List<LocationModel> locationModel);

    @Insert
    void singleInsert(LocationModel locationModel);

    @Delete
    void delete(LocationModel locationModel);

    @Update
    void update(LocationModel locationModel);

    @Query("SELECT * FROM LocationModel WHERE id = :id")
    List<LocationModel> fetchlocationModelById(String id);


    @Query("DELETE FROM LocationModel")
    void clear();
}
