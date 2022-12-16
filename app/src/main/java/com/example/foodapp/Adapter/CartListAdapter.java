package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Activities.CartActivity;
import com.example.foodapp.Iterface.SQliteInterface.ISqliteLisener;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {

    List<ItemCartModel> mfoodList;
    Context context;
    ISqliteLisener mILisener;

    public CartListAdapter(List<ItemCartModel> mfoodList, Context context, ISqliteLisener mILisener) {
        this.mfoodList = mfoodList;
        this.context = context;
        this.mILisener = mILisener;
    }

    public void setData(List<ItemCartModel> list) {
        this.mfoodList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        ItemCartModel itemCartModel = mfoodList.get(position);
        if (itemCartModel == null) {
            return;
        }
        Glide.with(context).load(itemCartModel.getImage()).into(holder.foodImg);
        holder.foodName.setText(itemCartModel.getFoodName());
        if(itemCartModel.getDiscount()==0)
        {
            holder.discount.setVisibility(View.GONE);
            holder.originalPrice.setVisibility(View.GONE);
        }
        float currentPrice = itemCartModel.getPrice() * (1 - (float)itemCartModel.getDiscount() / 100);
        holder.currentPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(currentPrice));
        holder.originalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(itemCartModel.getPrice()));
        holder.discount.setText("Giảm "+String.valueOf(itemCartModel.getDiscount())+"%");
        holder.quantity.setText(String.valueOf(itemCartModel.getQuantity()));
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current=itemCartModel.getQuantity();
                int next=current+1;
                itemCartModel.setQuantity(next);
                holder.quantity.setText(String.valueOf(next));
               mILisener.updateQuantity(itemCartModel,next);
            }
        });
        if(itemCartModel.isSelected())
        {
            holder.selection.setChecked(true);
        }
        else
        {
            holder.selection.setChecked(false);
        }
        holder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    itemCartModel.setSelected(true);
                }
                else
                {
                    itemCartModel.setSelected(false);
                }

            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current=itemCartModel.getQuantity();
                if(current==1)
                {
                    return;
                }
                int previous=current-1;
                itemCartModel.setQuantity(previous);
                holder.quantity.setText(String.valueOf(previous));
                mILisener.updateQuantity(itemCartModel,previous);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mfoodList != null) {
            return mfoodList.size();
        }
        return 0;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImg;
        TextView foodName, currentPrice, originalPrice, quantity;
        ImageView decrease, increase;
        TextView discount;
        CheckBox selection;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.foodimgCartItem);
            foodName = itemView.findViewById(R.id.foodNameItemCart);
            currentPrice = itemView.findViewById(R.id.currentPriceItemCart);
            originalPrice = itemView.findViewById(R.id.originalPrice_ItemCart);
            quantity = itemView.findViewById(R.id.quantityItemCart);
            decrease = itemView.findViewById(R.id.decrease);
            increase = itemView.findViewById(R.id.increase);
            discount = itemView.findViewById(R.id.discount_text_ItemCartActivity);
            selection=itemView.findViewById(R.id.selected_CartIem);
        }
    }
}
