package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Iterface.IClickFavoriteListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;


import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    List<FoodModel> mFoodList;
    Context context;
    IClickFoodItemListener mIListener;
    IClickFavoriteListener mListener1;

    public PopularAdapter(List<FoodModel> mFoodList, Context context, IClickFoodItemListener mIListener, IClickFavoriteListener mListener1) {
        this.mFoodList = mFoodList;
        this.context = context;
        this.mIListener = mIListener;
        this.mListener1 = mListener1;
    }

    public void setData(List<FoodModel> list) {
        this.mFoodList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        FoodModel foodModel = mFoodList.get(position);
        if (foodModel == null) {
            return;
        }
        if (foodModel.getDiscount() == 0) {
            holder.discount.setVisibility(View.GONE);
            holder.originalFee.setVisibility(View.GONE);
        }
        Glide.with(context).load(foodModel.getImage()).into(holder.pic);
        holder.originalFee.setText(String.valueOf(foodModel.getPrice()));
        float currentPrice = foodModel.getPrice() * (1 - (float) foodModel.getDiscount() / 100);
        holder.currentFee.setText(String.valueOf(currentPrice));
        holder.discount.setText(String.valueOf(foodModel.getDiscount()));
        holder.tittle.setText(foodModel.getName());
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener1.onItemClickHandler(foodModel);
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIListener.onItemClickHandler(foodModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mFoodList == null) {
            return 0;
        } else {
            return mFoodList.size();
        }
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        TextView tittle, currentFee, originalFee, discount;
        ImageView pic;
        LinearLayout favorite;
        LinearLayout container;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container_itemPopular);
            favorite = itemView.findViewById(R.id.favorite_ItemPopular);
            pic = itemView.findViewById(R.id.img_popular);
            tittle = itemView.findViewById(R.id.title_popular);
            discount = itemView.findViewById(R.id.discount_text_popular);
            currentFee = itemView.findViewById(R.id.currentPrice_popular);
            originalFee = itemView.findViewById(R.id.originalPrice_popular);
        }
    }
}
