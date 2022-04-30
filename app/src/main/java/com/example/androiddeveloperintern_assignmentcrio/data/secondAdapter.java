package com.example.androiddeveloperintern_assignmentcrio.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddeveloperintern_assignmentcrio.R;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class secondAdapter extends RecyclerView.Adapter<secondAdapter.ViewHolder> {

    List<String> ImageUriList;
    Context context;

    public List<String> getImageUriList() {
        return ImageUriList;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);

        }

    }

    public secondAdapter(List<String> ImageUriList, Context context) {

        this.ImageUriList = ImageUriList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_style, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String uri = ImageUriList.get(position);


    }

    @Override
    public int getItemCount() {

        return ImageUriList.size();

    }

}
