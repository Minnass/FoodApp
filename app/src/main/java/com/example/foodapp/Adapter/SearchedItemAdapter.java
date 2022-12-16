package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Iterface.IClickAddItemListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.List;

public class SearchedItemAdapter extends RecyclerView.Adapter<SearchedItemAdapter.SearchedItemViewHolder> {
    List<FoodModel> foodList;
    Context context;
    IClickFoodItemListener mIClickFoodItemListener;
    IClickAddItemListener mIClickAddingItemListener;

    public SearchedItemAdapter(List<FoodModel> foodList, Context context, IClickFoodItemListener mIClickFoodItemListener) {
        this.foodList = foodList;
        this.context = context;
        this.mIClickFoodItemListener = mIClickFoodItemListener;
    }

    public void setData(List<FoodModel> list) {
        this.foodList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_item, parent, false);
        return new SearchedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedItemViewHolder holder, int position) {
        FoodModel food = foodList.get(position);
        if (food == null) {
            return;
        }
        Glide.with(context).load(food.getImage()).into(holder.foodImage);
        holder.EatingNumber.setText(String.valueOf(food.getEaterNumber())+" người");
        holder.foodName.setText(food.getName().toString());
        holder.quantitySold.setText(String.valueOf(food.getQuantity()));
        holder.originalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(food.getPrice()));
        float currentPrice = food.getPrice() * (1 - (float)food.getDiscount() / 100);
        holder.currentPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(currentPrice));
        holder.discountRate.setText(String.valueOf(food.getDiscount())+"%");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickFoodItemListener.onItemClickHandler(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        }
        return 0;
    }

    public class SearchedItemViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage, btnAddItem;
        TextView discountRate, currentPrice, originalPrice, quantitySold, foodName, EatingNumber;
        RelativeLayout container;
        public SearchedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageItem_searched);
            btnAddItem = itemView.findViewById(R.id.addTocArt);
            discountRate = itemView.findViewById(R.id.discount_searchedItem);
            currentPrice = itemView.findViewById(R.id.currentPrice_searchedItem);
            originalPrice = itemView.findViewById(R.id.originalPrice_searchedItem);
            quantitySold = itemView.findViewById(R.id.soldQuantity_searchedItem);
            foodName = itemView.findViewById(R.id.foodName_searchedItem);
            EatingNumber = itemView.findViewById(R.id.mealAmount);
            container=itemView.findViewById(R.id.containerSearchedItem);
        }
    }
}
