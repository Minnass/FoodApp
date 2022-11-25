package com.example.foodapp.Adapter;//package com.example.foodorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Iterface.ChangeItemNumber;
import com.example.foodapp.Model.ItemCartModel;
import com.example.foodapp.R;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder>
{
    List<ItemCartModel> mItemCartModel;
    Context context;
    ChangeItemNumber mChangeItemNumber;

    public CartListAdapter(List<ItemCartModel> mItemCartModel, Context context,ChangeItemNumber changeItemNumber) {
        this.mItemCartModel = mItemCartModel;
        this.context = context;
        this.mChangeItemNumber=changeItemNumber;
    }
    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_food,parent,false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
            ItemCartModel itemCartModel=mItemCartModel.get(position);
            if(itemCartModel==null)
            {
                return;
            }
            else
            {
                holder.imgFood.setImageResource(itemCartModel.getImg_id());
                holder.tittle.setText(itemCartModel.getNameProduct());
                 holder.itemNumber.setText(String.valueOf(itemCartModel.getQuantity()));
                holder.itemPrice.setText(String.valueOf(itemCartModel.getPrice()));
                holder.totalPrice.setText(String.valueOf(countTotalPrice(itemCartModel)));
            }
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.itemNumber.getText().equals("1"))
                    {
                        mItemCartModel.remove(holder.getAdapterPosition());
                        holder.minus.setEnabled(false);
                        notifyDataSetChanged();
                    }
                    else
                    {
                        int temp =itemCartModel.getQuantity()-1;
                        itemCartModel.setQuantity(temp);
                        holder.itemNumber.setText(String.valueOf(temp));
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                    mChangeItemNumber.change();
                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.minus.setEnabled(true);
                    int temp =itemCartModel.getQuantity()+1;
                    itemCartModel.setQuantity(temp);
                    holder.itemNumber.setText(String.valueOf(temp));
                    notifyItemChanged(holder.getAdapterPosition());
                    mChangeItemNumber.change();
                }
            });
    }

    @Override
    public int getItemCount() {
        if(mItemCartModel ==null)
        {
            return 0;
        }
        else {
            return mItemCartModel.size();
        }
    }
    public class CartListViewHolder extends  RecyclerView.ViewHolder
    {
        ImageView imgFood,plus,minus;
        TextView tittle,itemNumber,itemPrice,totalPrice;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood=itemView.findViewById(R.id.img_itemSelected);
            plus=itemView.findViewById(R.id.plus_item);
            minus=itemView.findViewById(R.id.minus_item);
            tittle=itemView.findViewById(R.id.title_selectedFood);
            itemNumber=itemView.findViewById(R.id.item_number);
            itemPrice=itemView.findViewById(R.id.itemPrice_itemSelected_food);
            totalPrice=itemView.findViewById(R.id.totalPrice_itemSelected);
        }
    }
    float  countTotalPrice( ItemCartModel itemCartModel)
    {
        float result = (float) (Math.round(itemCartModel.getPrice()* itemCartModel.getQuantity() * 100.0) / 100.0);
        return result;
    }
}