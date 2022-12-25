package com.example.foodapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Iterface.IClickItemCartListener;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.ArrayList;
import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    ArrayList<ItemCartModel> foodList;
    Context context;
    IClickItemCartListener mListener;

    public PaymentAdapter(ArrayList<ItemCartModel> foodList, Context context, IClickItemCartListener mListener) {
        this.foodList = foodList;
        this.context = context;
        this.mListener = mListener;
    }

    public void setData(ArrayList<ItemCartModel> list) {
        this.foodList = list;
        Log.d("aa",foodList.size()+"");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        ItemCartModel food = foodList.get(position);
        if (food == null) {
            return;
        }
        Glide.with(context).load(food.getImage()).into(holder.foodImg);
        holder.foodName.setText(food.getFoodName());
        holder.originalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(food.getPrice()));
        float currentPrice = food.getPrice() * (1 - (float) food.getDiscount() / 100);
        holder.sale.setText("Giảm "+food.getDiscount()+"%");
        holder.currentPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(currentPrice));
        holder.notionContainer.setVisibility(View.GONE);
        holder.quantity.setText("Số lượng: " + food.getQuantity());
        float totalPrice = currentPrice * food.getQuantity();
        holder.finalTotalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(totalPrice));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(food, holder);
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

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImg;
        TextView foodName, originalPrice, currentPrice, sale, finalTotalPrice, quantity;
        LinearLayout notionContainer;
        public TextView notion;
        LinearLayout container;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.imageItem_paymentItem);
            foodName = itemView.findViewById(R.id.foodName_itemPayment);
            originalPrice = itemView.findViewById(R.id.originalPrice_paymentItem);
            currentPrice = itemView.findViewById(R.id.currentPrice_paymentItem);
            sale = itemView.findViewById(R.id.sale_paymentItem);
            finalTotalPrice = itemView.findViewById(R.id.totalPrice_itemPayment);
            quantity = itemView.findViewById(R.id.quantity_paymentItem);
            notion = itemView.findViewById(R.id.notion);
            notionContainer = itemView.findViewById(R.id.notion_container);
            container = itemView.findViewById(R.id.ItemContainer_paymentItem);
        }
    }
}
