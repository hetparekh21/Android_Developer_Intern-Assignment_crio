package com.example.androiddeveloperintern_assignmentcrio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.androiddeveloperintern_assignmentcrio.data.CollectionViewModel;
import com.example.androiddeveloperintern_assignmentcrio.data.TaskAsyncTask;
import com.example.androiddeveloperintern_assignmentcrio.data.collection;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddeveloperintern_assignmentcrio.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements dialog.NoticeDialogListener , collectionAdapter.OnImageListener{

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;

    private static final String TAG = "MainActivity";
    private static final int SELECT_PICTURES = 100;
    private AppBarConfiguration appBarConfiguration;
    private collectionAdapter mAdapter ;
    RecyclerView recyclerView;
    private ActivityMainBinding binding;
    private CollectionViewModel viewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CollectionViewModel.class);

        recyclerView = findViewById(R.id.collections_list);

        recyclerView.setHasFixedSize(true);

        mAdapter  = new collectionAdapter(new ArrayList<collection>(), this,this,viewModel,this);

        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getCollection().observe(this , collectionsList -> {

            print_data(collectionsList);

            mAdapter.CollectionList.clear();
            mAdapter.CollectionList.addAll(collectionsList);

            mAdapter.notifyDataSetChanged();

        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog d = new dialog();
                d.show(getSupportFragmentManager(),"dialog");
            }
        });

    }

    public void print_data(List<collection> collectionsList){

            if(collectionsList.isEmpty()){

                Log.e(TAG, "print_data: Empty List LOL" );

            }

            for(int i = 0 ; i < collectionsList.size() ; i++){

                Log.e(TAG, "print_data: " + collectionsList.get(i).getTitle() + " Images : "+ collectionsList.get(i).getImages());

            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        EditText editText = dialog.getDialog().findViewById(R.id.username);

        String title = editText.getText().toString() ;

        Log.e(TAG, "onDialogPositiveClick: Positive Click : " + title);

        if(!title.isEmpty()){

            Log.e(TAG, "onDialogPositiveClick: Executing" );
            new TaskAsyncTask(this,viewModel,title,TaskAsyncTask.INSERT_COLLECTION).execute();

        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        Log.e(TAG, "onDialogPositiveClick: Negative Click" );

    }

    @Override
    public void OnImageClick(int position) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    imagesEncodedList.add(mImageUri.toString());
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            imagesEncodedList.add(uri.toString());
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
//            Log.e(TAG, "onActivityResult: " + e.toString() );
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        for(int i = 0 ; i < imagesEncodedList.size() ; i++){

            Log.e(TAG, "onActivityResult: "+ imagesEncodedList.get(i) );

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

//    @SuppressLint("Range")
//    public String getImageFilePath(Uri uri) {
//
//        File file = new File(uri.getPath());
//        String[] filePath = file.getPath().split(":");
//        String image_id = filePath[filePath.length - 1];
//        String imagePath = null;
//
//        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
//        if (cursor!=null) {
//            cursor.moveToFirst();
//            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            cursor.close();
//        }
//
//        return imagePath ;
//
//    }

}