package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Activities.DetailFoodActivity;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    private Context context;
    List<FoodModel> mFoodList;
    IClickFoodItemListener listener;


    public FoodListAdapter(Context context, List<FoodModel> mFoodList, IClickFoodItemListener listener) {
        this.context = context;
        this.mFoodList = mFoodList;
        this.listener = listener;
    }

    public void setData(List<FoodModel> foodList) {
        this.mFoodList = foodList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodModel foodModel = mFoodList.get(position);
        if (foodModel == null) {
            return;
        }
        if (foodModel.getDiscount() == 0) {
            holder.discount_rate.setVisibility(View.GONE);
            holder.originalPrice.setVisibility(View.GONE);
        }
        Glide.with(context).load(foodModel.getImage()).into(holder.imgFood);
        holder.discount_rate.setText("Giảm "+foodModel.getDiscount() + "%");
        holder.foodName.setText((foodModel.getName()));
        holder.originalPrice.setText(String.valueOf(foodModel.getPrice()));
        holder.quantitySold.setText(String.valueOf(foodModel.getQuantity()));
        float currentPrice = foodModel.getPrice() * (1 - (float)foodModel.getDiscount() / 100);
        holder.currentPrice.setText(String.valueOf(currentPrice));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickHandler(foodModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mFoodList != null) {
            return mFoodList.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView foodName, originalPrice, discount_rate, currentPrice,quantitySold;
        private LinearLayout container;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            originalPrice = itemView.findViewById(R.id.originalPrice);
            discount_rate = itemView.findViewById(R.id.discount_text);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            quantitySold= itemView.findViewById(R.id.quantity_sold);
            container=itemView.findViewById(R.id.containerLayout);

        }
    }
}
