package com.example.foodapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CartListAdapter;
import com.example.foodapp.Iterface.SQliteInterface.ISqliteLisener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.SQLite.FavoriteFoodManagerSqLite;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity {
    ImageView back;
    TextView modifier,  deleteChoice;
    RecyclerView foodListRCV;
    LinearLayout noneOfFood, buyBtn;
    CheckBox checkAllFood;
    TextView totalPrice, savingPrice, numberOfSelection;
    LinearLayout paymentState, modifierState;

    CartListAdapter cartListAdapter;

    List<ItemCartModel> foodList;

    private FoodAppApi mFoodAppApi= RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    FavoriteFoodManagerSqLite favoriteFoodManagerSqLite = new FavoriteFoodManagerSqLite(this);
    CartManagerSqLite cartManagerSqLite = new CartManagerSqLite(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mappingID();
        handleBackClick();
        initRecycleView();

        setPrice();
        HanleCheckAllItem();
        handleModierClick();
    }

    void mappingID() {
        modifierState = findViewById(R.id.modifyState);
        paymentState = findViewById(R.id.paymentState);
        back = findViewById(R.id.backCartActivity);
        modifier = findViewById(R.id.modify_CartActivity);
        foodListRCV = findViewById(R.id.allFoof_CartActivity);
        checkAllFood = findViewById(R.id.totalCheckbox);
        noneOfFood = findViewById(R.id.noneOfFood);
        buyBtn = findViewById(R.id.buyItembtn);
        totalPrice = findViewById(R.id.total_money);
        savingPrice = findViewById(R.id.saving_money);
        numberOfSelection = findViewById(R.id.totalFood);
        deleteChoice = findViewById(R.id.delete);

    }

    void HanleCheckAllItem() {
        checkAllFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < foodList.size(); i++) {
                        foodList.get(i).setSelected(true);
                    }
                } else {
                    for (int i = 0; i < foodList.size(); i++) {
                        foodList.get(i).setSelected(false);
                    }
                }
                cartListAdapter.notifyDataSetChanged();
            }
        });
    }

    void handleModierClick() {
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modifier.getText().toString().equals("Sửa")) {
                    modifier.setText("Xong");
                    paymentState.setVisibility(View.GONE);
                    modifierState.setVisibility(View.VISIBLE);
                } else {
                    modifier.setText("Sửa");
                    paymentState.setVisibility(View.VISIBLE);
                    modifierState.setVisibility(View.GONE);
                }
            }
        });

    deleteChoice.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int counter = 0;
            for (int i=0;i<foodList.size();i++) {
                if (foodList.get(i).isSelected()) {
                    cartManagerSqLite.deleteItem(foodList.get(i));
                    foodList.remove(foodList.get(i));
                    i--;
                    counter++;
                }
            }
            cartListAdapter.notifyDataSetChanged();
            if (counter > 0) {
                setPrice();
                Toast.makeText(CartActivity.this, ""+counter, Toast.LENGTH_SHORT).show();
            }
        }
    });
    }

    public void setPrice() {
        float _totalPrice = 0;
        float _savingMoney = 0;
        if (foodList != null) {
            for (int position = 0; position < foodList.size(); position++) {
                ItemCartModel item = foodList.get(position);
                if (foodList.get(position).getDiscount() != 0) {
                    _savingMoney += (((float) item.getDiscount() / 100) * item.getPrice())*item.getQuantity();
                }
                float currentPrice = item.getPrice() * (1 - (float) item.getDiscount() / 100);
                _totalPrice += currentPrice*item.getQuantity();
            }
        }
        totalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency( _totalPrice));
        savingPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_savingMoney));
    }

    void handleBackClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    void initRecycleView() {

        cartListAdapter = new CartListAdapter(foodList, this, new ISqliteLisener() {
            @Override
            public void updateQuantity(ItemCartModel item, int newQuantity) {
                cartManagerSqLite.updateQuantity(item, newQuantity);
                setPrice();
            }

            @Override
            public void deleteItems(List<ItemCartModel> items) {
                cartManagerSqLite.deleteSomeItems(items);
                setPrice();
            }
        });

        foodListRCV.setAdapter(cartListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        foodListRCV.setLayoutManager(linearLayoutManager);
        setDataForRecycleView();
    }

    void setDataForRecycleView() {
        foodList = cartManagerSqLite.getAllContacts();
        cartListAdapter.setData(foodList);
        if (foodList == null || foodList.size() == 0) {
            noneOfFood.setVisibility(View.VISIBLE);
            foodListRCV.setVisibility(View.GONE);
        } else {
            foodListRCV.setVisibility(View.VISIBLE);
            noneOfFood.setVisibility(View.GONE);
        }
    }
}