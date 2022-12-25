package com.example.foodapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.PhotoViewPager2Adapter;
import com.example.foodapp.Adapter.PopularAdapter;
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Fragment.DefaultSearchingFragment;
import com.example.foodapp.Fragment.FavoriteFragment;
import com.example.foodapp.Fragment.HomeFragment;
import com.example.foodapp.Fragment.ProfileFragment;
import com.example.foodapp.Fragment.ReturnedFoodsFragment;
import com.example.foodapp.Iterface.IClickFavoriteListener;
import com.example.foodapp.Iterface.IClickFoodCategoryListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.CategoryModel;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.LoginModel.UserModel;
import com.example.foodapp.Model.Photo;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.SQLite.FavoriteFoodManagerSqLite;
import com.example.foodapp.Util.GridSpacingItemDecoration;
import com.example.foodapp.Util.TranslateAnimationUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.foodapp.Util.InternetConnection;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator3;

public class MainHomeActivity extends AppCompatActivity {
    public static UserModel user;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private FavoriteFragment favoriteFragment;
    private ProfileFragment profileFragment;
    private BottomNavigationView mBottomNavigationView;
    private NestedScrollView mNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getUserFromIntent();
        ft = getSupportFragmentManager().beginTransaction();
        homeFragment = HomeFragment.newInstance();
        favoriteFragment = FavoriteFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, homeFragment);
        ft.commit();
        initBottomNavigaion();
    }

    void initBottomNavigaion() {
        mBottomNavigationView = findViewById(R.id.bottom_nvg);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nvg: {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, homeFragment);
                        ft.commit();
                        break;
                    }
                    case R.id.cart_nvg: {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, homeFragment);
                        ft.commit();
                        break;
                    }
                    case R.id.favorite_nvg: {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, favoriteFragment);
                        ft.commit();
                        break;
                    }
                    case R.id.profile_nvg: {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, profileFragment);
                        ft.commit();
                        break;
                    }
                }
                return true;
            }
        });
}
    void getUserFromIntent() {
        Bundle bundle = getIntent().getExtras();
        user = (UserModel) bundle.getSerializable("user");
    }

}