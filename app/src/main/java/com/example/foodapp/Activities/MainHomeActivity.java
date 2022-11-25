package com.example.foodapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.PhotoViewPager2Adapter;
import com.example.foodapp.Adapter.PopularAdapter;
import com.example.foodapp.Model.CategoryModel;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.ItemCartModel;
import com.example.foodapp.Model.Photo;
import com.example.foodapp.R;
import com.example.foodapp.Util.TranslateAnimationUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
public class MainHomeActivity extends AppCompatActivity {
    public static  List<ItemCartModel>  selectedItemList;
    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton mFloatingActionButton;
    private NestedScrollView mNestedScrollView;
    private ViewPager2 mViewpager2;
    private CircleIndicator3 mCircleIndicator3;
    private RecyclerView mRecyleviewCategory;
    private  RecyclerView mRecyleviewPopular;
    List<FoodModel> mFoodList;
    List<Photo> mPhotoList;
    List<CategoryModel>mCategoryList;
    private Handler mHandler ;
    private Runnable mRunnable ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedItemList=new ArrayList<>();
        setContentView(R.layout.activity_main_home);
        initImageSlider();
        initBottomNavigaion();
        initNestedScrollView();
        initCategory();
        initPolularFood();
        initFloatingActionBtn();

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
                mHandler.postDelayed(mRunnable,5000);
            }
        });
            mHandler =new Handler();
        mRunnable= new Runnable() {
            @Override
            public void run() {
                if(mViewpager2.getCurrentItem()==mPhotoList.size()-1)
                {
                    mViewpager2.setCurrentItem(0);
                }
                else
                {
                    mViewpager2.setCurrentItem(mViewpager2.getCurrentItem()+1);
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

    void initPolularFood()
    {
        mRecyleviewPopular =findViewById(R.id.rcv_popular);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mRecyleviewPopular.setLayoutManager(linearLayoutManager);
        mFoodList=new ArrayList<>();
        mFoodList.add (new FoodModel(R.drawable.fruit,"Trái cây",5.45f));
        mFoodList.add (new FoodModel(R.drawable.pizza,"Pizza",10f));
        mFoodList.add (new FoodModel(R.drawable.soda,"Cafe",9.6f));
        mFoodList.add (new FoodModel(R.drawable.cake,"Bánh ngọt",7f));
        mFoodList.add (new FoodModel(R.drawable.hamburger,"Hamburger",5.34f));
        PopularAdapter popularAdapter=new PopularAdapter(mFoodList,this);
        mRecyleviewPopular.setAdapter(popularAdapter);
    }


    private  void initCategory()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mRecyleviewCategory = findViewById(R.id.rcv_Category);
        mRecyleviewCategory.setLayoutManager(linearLayoutManager);
        mCategoryList =new ArrayList<>();
        mCategoryList.add(new CategoryModel("Pizza",R.drawable.pizza));
        mCategoryList.add(new CategoryModel("Burger",R.drawable.hamburger));
        mCategoryList.add(new CategoryModel("Hotdog", R.drawable.hotdog));
        mCategoryList.add(new CategoryModel("Drink",R.drawable.soda));
        mCategoryList.add(new CategoryModel("Cake",R.drawable.cake));
        mCategoryList.add(new CategoryModel("Fruit",R.drawable.fruit));
        CategoryAdapter categoryAdapter =new CategoryAdapter(mCategoryList,this);
        mRecyleviewCategory.setAdapter(categoryAdapter);
    }
    void initBottomNavigaion()
    {
        mBottomNavigationView =findViewById(R.id.bottom_nvg);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.profile_nvg:
                    {

                        break;
                    }
                    case R.id.support_nvg:
                    {
                        Toast.makeText(MainHomeActivity.this,"Support",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.setting_nvg:
                    {
                        Toast.makeText(MainHomeActivity.this,"Setting",Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                return true;
            }
        });
    }
    void initNestedScrollView()
    {
        mNestedScrollView=findViewById(R.id.scroll_mainhome);
        mNestedScrollView.setOnTouchListener(new TranslateAnimationUtil(this,mFloatingActionButton));
    }
    void initFloatingActionBtn()
    {
        mFloatingActionButton =findViewById(R.id.floatactionbtn_home);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainHomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

}