package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;

import java.util.List;

public class SearchingCategoryAdapter extends  RecyclerView.Adapter<SearchingCategoryAdapter.SearchingKeyViewHolder>{
    List<String> keyWordList;
    Context context;

    public SearchingCategoryAdapter(List<String> keyWordList, Context context) {
        this.keyWordList = keyWordList;
        this.context = context;
    }
    public void setData(List<String>list)
    {
        this.keyWordList=list;
    }
    @NonNull
    @Override
    public SearchingKeyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_item, parent, false);
        return new SearchingKeyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchingKeyViewHolder holder, int position) {
            String keyWord=keyWordList.get(position);
            if(keyWord!=null)
            {
                holder.keyWord.setText(keyWord);
            }
    }

    @Override
    public int getItemCount() {
        if(keyWordList!=null)
        {
            return keyWordList.size();
        }
        return 0;
    }

    public class SearchingKeyViewHolder extends RecyclerView.ViewHolder
    {
        TextView keyWord;
        public SearchingKeyViewHolder(@NonNull View itemView) {
            super(itemView);
            keyWord=itemView.findViewById(R.id.keyword_text);
        }
    }
}
