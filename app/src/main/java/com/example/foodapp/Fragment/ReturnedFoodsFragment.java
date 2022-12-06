package com.example.foodapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.SearchedItemAdapter;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReturnedFoodsFragment extends Fragment {
    private FindItemActivity findItemActivity;
    Context context = null;
    private TextView totalResult;
    private ImageView filter;
    private RecyclerView returnedFoodListRCV;
    private SearchedItemAdapter adapter;
    private List<FoodModel> foodList;

    public static ReturnedFoodsFragment newInstance() {
        ReturnedFoodsFragment fragment = new ReturnedFoodsFragment();
        return fragment;
    }

    public void setData(List<FoodModel> list) {
        this.foodList = list;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            findItemActivity = (FindItemActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view_layout_returnedFood = (LinearLayout) inflater.inflate(R.layout.returned_food, null);
        totalResult = view_layout_returnedFood.findViewById(R.id.totalReturnedFood);
        filter = view_layout_returnedFood.findViewById(R.id.filter);
        returnedFoodListRCV = view_layout_returnedFood.findViewById(R.id.returnedFoodListRcv);
        initReturnedFoodListRCV();
        if (foodList != null) {
            totalResult.setText("(" + String.valueOf(foodList.size()) + " kết quả)");
        }
        else
        {
            totalResult.setText("(0 kết quả)");
        }
        return view_layout_returnedFood;
    }
    void initReturnedFoodListRCV()
    {
        adapter = new SearchedItemAdapter(foodList,context);
        returnedFoodListRCV.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        returnedFoodListRCV.setLayoutManager(linearLayoutManager);
    }

    void implementFilterClick() {

    }


}
