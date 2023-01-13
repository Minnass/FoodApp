package com.example.foodapp.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.List;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.HistoryOrderViewHolder> {
    private Context context;
    List<ItemCartModel> mFoodList;


    public HistoryOrderAdapter(Context context, List<ItemCartModel> mFoodList) {
        this.context = context;
        this.mFoodList = mFoodList;
    }

    public void setData(List<ItemCartModel> foodList) {
        this.mFoodList = foodList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_order_item, parent, false);
        return new HistoryOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryOrderViewHolder holder, int position) {
        ItemCartModel item = mFoodList.get(position);
        if (item == null) {
            return;
        }

        if (item.getImage().contains("http")) {
            Glide.with(context).load(item.getImage()).into(holder.foodImg);
        } else {
            String path= InternetConnection.BASE_URL+"images/"+item.getImage();
            Glide.with(context).load(path).into(holder.foodImg);
        }
        holder.foodName.setText((item.getFoodName()));
        float currentPrice = item.getPrice() * (1 - (float) item.getDiscount() / 100);
        float _totalPrice=currentPrice*item.getQuantity();
        holder.price.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_totalPrice));
        holder.quantity.setText("Số lượng:"+String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (mFoodList != null) {
            return mFoodList.size();
        }
        return 0;
    }

    public class HistoryOrderViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImg;
        TextView foodName, quantity, price;

        public HistoryOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.imageItem_historyOrder_item);
            foodName=itemView.findViewById(R.id.foodName_historyOrder_item);
            quantity=itemView.findViewById(R.id.quantity_historyOrder_item);
            price=itemView.findViewById(R.id.price_historyOrder_item);
        }
    }
}
