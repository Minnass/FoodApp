package com.example.foodapp.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activities.DetailFoodActivity;
import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.SearchedItemAdapter;
import com.example.foodapp.Adapter.SearchingCategoryAdapter;
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Enum.Option;
import com.example.foodapp.Iterface.IClickAddItemListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReturnedFoodsFragment extends Fragment {
    private FindItemActivity findItemActivity;
    Context context = null;
    private TextView totalResult;
    private ImageView filter;
    private RecyclerView returnedFoodListRCV;
    private SearchedItemAdapter adapter;
    private List<FoodModel> foodList;
    Dialog filterDialog;

    private  String searchedKeyword;

    private CartManagerSqLite cartManagerSqLite;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);

    public static ReturnedFoodsFragment newInstance() {
        ReturnedFoodsFragment fragment = new ReturnedFoodsFragment();
        return fragment;
    }

    public void setData(List<FoodModel> list) {
        this.foodList = list;
        adapter.notifyDataSetChanged();
    }

    public  void setKeyWord(String keyWord)
    {
        searchedKeyword = keyWord;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            findItemActivity = (FindItemActivity) getActivity();
            cartManagerSqLite = new CartManagerSqLite(context);
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
        adapter = new SearchedItemAdapter(foodList, context, new IClickFoodItemListener() {
            @Override
            public void onItemClickHandler(FoodModel food) {
                Intent intent = new Intent(context, DetailFoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("foodname", food.getName().toString());
                bundle.putString("image", food.getImage().toString());
                bundle.putString("description", food.getDescription());
                bundle.putString("originalprice", String.valueOf(food.getPrice()));
                bundle.putInt("sale", food.getDiscount());
                bundle.putInt("quantitySold", food.getQuantity());
                float currentPrice = food.getPrice() * (1 - (float) food.getDiscount() / 100);
                bundle.putString("currentprice", String.valueOf(currentPrice));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, new IClickAddItemListener() {
            @Override
            public void onClick(FoodModel food) {
                cartManagerSqLite.addCart(new ItemCartModel(food.getName(), 1
                        , (int) food.getPrice(), food.getDiscount()
                        , food.getImage()));
                NotificationDialog notificationDialog=new NotificationDialog(context);
                notificationDialog.setContent("Đã thêm vào giỏ hàng");
                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                notificationDialog.show();
            }
        });
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
        filterDialog.setCancelable(true);
        TextView btnFilderDelete = filterDialog.findViewById(R.id.btnFilterDelete);
        TextView btnCloseFilter = filterDialog.findViewById(R.id.btnCloseDialog);
        GridView categoryGridView = filterDialog.findViewById(R.id.category_filter);
        GridView typeFilterGridView = filterDialog.findViewById(R.id.typeFilter);

        SearchingCategoryAdapter searchingCategoryAdapter1 = new SearchingCategoryAdapter(context, "Categories");
        SearchingCategoryAdapter searchingCategoryAdapter2 = new SearchingCategoryAdapter(context, "Option");
        categoryGridView.setAdapter(searchingCategoryAdapter1);
        typeFilterGridView.setAdapter(searchingCategoryAdapter2);

        btnFilderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchingCategoryAdapter1.resetPositionChecked();
                searchingCategoryAdapter2.resetPositionChecked();
            }
        });
        btnCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterData(searchedKeyword,(Categories) searchingCategoryAdapter1.getCheckedKeyWord(),(Option) searchingCategoryAdapter2.getCheckedKeyWord());
                filterDialog.dismiss();
            }
        });
    }

    void FilterData(String firstKeyword, Categories category, Option option) {
        String categoryKeyword = (category != null) ? category.toString() : "null";
        String optionKeyword = (option != null) ? option.toString() : "null";

        if (categoryKeyword.equals("null") && optionKeyword.equals("null")) {
            searchFoodName();

        }
        else
        {
            compositeDisposable.add(mFoodAppApi.filterFood(firstKeyword, categoryKeyword, optionKeyword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            foodsReturned -> {
                                if (foodsReturned != null) {
                                    adapter.setData(foodsReturned);
                                    totalResult.setText("(" + String.valueOf(foodsReturned.size()) + " kết quả)");
                                }
                            },
                            error ->
                            {
                                Log.d("Loi", error.getMessage());
                            }
                    )
            );
        }


    }
    void searchFoodName() {

        compositeDisposable.add(mFoodAppApi.searchFood(searchedKeyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodsReturned -> {
                            if( foodsReturned!=null)
                            {
                                adapter.setData(foodsReturned);
                                totalResult.setText("(" + String.valueOf(foodsReturned.size()) + " kết quả)");
                            }
                        },
                        error->
                        {
                            Log.d("Loi",error.getMessage());
                        }
                )
        );
    }
}
