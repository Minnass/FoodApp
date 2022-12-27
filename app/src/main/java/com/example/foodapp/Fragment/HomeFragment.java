package com.example.foodapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodapp.Activities.DetailFoodActivity;
import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Activities.FoodCatetoryActivity;
import com.example.foodapp.Activities.MainHomeActivity;
import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Adapter.PhotoViewPager2Adapter;
import com.example.foodapp.Adapter.PopularAdapter;
import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Iterface.IClickFavoriteListener;
import com.example.foodapp.Iterface.IClickFoodCategoryListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.Photo;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.FavoriteFoodManagerSqLite;
import com.example.foodapp.Util.GridSpacingItemDecoration;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;
import com.example.foodapp.Util.TranslateAnimationUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    private Context context;
    private MainHomeActivity mainHomeActivity;


    private ViewPager2 mViewpager2;
    private CircleIndicator3 mCircleIndicator3;
    private RecyclerView mRecyleviewCategory;
    private CategoryAdapter categoryAdapter;

    private TextView userName;
    private EditText searchingItemEdit;

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

    FavoriteFoodManagerSqLite favoriteFoodManagerSqLite;


    public HomeFragment() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            mainHomeActivity = (MainHomeActivity) getActivity();
            favoriteFoodManagerSqLite=new FavoriteFoodManagerSqLite(context);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view_layout_returnedFood = (LinearLayout) inflater.inflate(R.layout.home_fragment, null);
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        userName=view_layout_returnedFood.findViewById(R.id.name);
        userName.setText(MainHomeActivity.user.getName());
        initImageSlider(view_layout_returnedFood);
        initCategory(view_layout_returnedFood);
        initPolularFood(view_layout_returnedFood);
        initYourchoice(view_layout_returnedFood);
        initSearchingFood(view_layout_returnedFood);
        return view_layout_returnedFood;
    }

    void initSearchingFood(LinearLayout viewGroup) {
        searchingItemEdit = viewGroup.findViewById(R.id.seach_item);
        searchingItemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindItemActivity.class);
                startActivity(intent);
            }
        });
        searchingItemEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    Intent intent=new Intent(context,FindItemActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    void initImageSlider(LinearLayout viewGroup) {
        mViewpager2 = viewGroup.findViewById(R.id.view_paper2);
        mCircleIndicator3 = viewGroup.findViewById(R.id.circle_indicator3);
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


    private void initCategory(LinearLayout viewGroup) {
        mRecyleviewCategory = viewGroup.findViewById(R.id.rcv_Category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        mRecyleviewCategory.setLayoutManager(gridLayoutManager);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(4, 20, false);
        mRecyleviewCategory.addItemDecoration(gridSpacingItemDecoration);

        categoryAdapter = new CategoryAdapter(context, new IClickFoodCategoryListener() {
            @Override
            public void onItemFoodCategoryHandler(Categories category) {
                Intent intent = new Intent(context, FoodCatetoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyleviewCategory.setAdapter(categoryAdapter);
    }

    private void initYourchoice(LinearLayout viewGroup) {

        mRecycleviewChoice = viewGroup.findViewById(R.id.rcv_yourChoice);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        mRecycleviewChoice.setLayoutManager(gridLayoutManager);
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(2, 50, false);
        mRecycleviewChoice.addItemDecoration(itemDecoration);
        mFoodListAdapter = new FoodListAdapter(context, mFoodList, new IClickFoodItemListener() {
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


    void initPolularFood(LinearLayout viewGroup) {
        mRecyleviewPopular = viewGroup.findViewById(R.id.rcv_popular);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        mRecyleviewPopular.setLayoutManager(linearLayoutManager);
        mPopularAdapter = new PopularAdapter(mPopularFoodList, context, new IClickFoodItemListener() {
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
        }, new IClickFavoriteListener() {
            @Override
            public void onItemClickHandler(FoodModel foodModel) {
                favoriteFoodManagerSqLite.addFavoriteFood(foodModel);
                NotificationDialog notificationDialog = new NotificationDialog(context);
                notificationDialog.setContent("Đã thêm vào mục yêu thích");
                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                notificationDialog.show();
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

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 5000);
    }
}
