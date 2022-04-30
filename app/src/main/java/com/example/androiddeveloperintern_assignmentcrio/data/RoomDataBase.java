package com.example.androiddeveloperintern_assignmentcrio.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {collection.class}, version = 1, exportSchema = false)
//@TypeConverters({converters.class})
public abstract class RoomDataBase extends RoomDatabase {

    public abstract collectionDao collectionDao();

    public static volatile RoomDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDataBase.class, "_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadExecutor().execute(() -> {

                String uri = "content://com.android.providers.media.documents/document/image%3A47467" ;

                collectionDao dao = INSTANCE.collectionDao();

                long key = dao.insertCollection(new collection().collection_("First Collection",uri));
                Log.e("Room DataBase", "onCreate: Key : " + key );

            });

        }
    };

}
