package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Iterface.IClickAddItemListener;
import com.example.foodapp.Iterface.IClickAdminFoodOptionListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.List;

public class FoodListAdminAdapter extends RecyclerView.Adapter<FoodListAdminAdapter.AdminFoodItemViewHolder> {
    List<FoodModel> foodList;
    Context context;
    IClickFoodItemListener mIClickFoodItemListener;
    IClickAdminFoodOptionListener clickAdminFoodOptionListener;

    public FoodListAdminAdapter(List<FoodModel> foodList, Context context, IClickFoodItemListener mIClickFoodItemListener, IClickAdminFoodOptionListener clickAdminFoodOptionListener) {
        this.foodList = foodList;
        this.context = context;
        this.mIClickFoodItemListener = mIClickFoodItemListener;
        this.clickAdminFoodOptionListener = clickAdminFoodOptionListener;
    }

    public void setData(List<FoodModel> list) {
        this.foodList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminFoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_admin, parent, false);
        return new AdminFoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodItemViewHolder holder, int position) {
        FoodModel food = foodList.get(position);
        if (food == null) {
            return;
        }

        if (food.getImage().contains("http")) {
            Glide.with(context).load(food.getImage()).into(holder.foodImage);
        } else {
            String path= InternetConnection.BASE_URL+"images/"+food.getImage();
            Glide.with(context).load(path).into(holder.foodImage);
        }
        holder.foodName.setText(food.getName().toString());
        holder.foodOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, food);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public class AdminFoodItemViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage, foodOption;
        TextView foodName;

        public AdminFoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageItem_admin);
            foodName = itemView.findViewById(R.id.foodName_item_admin);
            foodOption = itemView.findViewById(R.id.foodOption);
        }
    }
    public void showPopup(View v, FoodModel food) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.food_option_admin);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_food:
                        clickAdminFoodOptionListener.onDeleteClick(food);
                        return true;
//                    case R.id.edit_food:
//                        clickAdminFoodOptionListener.onEditClick(food);
//                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
}