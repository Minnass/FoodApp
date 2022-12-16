package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Iterface.Address.IClickAddressSettingListener;
import com.example.foodapp.Model.SQLiteModel.AddressModel;
import com.example.foodapp.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    List<AddressModel> addressList;
    Context context;
    IClickAddressSettingListener mListener;

    public AddressAdapter(List<AddressModel> addressList, Context context, IClickAddressSettingListener mListener) {
        this.addressList = addressList;
        this.context = context;
        this.mListener = mListener;
    }
    void setData(List<AddressModel> list)
    {
        this.addressList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel address=addressList.get(position);
        if(address==null)
        {
            return;
        }
        holder.userName.setText(address.getName());
        holder.address.setText(address.getAddress());
        holder.phoneNumber.setText(address.getPhone());
        holder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(holder.getAdapterPosition());
            }
        });
        if(position==0)
        {
            holder.defaultAddress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if(addressList!=null)
        {
            return addressList.size();
        }
        return 0;
    }


    public class AddressViewHolder extends  RecyclerView.ViewHolder{
        TextView userName,phoneNumber,setting,address,defaultAddress;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName_itemaddress);
            phoneNumber=itemView.findViewById(R.id.phoneNumber_itemaddress);
            address=itemView.findViewById(R.id.address_addressitem);
            setting=itemView.findViewById(R.id.address_addressitem);
            defaultAddress=itemView.findViewById(R.id.defaultAddress);
        }
    }
}
