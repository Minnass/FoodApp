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

public class SearchingCategoryAdapter extends BaseAdapter {

    Context context;
    String type;
    int checkedPostion;


    public SearchingCategoryAdapter(Context context, String type) {
        this.context = context;
        this.type = type;
        checkedPostion = -1;
    }


    @Override
    public int getCount() {
        if (type.equals("Categories")) {
            return Categories.values().length;
        } else {
            return Option.values().length;
        }
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
        String keyword;
        if (type.equals("Categories")) {
            keyword = Categories.values()[position].toString();
        } else {
            keyword = Option.values()[position].toString();
        }
        TextView textView = convertView.findViewById(R.id.keyword_text);
        textView.setText(keyword);

        if (checkedPostion == position) {
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkedPostion = position;
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public Object getCheckedKeyWord()
    {
        if(checkedPostion!=-1)
        {
            if(type.equals("Categories"))
            {
                return Categories.values()[checkedPostion];
            }
            else
            {
                return Option.values()[checkedPostion];
            }
        }
        return null;
    }
    public void resetPositionChecked()
    {
        checkedPostion=-1;
        notifyDataSetChanged();
    }
}
