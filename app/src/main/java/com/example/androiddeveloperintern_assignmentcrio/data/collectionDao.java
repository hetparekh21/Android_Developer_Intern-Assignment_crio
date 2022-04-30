package com.example.androiddeveloperintern_assignmentcrio.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface collectionDao {

    @Query("SELECT * FROM collection group by title;")
    LiveData<List<collection>> getCollection();

    @Insert()
    Long insertCollection(collection collection);

    @Insert()
    void insertMultipleCollection(List<collection> collectionList);

    @Query("select images from collection where title = :title ;")
    LiveData<List<String>> getImagesForTitle(String title);


}
