package com.example.foodapp.Fragment;

import android.content.Context;
import android.icu.number.CompactNotation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.SearchingCategoryAdapter;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.List;

public class DefaultSearchingFragment extends Fragment {
    private FindItemActivity findItemActivity;
    private Context context;
    private TextView oldSearchingDelete;
    private RecyclerView oldSearchingRcv, categorySearchingRcv;
    private SearchingCategoryAdapter categorySearchingAdapter, oldSearchingAdapter;
    private List<String> oldSearchingReslut;
    private List<String> categorySearching;

    public static DefaultSearchingFragment newInstance() {
        DefaultSearchingFragment fragment = new DefaultSearchingFragment();
        return fragment;
    }

    public void setOldSeachingData(List<String> list) {
        oldSearchingReslut=list;
        oldSearchingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context=getActivity();
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
        oldSearchingRcv=view_layout_defaultSeaching.findViewById(R.id.oldSearchingRcv);
        categorySearchingRcv=view_layout_defaultSeaching.findViewById(R.id.categorySearchingRcv);
        initFoodListRecycleView();
        return  view_layout_defaultSeaching;
    }

    void implementFilterClick() {

    }

    void initFoodListRecycleView() {
        oldSearchingReslut = new ArrayList<>();
        categorySearching = new ArrayList<>();
        oldSearchingAdapter = new SearchingCategoryAdapter(oldSearchingReslut, context);
        categorySearchingAdapter = new SearchingCategoryAdapter(categorySearching, context);
        oldSearchingRcv.setAdapter(oldSearchingAdapter);
        categorySearchingRcv.setAdapter(categorySearchingAdapter);
    }
}
