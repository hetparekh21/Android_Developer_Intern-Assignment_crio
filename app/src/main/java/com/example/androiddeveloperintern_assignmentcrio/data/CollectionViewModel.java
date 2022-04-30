package com.example.androiddeveloperintern_assignmentcrio.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CollectionViewModel extends AndroidViewModel {

    private collectionRepository mRepository;

    private LiveData<List<collection>> mcollection;

    public CollectionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new collectionRepository(application);
    }

    public LiveData<List<String>> getImagesForTitle(String title){

        return mRepository.getImagesForTitle(title);

    }

    void insertMultipleCollection(List<collection> collectionList){

        mRepository.insertMultipleCollection(collectionList);

    }

    public void insertCollection(collection collection){

        mRepository.insertCollection(collection);

    }

    public LiveData<List<collection>> getCollection() {

        mcollection = mRepository.getCollection();

        return mcollection;
    }

}
