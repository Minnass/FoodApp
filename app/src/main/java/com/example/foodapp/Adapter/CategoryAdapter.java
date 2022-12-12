package com.example.foodapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Iterface.IClickFoodCategoryListener;
import com.example.foodapp.R;


import java.util.HashMap;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    IClickFoodCategoryListener mLisener;

    HashMap<Categories, Integer> categories;


    public CategoryAdapter(Context context, IClickFoodCategoryListener mLisener) {
        this.context = context;
        this.mLisener = mLisener;
        categories=new HashMap<>();
        categories.put(Categories.RICE,R.drawable.rice);
        categories.put(Categories.NOODLE,R.drawable.noodles);
        categories.put(Categories.BEVERAGE,R.drawable.drinking);
        categories.put(Categories.FASTFOOD,R.drawable.fastfood);
        categories.put(Categories.MEATANDFISH,R.drawable.steak);
        categories.put(Categories.DESSERT,R.drawable.cupcake);
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
        Categories value = Categories.values()[position];
        holder.textView.setText(value.toString());
        holder.imageView.setImageResource(categories.get(value));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLisener.onItemFoodCategoryHandler(value);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Categories.values().length;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout container;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.categoryName);
            imageView = itemView.findViewById(R.id.img_category);
            container = itemView.findViewById(R.id.container_categoryItem);
        }
    }


}
