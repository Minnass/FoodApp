package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Adapter.FoodListAdminAdapter;
import com.example.foodapp.Iterface.IClickAdminFoodOptionListener;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodListAdminActivity extends AppCompatActivity {
    ImageView back;
    EditText searchingBox;
    RecyclerView foodListRCV;
    FloatingActionButton button;
    ActivityResultLauncher<Intent> activityResultLauncher;

    List<FoodModel> foodList;
    FoodListAdminAdapter adminAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_admin);
        mappingID();
        handleClick();
        initfoodListRCV();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 100) {
                            Intent intent = result.getData();
                            Bundle bundle = intent.getExtras();
                            if (bundle != null) {
                                if (bundle.getBoolean("success")) {
                                    NotificationDialog notificationDialog = new NotificationDialog(FoodListAdminActivity.this);
                                    notificationDialog.setContent("Thêm sản phẩm thành công");
                                    notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                                    notificationDialog.show();
                                    getAllFood();
                                } else {
                                    NotificationDialog notificationDialog = new NotificationDialog(FoodListAdminActivity.this);
                                    notificationDialog.setContent("Thêm sản phẩm thất bại");
                                    notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_warning_24);
                                    notificationDialog.show();
                                }
                            }

                        }
                    }
                });
    }

    void mappingID() {
        back = findViewById(R.id.back_foodListAdmin);
        searchingBox = findViewById(R.id.searchFood_foodListAdmin);
        foodListRCV = findViewById(R.id.foodListRCV_admin);
        button = findViewById(R.id.floatActionButton);
    }

    void handleClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListAdminActivity.this, FoodEditerAdminActitivity.class);
                activityResultLauncher.launch(intent);
            }
        });
        searchingBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    searchFoodName(searchingBox.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    void initfoodListRCV() {
        adminAdapter = new FoodListAdminAdapter(foodList, this, new IClickFoodItemListener() {
            @Override
            public void onItemClickHandler(FoodModel food) {
                Intent intent = new Intent(FoodListAdminActivity.this, DetailFoodActivity.class);
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
        }, new IClickAdminFoodOptionListener() {
            @Override
            public void onDeleteClick(FoodModel food) {
                deleteFood(food);
            }

            @Override
            public void onEditClick(FoodModel food) {
                eidtFood(food);
            }
        });
        foodListRCV.setAdapter(adminAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        foodListRCV.setLayoutManager(linearLayoutManager);
        getAllFood();
    }

    public void searchFoodName(String keyword) {
        compositeDisposable.add(mFoodAppApi.searchFood(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFood -> {
                            if(allFood!=null)
                            {
                                adminAdapter.setData(allFood);
                            }
                        },
                        error->
                        {
                            Log.d("Loi",error.getMessage());
                        }
                )
        );
    }

    void deleteFood(FoodModel foodModel) {
        compositeDisposable.add(mFoodAppApi.deleteFood(foodModel.getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            if (success) {
                              try {
                                  foodList.remove(foodModel);

                              }
                              catch (Exception e)
                              {

                              }
                                getAllFood();
                                NotificationDialog notificationDialog = new NotificationDialog(this);
                                notificationDialog.setContent("Xóa sản phẩm thành công");
                                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                                notificationDialog.show();
                            }

                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    void eidtFood(FoodModel food) {

    }

    void getAllFood() {
        compositeDisposable.add(mFoodAppApi.getAllFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFood -> {
                            if (allFood.size() > 0) {
                                adminAdapter.setData(allFood);
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