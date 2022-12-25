package com.example.foodapp.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Enum.Option;
import com.example.foodapp.Iterface.SQliteInterface.IClickHistoryFindingListener;
import com.example.foodapp.R;

import java.util.List;

public class HistoryFindingAdapter extends BaseAdapter {

    Context context;
    List<String> list;
    IClickHistoryFindingListener mListener;

    public HistoryFindingAdapter(Context context, List<String> list, IClickHistoryFindingListener mListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
    }
    public void setData(List<String>list)
    {
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list!=null)
        {
            return list.size();
        }
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.keyword_item, null);
        TextView textView = convertView.findViewById(R.id.keyword_text);
        textView.setText(list.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(list.get(position));
            }
        });
        return convertView;
    }

}
