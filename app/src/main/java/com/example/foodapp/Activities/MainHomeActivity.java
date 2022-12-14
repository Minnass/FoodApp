package com.example.foodapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.PhotoViewPager2Adapter;
import com.example.foodapp.Adapter.PopularAdapter;
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Iterface.IClickFavoriteListener;
import com.example.foodapp.Iterface.IClickFoodCategoryListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.CategoryModel;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.Photo;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.Util.GridSpacingItemDecoration;
import com.example.foodapp.Util.TranslateAnimationUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.foodapp.Util.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator3;

public class MainHomeActivity extends AppCompatActivity {

    private EditText searchingItemEdit;
    private List<FoodModel> searchedItems;

    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton mFloatingActionButton;
    private NestedScrollView mNestedScrollView;
    private ViewPager2 mViewpager2;
    private CircleIndicator3 mCircleIndicator3;
    private RecyclerView mRecyleviewCategory;
    private CategoryAdapter categoryAdapter;

    private RecyclerView mRecyleviewPopular;
    private PopularAdapter mPopularAdapter;
    private List<FoodModel> mPopularFoodList;
    private RecyclerView mRecycleviewChoice;
    private FoodListAdapter mFoodListAdapter;
    private List<FoodModel> mFoodList;

    private List<Photo> mPhotoList;

    private Handler mHandler;
    private Runnable mRunnable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        searchingItemEdit = findViewById(R.id.seach_item);
        setContentView(R.layout.activity_main_home);
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        initImageSlider();
        initBottomNavigaion();
        initNestedScrollView();
        initCategory();
        initPolularFood();
        initFloatingActionBtn();
        initYourchoice();

    }


    void initImageSlider() {
        mViewpager2 = findViewById(R.id.view_paper2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator3);
        mPhotoList = new ArrayList<Photo>();
        mPhotoList.add(new Photo(R.drawable.anh01));
        mPhotoList.add(new Photo(R.drawable.anh02));
        mPhotoList.add(new Photo(R.drawable.anh03));
        PhotoViewPager2Adapter photoViewPager2Adapter = new PhotoViewPager2Adapter(mPhotoList);
        mViewpager2.setAdapter(photoViewPager2Adapter);
        mCircleIndicator3.setViewPager(mViewpager2);
        mViewpager2.setPageTransformer(new CompositePageTransformer());
        mViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 5000);
            }
        });
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mViewpager2.getCurrentItem() == mPhotoList.size() - 1) {
                    mViewpager2.setCurrentItem(0);
                } else {
                    mViewpager2.setCurrentItem(mViewpager2.getCurrentItem() + 1);
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 5000);
    }


    private void initCategory() {
        mRecyleviewCategory = findViewById(R.id.rcv_Category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyleviewCategory.setLayoutManager(gridLayoutManager);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(4, 20, false);
        mRecyleviewCategory.addItemDecoration(gridSpacingItemDecoration);

        categoryAdapter = new CategoryAdapter(this, new IClickFoodCategoryListener() {
            @Override
            public void onItemFoodCategoryHandler(Categories category) {
                Intent intent = new Intent(MainHomeActivity.this, FoodCatetoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyleviewCategory.setAdapter(categoryAdapter);
    }

    private void initYourchoice() {

        mRecycleviewChoice = findViewById(R.id.rcv_yourChoice);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecycleviewChoice.setLayoutManager(gridLayoutManager);
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(2, 40, false);
        mRecycleviewChoice.addItemDecoration(itemDecoration);
        mFoodListAdapter = new FoodListAdapter(this, mFoodList, new IClickFoodItemListener() {
            @Override
            public void onItemClickHandler(FoodModel food) {
                Intent intent = new Intent(MainHomeActivity.this, DetailFoodActivity.class);
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
        });
        mRecycleviewChoice.setAdapter(mFoodListAdapter);
        getAllFood();
    }

    void getAllFood() {
        compositeDisposable.add(mFoodAppApi.getAllFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFood -> {
                            if (allFood.size() > 0) {
                                mFoodListAdapter.setData(allFood);
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }


    void initBottomNavigaion() {
        mBottomNavigationView = findViewById(R.id.bottom_nvg);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile_nvg: {

                        break;
                    }
                    case R.id.support_nvg: {
                        Toast.makeText(MainHomeActivity.this, "Support", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.setting_nvg: {
                        Toast.makeText(MainHomeActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                return true;
            }
        });
    }

    void initNestedScrollView() {
        mNestedScrollView = findViewById(R.id.scroll_mainhome);
        mNestedScrollView.setOnTouchListener(new TranslateAnimationUtil(this, mFloatingActionButton));

    }


    void initPolularFood() {
        mRecyleviewPopular = findViewById(R.id.rcv_popular);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyleviewPopular.setLayoutManager(linearLayoutManager);
        mPopularAdapter = new PopularAdapter(mPopularFoodList, this, new IClickFoodItemListener() {
            @Override
            public void onItemClickHandler(FoodModel food) {
                Intent intent = new Intent(MainHomeActivity.this, DetailFoodActivity.class);
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
        }, new IClickFavoriteListener() {
            @Override
            public void onItemClickHandler(FoodModel foodModel) {
                //add vao gio hang yeu thich
            }
        });
        mRecyleviewPopular.setAdapter(mPopularAdapter);
        getPopularFood();
    }

    void getPopularFood() {
        compositeDisposable.add(mFoodAppApi.getTopPopularFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFood -> {
                            if (allFood.size() > 0) {
                                mPopularAdapter.setData(allFood);
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    void initFloatingActionBtn() {
        mFloatingActionButton = findViewById(R.id.floatactionbtn_home);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainHomeActivity.this, CartActivity.class);
//                startActivity(intent);
            }
        });
    }

}