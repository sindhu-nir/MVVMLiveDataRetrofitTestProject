package com.rescreation.btslmvvm.room.dao;


import com.rescreation.btslmvvm.room.model.ContactTracingNotification;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactTracingNotificationDao {

    @Query("SELECT * FROM ContactTracingNotification")
    List<ContactTracingNotification> getAllContactTracingNotiData();

    @Insert
    void insert(List<ContactTracingNotification> contactTracingNotifications);

    @Insert
    void singleInsert(ContactTracingNotification contactTracingNotification);

    @Delete
    void delete(ContactTracingNotification contactTracingNotification);

    @Update
    void update(ContactTracingNotification contactTracingNotification);



    @Query("DELETE FROM ContactTracingNotification")
    void clear();
}
