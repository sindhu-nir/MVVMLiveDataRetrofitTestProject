package com.rescreation.btslmvvm.room.dao;


import com.rescreation.btslmvvm.room.model.ContactTracingModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactTracingDao {

    @Query("SELECT * FROM ContactTracingModel")
    List<ContactTracingModel> getAllContactTracingData();

    @Insert
    void insert(List<ContactTracingModel> contactTracingModel);

    @Insert
    void singleInsert(ContactTracingModel contactTracingModel);

    @Delete
    void delete(ContactTracingModel contactTracingModel);

    @Update
    void update(ContactTracingModel contactTracingModel);



    @Query("DELETE FROM ContactTracingModel")
    void clear();

}
