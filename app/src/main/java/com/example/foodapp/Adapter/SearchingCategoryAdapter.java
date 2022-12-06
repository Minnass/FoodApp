package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;

import java.util.List;

public class SearchingCategoryAdapter extends BaseAdapter {
    List<String> keyWordList;
    Context context;

    public SearchingCategoryAdapter(List<String> keyWordList, Context context) {
        this.keyWordList = keyWordList;
        this.context = context;
    }

    public void setData(List<String> list) {
        this.keyWordList = list;
    }

    @Override
    public int getCount() {
        if (keyWordList != null) {
            return keyWordList.size();
        }
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
        String keyword=keyWordList.get(position);
        TextView textView =convertView.findViewById(R.id.keyword_text);
        textView.setText(keyword);
        return convertView;
    }
}
