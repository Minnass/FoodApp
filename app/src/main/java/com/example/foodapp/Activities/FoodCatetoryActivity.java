package com.example.foodapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.Adapter.TabLayoutFragmentAdapter;
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FoodCatetoryActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private TabLayoutFragmentAdapter mTabLayoutFragmentAdapter;

    private EditText searchingbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_catetory);
        mappingID();
        searchingbox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    Intent intent=new Intent(FoodCatetoryActivity.this,FindItemActivity.class);
                    startActivity(intent);
                }
            }
        });
        searchingbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FoodCatetoryActivity.this,FindItemActivity.class);
                startActivity(intent);
            }
        });
        mTabLayoutFragmentAdapter = new TabLayoutFragmentAdapter(this, null);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setAdapter(mTabLayoutFragmentAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Phở bún cháo");
                        break;
                    case 1:
                        tab.setText("Fast Food, Đồ nướng");
                        break;
                    case 2:
                        tab.setText("Đồ uống");
                        break;
                    case 3:
                        tab.setText("Cơm");
                        break;
                    case 4:
                        tab.setText("Thịt,Cá");
                        break;
                    case 5:
                        tab.setText("Tráng miệng, Trái cây");
                        break;
                }
            }
        }).attach();
        initFirstTab();
    }

    void initFirstTab() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Categories value = (Categories) bundle.getSerializable("category");
            mViewPager.setCurrentItem(Categories.getIndex(value));
        }
    }

    void mappingID() {
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.view_paper2FoodCategory);
        searchingbox=findViewById(R.id.searchFood_foodCategoryActivity);
    }
}