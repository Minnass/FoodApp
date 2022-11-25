package com.example.foodapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.Photo;
import com.example.foodapp.R;


import java.util.List;

public class PhotoViewPager2Adapter extends RecyclerView.Adapter<PhotoViewPager2Adapter.PhotoViewHolder> {
    private List<Photo> mListPhoto;
    public PhotoViewPager2Adapter(List<Photo> mListPhoto) {
        this.mListPhoto = mListPhoto;
    }
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpaper, parent, false);
        return new PhotoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo=mListPhoto.get(position);
        if(photo==null){
            return;
        }
        holder.imgView.setImageResource(photo.getResource_id());

    }
    @Override
    public int getItemCount() {
        if (mListPhoto == null) {
            return 0;
        } else {
            return mListPhoto.size();
        }
    }
    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.image_viewpaper);
        }
    }
}
