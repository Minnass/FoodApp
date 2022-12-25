package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Model.DeliveryModel;
import com.example.foodapp.R;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {
    int checkedPostion;
    List<DeliveryModel> DeliveryList;
    Context context;

    public DeliveryAdapter(List<DeliveryModel> deliveryList, Context context) {
        DeliveryList = deliveryList;
        this.context = context;
        checkedPostion =-1;
    }

    public void setData(List<DeliveryModel> list) {
        this.DeliveryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        DeliveryModel deliveryType = DeliveryList.get(position);
        if (deliveryType == null) {
            return;
        }
        Glide.with(context).load(deliveryType.getDeliveryImage()).into(holder.deliveryImg);
        holder.deliveryName.setText(deliveryType.getDeliveryName());
        holder.description.setText(deliveryType.getDescription());

        if (checkedPostion == -1) {
            holder.checkImg.setVisibility(View.GONE);
        } else {
            if (checkedPostion == holder.getAdapterPosition()) {
                holder.checkImg.setVisibility(View.VISIBLE);
            } else {
                holder.checkImg.setVisibility(View.GONE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedPostion != holder.getAdapterPosition()) {
                    holder.checkImg.setVisibility(View.VISIBLE);
                    int temp=checkedPostion;
                    checkedPostion = holder.getAdapterPosition();
                    notifyItemChanged(temp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
       if (DeliveryList!=null)
       {
           return  DeliveryList.size();
       }
       return 0;
    }

    public class DeliveryViewHolder extends RecyclerView.ViewHolder {
        TextView deliveryName, description;
        ImageView checkImg, deliveryImg;
        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryName = itemView.findViewById(R.id.deliveryName);
            description = itemView.findViewById(R.id.deliveryDescription);
            checkImg = itemView.findViewById(R.id.checkImage);
            deliveryImg = itemView.findViewById(R.id.delivery_image);
        }
    }

    public DeliveryModel getSelectedItem() {
        return DeliveryList.get(checkedPostion);
    }
}
