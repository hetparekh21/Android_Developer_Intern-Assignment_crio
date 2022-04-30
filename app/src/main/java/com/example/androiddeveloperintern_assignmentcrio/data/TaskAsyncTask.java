package com.example.androiddeveloperintern_assignmentcrio.data;

import android.app.Activity;
import android.os.AsyncTask;

public class TaskAsyncTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private CollectionViewModel viewModel;
    private String title;
    private int queryCase;
    public static final int INSERT_COLLECTION = 1;

    public TaskAsyncTask(Activity activity, CollectionViewModel collectionViewModel, String title, int queryCase) {

        this.activity = activity;
        this.viewModel = collectionViewModel;
        this.title = title;
        this.queryCase = queryCase;

    }


    @Override
    protected Void doInBackground(Void... voids) {

        switch (queryCase) {

            case INSERT_COLLECTION:

                collection collection = new collection();
                collection.setTitle(title);

                viewModel.insertCollection(collection);
                break;

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);



    }
}
