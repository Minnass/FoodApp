package com.example.foodapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodapp.Activities.DetailFoodActivity;
import com.example.foodapp.Adapter.SearchedItemAdapter;
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
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Util.NotificationDialog;
import com.example.foodapp.Util.TranslateAnimationUtil;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodCategoryFragment extends Fragment {

    private static Context context;
    private static final String TYPE = "CATEGORY_TYPE";
    private  Categories foodType;

    private CartManagerSqLite cartManagerSqLite;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    FoodAppApi mFoodAppApi;

    private LinearLayout bottomBar;
    private ImageView refresh;
    private TextView totalFood;
    private TextView newOption,hotSaleOption;
    private RecyclerView mRecycleview;
    private List<FoodModel> mListFood;
    private SearchedItemAdapter searchedItemAdapter;


    public FoodCategoryFragment() {
        // Required empty public constructor
    }

    public void SetDataForFragment(List<FoodModel> list) {
        this.mListFood = list;
        searchedItemAdapter.notifyDataSetChanged();
    }


    // TODO: Rename and change types and number of parameters
    public static FoodCategoryFragment newInstance(Categories categories) {
        FoodCategoryFragment fragment = new FoodCategoryFragment();
        if (categories != null) {
            Bundle args = new Bundle();
            args.putSerializable(TYPE,categories);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
            cartManagerSqLite = new CartManagerSqLite(context);
        } catch (Exception exception) {
        }
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            foodType= (Categories) bundle.getSerializable(TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout viewLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_food_category, null);
        mRecycleview = viewLayout.findViewById(R.id.foodCategory);
        totalFood = viewLayout.findViewById(R.id.totalFoodCategoryActivity);
        hotSaleOption=viewLayout.findViewById(R.id.hotSaleOption);
        newOption=viewLayout.findViewById(R.id.newOption);
        refresh=viewLayout.findViewById(R.id.refresh);
        bottomBar=viewLayout.findViewById(R.id.bottom_bar);
        initFoodList();
        handleClick();
        return viewLayout;
    }
    void initFoodList()
    {
        searchedItemAdapter = new SearchedItemAdapter(mListFood, context, new IClickFoodItemListener() {
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
        mRecycleview.setAdapter(searchedItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        mRecycleview.setLayoutManager(linearLayoutManager);
        mRecycleview.setOnTouchListener(new TranslateAnimationUtil(context,bottomBar));
        setData(foodType);
    }

    void setData(Categories categories) {
        String keyWord;
        switch (categories)
        {
            case NOODLE:
                keyWord=Categories.NOODLE.toString();
                break;
            case FASTFOOD:
                keyWord=Categories.FASTFOOD.toString();
                break;
            case  BEVERAGE:
                keyWord=Categories.BEVERAGE.toString();
                break;

            case  RICE:
                keyWord=Categories.RICE.toString();
                break;
            case  MEATANDFISH:
                keyWord=Categories.MEATANDFISH.toString();
                break;
            case DESSERT:
                keyWord=Categories.DESSERT.toString();
                break;
            default:
                keyWord="";
        }
        compositeDisposable.add(mFoodAppApi.getCategories(keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                       allFood-> {
                            if (allFood != null) {
                                searchedItemAdapter.setData(allFood);
                                totalFood.setText(allFood.size()+"");
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );


    }

    void handleClick()
    {
        hotSaleOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotSaleOption.setTextColor(Color.RED);
                newOption.setTextColor(Color.BLACK);
                compositeDisposable.add(mFoodAppApi.filterFood("",foodType.toString() , "Bán chạy")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                foodsReturned -> {
                                    if (foodsReturned != null) {
                                        searchedItemAdapter.setData(foodsReturned);
                                        totalFood.setText(foodsReturned.size()+"");
                                    }
                                },
                                error ->
                                {
                                    Log.d("Loi", error.getMessage());
                                }
                        )
                );
            }
        });
        newOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotSaleOption.setTextColor(Color.BLACK);
                newOption.setTextColor(Color.RED);
                compositeDisposable.add(mFoodAppApi.filterFood("",foodType.toString() , "Mới nhất")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                foodsReturned -> {
                                    if (foodsReturned != null) {
                                        searchedItemAdapter.setData(foodsReturned);
                                        totalFood.setText(foodsReturned.size()+"");
                                    }
                                },
                                error ->
                                {
                                    Log.d("Loi", error.getMessage());
                                }
                        )
                );
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotSaleOption.setTextColor(Color.BLACK);
                newOption.setTextColor(Color.BLACK);
                compositeDisposable.add(mFoodAppApi.getCategories(foodType.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                allFood-> {
                                    if (allFood != null) {
                                        searchedItemAdapter.setData(allFood);
                                        totalFood.setText(allFood.size()+"");
                                    }
                                },
                                error ->
                                {
                                    Log.d("Loi", error.getMessage());
                                }
                        )
                );
            }
        });
    }

}