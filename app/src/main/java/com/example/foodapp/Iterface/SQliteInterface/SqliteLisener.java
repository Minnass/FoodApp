package com.example.foodapp.Iterface.SQliteInterface;

import com.example.foodapp.Model.SQLiteModel.ItemCartModel;

import java.util.List;

public interface SqliteLisener {
    public  void updateQuantity(ItemCartModel item,int newQuantity);

    public  void deleteItems(List<ItemCartModel> items);
}
