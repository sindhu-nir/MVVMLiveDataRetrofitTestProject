package com.rescreation.btslmvvm.room.dao;



import com.rescreation.btslmvvm.room.model.Quarantine;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface QuarantineDao {

    @Query("SELECT * FROM Quarantine")
    List<Quarantine> getAllquarantine();

    @Insert
    void insert(List<Quarantine> quarantineModel);

    @Insert
    void singleInsert(Quarantine quarantineModel);

    @Delete
    void delete(Quarantine quarantineModel);

    @Update
    void update(Quarantine quarantineModel);

    @Query("SELECT * FROM Quarantine WHERE sq_id = :id")
    List<Quarantine> fetchQuarantineById(String id);



    @Query("DELETE FROM Quarantine")
    void clear();

}
