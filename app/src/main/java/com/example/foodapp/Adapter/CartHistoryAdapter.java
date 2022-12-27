package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Iterface.ICLickHistoryCartItemListener;
import com.example.foodapp.Model.CartHistoryItemModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.text.SimpleDateFormat;
import java.util.List;

public class CartHistoryAdapter extends RecyclerView.Adapter<CartHistoryAdapter.HisttoryViewHolder> {
    List<CartHistoryItemModel> cartHistoryList;
    Context context;
    ICLickHistoryCartItemListener mListener;

    public CartHistoryAdapter(List<CartHistoryItemModel> cartHistoryList, Context context, ICLickHistoryCartItemListener mListener) {
        this.cartHistoryList = cartHistoryList;
        this.context = context;
        this.mListener = mListener;
    }

    public void setData(List<CartHistoryItemModel> list) {
        this.cartHistoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HisttoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_history_item, parent, false);
        return new HisttoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HisttoryViewHolder holder, int position) {
        CartHistoryItemModel item = cartHistoryList.get(position);
        if (item == null) {
            return;
        }
        holder.orderCode.setText(item.getOrderCode());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        holder.orderDate.setText(simpleDateFormat.format(item.getOrderDate()));
        float _totalPrice=item.getTotalPrice()+item.getDeliveryFee()-item.getSaleCode();
        holder.totalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_totalPrice));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartHistoryList != null) {
            return cartHistoryList.size();
        }
        return 0;
    }

    public class HisttoryViewHolder extends RecyclerView.ViewHolder {
        TextView orderDate, totalPrice, orderCode;

        public HisttoryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.order_dateTime_item);
            totalPrice = itemView.findViewById(R.id.totalPrice_cartHistory_item);
            orderCode = itemView.findViewById(R.id.cartCode_cartHistoryItem);
        }
    }
}
