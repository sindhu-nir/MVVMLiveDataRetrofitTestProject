package com.rescreation.btslmvvm.room.database;

import android.content.Context;


import com.rescreation.btslmvvm.room.dao.ContactTracingDao;
import com.rescreation.btslmvvm.room.dao.ContactTracingNotificationDao;
import com.rescreation.btslmvvm.room.dao.ContentDao;
import com.rescreation.btslmvvm.room.dao.ContentTypeDao;
import com.rescreation.btslmvvm.room.dao.LocationDao;
import com.rescreation.btslmvvm.room.dao.NotificationDao;
import com.rescreation.btslmvvm.room.dao.QuarantineDao;
import com.rescreation.btslmvvm.room.model.ContactTracingModel;
import com.rescreation.btslmvvm.room.model.ContactTracingNotification;
import com.rescreation.btslmvvm.room.model.ContentTypeModel;
import com.rescreation.btslmvvm.room.model.LocationModel;
import com.rescreation.btslmvvm.room.model.Notification;
import com.rescreation.btslmvvm.room.model.ObjectModel;
import com.rescreation.btslmvvm.room.model.Quarantine;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContentTypeModel.class, ObjectModel.class, Notification.class, LocationModel.class, Quarantine.class, ContactTracingModel.class, ContactTracingNotification.class}, version = 1)
public abstract class DatabaseInstance extends RoomDatabase {

    private static DatabaseInstance db;

    private static String DB_NAME = "contact_tracing_db";

    public static DatabaseInstance getInstance(Context context) {
        if (null == db) {
            db = Room.databaseBuilder(context.getApplicationContext(), DatabaseInstance.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract ContentTypeDao contentTypeDao();
    public abstract ContentDao contentDao();
    public abstract NotificationDao notificationDao();
    public abstract LocationDao locationDao();
    public abstract QuarantineDao quarantineDao();
    public abstract ContactTracingDao contactTracingDao();
    public abstract ContactTracingNotificationDao contactTracingNotificationDao();
}
