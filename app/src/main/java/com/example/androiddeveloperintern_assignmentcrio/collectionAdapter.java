package com.example.androiddeveloperintern_assignmentcrio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddeveloperintern_assignmentcrio.data.CollectionViewModel;
import com.example.androiddeveloperintern_assignmentcrio.data.collection;
import com.example.androiddeveloperintern_assignmentcrio.data.secondAdapter;

import java.util.ArrayList;
import java.util.List;

public class collectionAdapter extends RecyclerView.Adapter<collectionAdapter.ViewHolder> {

    List<collection> CollectionList;
    Context context;
    OnImageListener mOnImageListener;
    CollectionViewModel collectionViewModel;
    secondAdapter secondAdapter ;
    LifecycleOwner owner ;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView imageView;
        public OnImageListener onImageListener;
        public RecyclerView ChildrecyclerView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnImageListener onImageListener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.action_image);
            ChildrecyclerView = itemView.findViewById(R.id.inset_view);

            this.onImageListener = onImageListener;
            itemView.setOnClickListener(this);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onImageListener.OnImageClick(getAdapterPosition());

                }
            });


        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public RecyclerView getChildrecyclerView() {
            return ChildrecyclerView;
        }

        @Override
        public void onClick(View v) {

            onImageListener.OnImageClick(getAdapterPosition());

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param CollectionList ArrayList<collection> containing the data to populate views to be used
     *                       by RecyclerView.
     */
    public collectionAdapter(List<collection> CollectionList, OnImageListener OnImageListener, Context context, CollectionViewModel viewModel , LifecycleOwner lifecycleOwner) {

        this.context = context;
        this.CollectionList = CollectionList;
        this.mOnImageListener = OnImageListener;
        this.collectionViewModel = viewModel;
        this.owner = lifecycleOwner ;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_style, parent, false);

        return new ViewHolder(view, mOnImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        collection collection = CollectionList.get(position);

        String title = collection.getTitle();
        holder.title.setText(title);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.ChildrecyclerView.setLayoutManager(layoutManager);
        holder.ChildrecyclerView.setHasFixedSize(true);

        secondAdapter = new secondAdapter(new ArrayList<String>() ,context );
        holder.ChildrecyclerView.setAdapter(secondAdapter);

        collectionViewModel.getImagesForTitle(title).observe(owner,ImageList ->{

            secondAdapter.getImageUriList().clear();
            secondAdapter.getImageUriList().addAll(ImageList);

            secondAdapter.notifyDataSetChanged();

        });



    }

    public interface OnImageListener {

        void OnImageClick(int position);

    }

    @Override
    public int getItemCount() {
        return CollectionList.size();
    }

}

