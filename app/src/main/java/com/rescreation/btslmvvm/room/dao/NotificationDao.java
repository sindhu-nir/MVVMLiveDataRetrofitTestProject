package com.rescreation.btslmvvm.room.dao;




import com.rescreation.btslmvvm.room.model.Notification;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM Notification")
    List<Notification> getAllNotification();

    @Insert
    void insert(List<Notification> notificationsModel);

    @Insert
    void singleInsert(Notification notificationModel);

    @Delete
    void delete(Notification notificationModel);

    @Update
    void update(Notification notificationModel);

    @Query("SELECT * FROM Notification WHERE push_nt_id = :id")
    List<Notification> fetchNotificationModelById(String id);


    @Query("DELETE FROM Notification")
    void clear();
}
