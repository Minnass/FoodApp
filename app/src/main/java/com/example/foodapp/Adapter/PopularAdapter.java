package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activities.DetailProduct;
import com.example.foodapp.Activities.MainHomeActivity;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.ManagementCart;


import java.io.ByteArrayOutputStream;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    List<FoodModel> mFoodList;
    Context context;

    public PopularAdapter(List<FoodModel> mFoodList, Context context) {
        this.mFoodList = mFoodList;
        this.context = context;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        FoodModel foodModel=mFoodList.get(position);
        if(foodModel==null){
            return ;
        }
        holder.fee.setText(String.valueOf(foodModel.getPrice()));
        holder.pic.setImageResource(foodModel.getId());
        holder.tittle.setText(foodModel.getName());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagementCart.insertFood(MainHomeActivity.selectedItemList,foodModel);
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                holder.pic.setDrawingCacheEnabled(true);
                Bitmap scaledBitmap =holder.pic.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bundle.putByteArray("picture",byteArray);
                bundle.putString("name",holder.tittle.getText().toString());
                bundle.putString("price",holder.fee.getText().toString());
                Intent intent =new Intent(context, DetailProduct.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
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
        TextView tittle, fee;
        ImageView pic;
        TextView addBtn;
        LinearLayout container;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
                container=itemView.findViewById(R.id.container_itemPopular);
                addBtn=itemView.findViewById(R.id.add_popular);
                pic=itemView.findViewById(R.id.img_popular);
                tittle=itemView.findViewById(R.id.title_popular);
                fee=itemView.findViewById(R.id.fee_popular);
        }
    }
}