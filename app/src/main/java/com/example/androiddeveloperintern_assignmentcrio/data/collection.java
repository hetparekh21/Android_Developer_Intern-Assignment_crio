package com.example.androiddeveloperintern_assignmentcrio.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "collection")
public class collection {

    public collection collection_(String title , String uri) {

        collection collection = new collection();

        collection.setTitle(title);
        collection.setImages(uri);

        return collection;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String title;

    @ColumnInfo()
    private String images;

    public String getTitle() {
        return title;
    }

    public String getImages() {
        return images;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }
}
