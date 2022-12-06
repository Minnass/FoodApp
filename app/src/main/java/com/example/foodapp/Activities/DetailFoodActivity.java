package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DetailFoodActivity extends AppCompatActivity {
    private ImageView back, foodImg;
    private TextView foodName, quantitySold, currentPrice, originalPrice, sale, eaterNumber, expire, preparationTime, description, preservationTool, buyNow;
    private ImageView call, interest;
    private LinearLayout addTocart;
    private RecyclerView RelativefoodListRCV;
    private List<FoodModel> relativeFoods;
    private FoodListAdapter relativeFoodListAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        mappingID();
        intView();
        getDetailFood(foodName.getText().toString());
        createRelativeFoodList(foodName.getText().toString());
        handleButonClick();
    }

    void mappingID() {
        back = findViewById(R.id.back_detailActivity);
        foodImg = findViewById(R.id.foodImageDetail);
        foodName = findViewById(R.id.foodNameDetailActivity);
        quantitySold = findViewById(R.id.quantity_sold_DetailActivity);
        currentPrice = findViewById(R.id.currentPrice_DetailActivity);
        originalPrice = findViewById(R.id.originalPriceDetailActivty);
        sale = findViewById(R.id.discount_DetailActivity);
        eaterNumber = findViewById(R.id.eaterNumber);
        expire = findViewById(R.id.dateNumber);
        preparationTime = findViewById(R.id.preparationTime);
        description = findViewById(R.id.description);
        preservationTool = findViewById(R.id.preserveGuide);
        call = findViewById(R.id.calling);
        interest = findViewById(R.id.interest);
        addTocart = findViewById(R.id.btnAddToCard);
        buyNow = findViewById(R.id.buyNow);
        RelativefoodListRCV = findViewById(R.id.relative_foodRCV);
    }

    void handleButonClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void intView() {
        Bundle bundle = getIntent().getExtras();
        description.setText(bundle.getString("description"));
        foodName.setText(bundle.getString("foodname"));
        currentPrice.setText(bundle.getString("currentprice"));
        originalPrice.setText(bundle.getString("originalprice"));
        quantitySold.setText(String.valueOf(bundle.getInt("quantitySold")));
        sale.setText("Giảm " + String.valueOf(bundle.getInt("sale")) + "%");
        Glide.with(getApplicationContext()).load(bundle.getString("image")).into(foodImg);
    }

    void getDetailFood(String foodName) {
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        compositeDisposable.add(mFoodAppApi.getDetalFood(foodName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodDetail -> {
                            if (foodDetail != null) {
                                expire.setText(foodDetail.getExpiration().toString());
                                eaterNumber.setText(String.valueOf(foodDetail.getEaterNumber()) + " người");
                                preparationTime.setText(foodDetail.getPreparationTime());
                                preservationTool.setText(foodDetail.getPreservationGuide());
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    void createRelativeFoodList(String foodName) {
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        compositeDisposable.add(mFoodAppApi.getRelativeFoods(foodName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFoods -> {
                            if (allFoods != null) {
                                relativeFoods = new ArrayList<>(allFoods);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailFoodActivity.this, RecyclerView.HORIZONTAL, false);
                                RelativefoodListRCV.setLayoutManager(linearLayoutManager);
                                relativeFoodListAdapter = new FoodListAdapter(DetailFoodActivity.this, relativeFoods, new IClickFoodItemListener() {
                                    @Override
                                    public void onItemClickHandler(FoodModel food) {
                                        Intent intent = new Intent(DetailFoodActivity.this, DetailFoodActivity.class);
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
                                RelativefoodListRCV.setAdapter(relativeFoodListAdapter);
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
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}