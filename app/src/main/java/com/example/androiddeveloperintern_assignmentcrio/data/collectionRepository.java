package com.example.androiddeveloperintern_assignmentcrio.data;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class collectionRepository {

    private LiveData<List<collection>> mcollection;
    private collectionDao mcollectionDao;

    public collectionRepository(Application application) {

        RoomDataBase db = RoomDataBase.getDatabase(application);
        mcollectionDao = db.collectionDao();
        mcollection = mcollectionDao.getCollection();

    }

    void insertMultipleCollection (List<collection> collectionList){

        mcollectionDao.insertMultipleCollection(collectionList);

    }

    LiveData<List<String>> getImagesForTitle(String title){

        return mcollectionDao.getImagesForTitle(title);

    }

    LiveData<List<collection>> getCollection() {

        return mcollection;

    }

    void insertCollection(collection collection){

        mcollectionDao.insertCollection(collection);

    }

}
