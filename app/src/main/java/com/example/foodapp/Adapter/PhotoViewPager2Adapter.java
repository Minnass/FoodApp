package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;


import java.util.List;

public class PhotoViewPager2Adapter extends RecyclerView.Adapter<PhotoViewPager2Adapter.PhotoViewHolder> {
    private List<String> mListPhoto;
    Context context;

    public PhotoViewPager2Adapter(List<String> mListPhoto, Context context) {
        this.mListPhoto = mListPhoto;
        this.context = context;
    }

    public  void setData(List<String> list)
    {
        this.mListPhoto=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpaper, parent, false);
        return new PhotoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String photo=mListPhoto.get(position);
        if(photo==null){
            return;
        }
        Glide.with(context).load(photo).into(holder.imgView);

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
