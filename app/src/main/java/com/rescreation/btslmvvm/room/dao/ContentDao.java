package com.rescreation.btslmvvm.room.dao;



import com.rescreation.btslmvvm.room.model.ObjectModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContentDao {

    @Query("SELECT * FROM ObjectModel")
    List<ObjectModel> getAllContentt();

    @Insert
    void insert(List<ObjectModel> contentModel);

    @Insert
    void singleInsert(ObjectModel contentModel);

    @Delete
    void delete(ObjectModel contentModel);

    @Update
    void update(ObjectModel contentModel);

    @Query("SELECT * FROM ObjectModel WHERE content_type_id = :id")
    List<ObjectModel> fetchObjectModelById(String id);

    @Query("SELECT file_url FROM ObjectModel WHERE content_id = :id")
    String fetchFileUrllById(String id);

    @Query("UPDATE ObjectModel SET file_url = :file_url WHERE content_id = :id")
    void updateContentModel(String id, String file_url);

    @Query("DELETE FROM ObjectModel WHERE content_type_id = :contentId")
    void deleteById(String contentId);


    /*UPDATE ContentTypeModel
SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
WHERE CustomerID = 1;*/

    @Query("DELETE FROM ObjectModel")
    void clear();
}
