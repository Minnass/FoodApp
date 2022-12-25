package com.example.foodapp.Fragment;

import android.content.Context;
import android.icu.number.CompactNotation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.HistoryFindingAdapter;
import com.example.foodapp.Adapter.SearchingCategoryAdapter;
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Iterface.SQliteInterface.IClickHistoryFindingListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.HistoryFindingSqLite;

import java.util.ArrayList;
import java.util.List;

public class DefaultSearchingFragment extends Fragment {
    private FindItemActivity findItemActivity;
    private Context context;
    private TextView oldSearchingDelete;
    private GridView oldSearchingRcv, categorySearchingRcv;

    private List<String> oldFindingList;
    private List<String> categoriesList=new ArrayList<>();
    private HistoryFindingAdapter historyFindingAdapter;
    private HistoryFindingAdapter  categorySearchingAdapter;

    HistoryFindingSqLite historyFindingSqLite;

    public static DefaultSearchingFragment newInstance() {
        DefaultSearchingFragment fragment = new DefaultSearchingFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context=getActivity();
            historyFindingSqLite=new HistoryFindingSqLite(context);
            findItemActivity=(FindItemActivity)getActivity();
        }
        catch (IllegalStateException e)
        {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view_layout_defaultSeaching = (LinearLayout) inflater.inflate(R.layout.default_searching_ui, null);
        oldSearchingDelete=view_layout_defaultSeaching.findViewById(R.id.oldSearchingDelete);
        oldSearchingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldFindingList.clear();
                historyFindingSqLite.deleteALl();
                historyFindingAdapter.notifyDataSetChanged();
            }
        });
        oldSearchingRcv=view_layout_defaultSeaching.findViewById(R.id.oldSearchingRcv);
        categorySearchingRcv=view_layout_defaultSeaching.findViewById(R.id.categorySearchingRcv);

        initFindingList();
        return  view_layout_defaultSeaching;
    }

    void initFindingList()
    {
        historyFindingAdapter=new HistoryFindingAdapter(context, oldFindingList, new IClickHistoryFindingListener() {
            @Override
            public void onClick(String keyword) {
                findItemActivity.searchFoodName(keyword);
            }
        });
        oldSearchingRcv.setAdapter(historyFindingAdapter);
        setOldFindingData();

        categoriesList=new ArrayList<>();
        for(Categories item:Categories.values())
        {
            categoriesList.add(item.toString());
        }

        categorySearchingAdapter=new HistoryFindingAdapter(context, categoriesList, new IClickHistoryFindingListener() {
            @Override
            public void onClick(String keyword) {
                findItemActivity.searchCategory(keyword);
            }
        });
        categorySearchingRcv.setAdapter(categorySearchingAdapter);
    }

    void setOldFindingData()
    {
        oldFindingList=historyFindingSqLite.getAllKeywords();
        historyFindingAdapter.setData(oldFindingList);
    }

}
