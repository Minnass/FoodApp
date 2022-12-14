package com.example.foodapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodapp.Adapter.CartListAdapter;
import com.example.foodapp.Iterface.SQliteInterface.SqliteLisener;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.CartManagerSqLite;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    ImageView back;
    TextView modifier;
    RecyclerView foodListRCV;
    LinearLayout noneOfFood, buyBtn;
    CheckBox checkAllFood;
    TextView totalPrice, savingPrice, numberOfSelection;
    LinearLayout paymentState, modifierState;

    CartListAdapter cartListAdapter;

    List<ItemCartModel> foodList;

    CartManagerSqLite cartManagerSqLite = new CartManagerSqLite(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mappingID();
        handleBackClick();
        initRecycleView();
        handleNoneOfData();
        setPrice();
        HanleCheckAllItem();
        handleModierClick();
    }

    void mappingID() {
        modifierState = findViewById(R.id.modifyState);
        paymentState = findViewById(R.id.paymentState);
        back = findViewById(R.id.backCartActivity);
        modifier = findViewById(R.id.modify_CartActivity);
        foodList = findViewById(R.id.allFoof_CartActivity);
        checkAllFood = findViewById(R.id.totalCheckbox);
        noneOfFood = findViewById(R.id.noneOfFood);
        buyBtn = findViewById(R.id.buyItembtn);
        totalPrice = findViewById(R.id.total_money);
        savingPrice = findViewById(R.id.saving_money);
        numberOfSelection = findViewById(R.id.totalFood);
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
    }

    public void setPrice() {
        float _totalPrice = 0;
        float _savingMoney = 0;
        if (foodList != null) {
            for (int position = 0; position < foodList.size(); position++) {
                ItemCartModel item = foodList.get(position);
                if (foodList.get(position).getDiscount() != 0) {
                    _savingMoney += ((float) item.getDiscount() / 100) * item.getPrice();
                }
                float currentPrice = item.getPrice() * (1 - (float) item.getDiscount() / 100);
                _totalPrice += currentPrice;
            }
        }
        totalPrice.setText(String.valueOf(_totalPrice));
        savingPrice.setText(String.valueOf(_savingMoney));
    }

    void handleBackClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void handleNoneOfData() {
        if (foodList == null || foodList.size() == 0) {
            noneOfFood.setVisibility(View.VISIBLE);
            foodListRCV.setVisibility(View.GONE);
        } else {
            foodListRCV.setVisibility(View.VISIBLE);
            noneOfFood.setVisibility(View.GONE);
        }
    }

    void initRecycleView() {
        foodList = cartManagerSqLite.getAllContacts();
        cartListAdapter = new CartListAdapter(foodList, this, new SqliteLisener() {
            @Override
            public void updateQuantity(ItemCartModel item, int newQuantity) {
                cartManagerSqLite.updateQuantity(item, newQuantity);
            }

            @Override
            public void deleteItems(List<ItemCartModel> items) {
                cartManagerSqLite.deleteSomeItems(items);
            }
        });

        foodListRCV.setAdapter(cartListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        foodListRCV.setLayoutManager(linearLayoutManager);
    }
}