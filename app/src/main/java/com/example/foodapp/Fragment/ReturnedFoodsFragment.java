package com.example.foodapp.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.SearchedItemAdapter;
import com.example.foodapp.Adapter.SearchingCategoryAdapter;
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
    Dialog filterDialog;

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
        } else {
            totalResult.setText("(0 kết quả)");
        }
        initFilterDialog();
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.show();
            }
        });
        return view_layout_returnedFood;
    }

    void initReturnedFoodListRCV() {
        adapter = new SearchedItemAdapter(foodList, context);
        returnedFoodListRCV.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        returnedFoodListRCV.setLayoutManager(linearLayoutManager);
    }

    void initFilterDialog() {
        filterDialog = new Dialog(context);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.filter_dialog);
        Window window = filterDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.RIGHT;
        window.setAttributes(windowAttributes);
        filterDialog.setCancelable(false);
        TextView btnFilderDelete = filterDialog.findViewById(R.id.btnFilterDelete);
        TextView btnCloseFilter=filterDialog.findViewById(R.id.btnCloseDialog);
        GridView categoryGridView=filterDialog.findViewById(R.id.category_filter);
        GridView typeFilterGridView=filterDialog.findViewById(R.id.typeFilter);
        List<String> temp1=new ArrayList<>();
        List<String> temp2=new ArrayList<>();
        temp1.add("Cá");
        temp1.add("Thịt");
        temp1.add("Thịt");
        temp1.add("Thịt");
        temp1.add("Thịt");
        temp1.add("Thịt");
        temp1.add("Thịt");
        temp2.add("Mới nhất");
        temp2.add("Bán chạy");
        SearchingCategoryAdapter searchingCategoryAdapter1=new SearchingCategoryAdapter(temp1,context);
        SearchingCategoryAdapter searchingCategoryAdapter2=new SearchingCategoryAdapter(temp2,context);
        categoryGridView.setAdapter(searchingCategoryAdapter1);
        typeFilterGridView.setAdapter(searchingCategoryAdapter2);
        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged(); // call it here
                if(categoryGridView.isItemChecked(position)) {
                    view = categoryGridView.getChildAt(position);
                    view.setBackgroundColor(Color.DKGRAY);
                }else {
                    view = categoryGridView.getChildAt(position);
                    view.setBackgroundColor(Color.RED); //the color code is the background color of GridView
                }
                Toast.makeText(findItemActivity, temp1.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        typeFilterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(findItemActivity,temp2.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        btnFilderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(findItemActivity, "xoa", Toast.LENGTH_SHORT).show();
            }
        });
        btnCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(findItemActivity, "ok", Toast.LENGTH_SHORT).show();
                filterDialog.dismiss();
            }
        });
    }

    void implementFilterClick() {
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
