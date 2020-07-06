package com.rescreation.btslmvvm.room.dao;




import com.rescreation.btslmvvm.room.model.ContentTypeModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContentTypeDao {
    @Query("SELECT * FROM ContentTypeModel")
    List<ContentTypeModel> getAllContentType();

    @Insert
    void insert(List<ContentTypeModel> contentTypeModel);

    @Insert
    void singleInsert(ContentTypeModel contentTypeModel);

    @Delete
    void delete(ContentTypeModel contentTypeModel);

    @Update
    void update(ContentTypeModel contentTypeModel);

    @Query("SELECT * FROM ContentTypeModel WHERE content_type_id = :id")
    ContentTypeModel fetchBeneficiaryById(int id);

    @Query("UPDATE ContentTypeModel SET file_url = :file_url WHERE content_type_id = :id")
    void updateContentTypeModel(int id, String file_url);


    /*UPDATE ContentTypeModel
SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
WHERE CustomerID = 1;*/

    @Query("DELETE FROM ContentTypeModel")
    void clear();
}
