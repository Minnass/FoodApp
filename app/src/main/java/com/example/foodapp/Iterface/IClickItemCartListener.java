package com.example.foodapp.Iterface;

import com.example.foodapp.Adapter.PaymentAdapter;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;

public interface IClickItemCartListener {
    public void onItemClick(ItemCartModel item, PaymentAdapter.PaymentViewHolder viewHolder);
}
