package com.example.foodapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.CategoryModel;
import com.example.foodapp.R;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<CategoryModel> mCategoriesList;
    Context context;
    public CategoryAdapter(List<CategoryModel> mCategoriesList, Context context) {
        this.mCategoriesList = mCategoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catogory, parent, false);
        return new CategoryViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = mCategoriesList.get(position);
        if ( categoryModel == null) {
            return;
        }
        if(position==0)
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.bg_category1));
        }
        else if(position==1)
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.bg_category2));
        }
        else if(position==2)
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.bg_category3));
        }
        else if(position==3)
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.bg_category4));
        }
        else if(position==4)
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.bg_category5));
        }
        else if(position==5)
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.bg_category6));
        }
        holder.textView.setText(categoryModel.getTitle());
        holder.imageView.setImageResource(categoryModel.getId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeTail();
            }
        });
    }
    @Override
    public int getItemCount() {
        if (mCategoriesList == null) {
            return 0;
        } else {
            return mCategoriesList.size();
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_category);
            textView = itemView.findViewById(R.id.txt_category);
            cardView = itemView.findViewById(R.id.cardview_category);
        }
    }
    void onClickDeTail() {
        Toast.makeText(context, "Duoc roi di thoi", Toast.LENGTH_SHORT).show();
    }
}
