package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodapp.Adapter.CartListAdapter;
import com.example.foodapp.Iterface.ChangeItemNumber;
import com.example.foodapp.Model.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.ManagementCart;

import java.util.List;

public class CartActivity extends AppCompatActivity implements ChangeItemNumber {
    private RecyclerView mRecyclerView;
    TextView itemTotal;
    TextView deliveryFee;
    TextView tax;
    TextView total;
    TextView emptyText;
    TextView checkout;
    ImageView back;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();

        change();
        initList();
        initBackBtn();
    }

    void initView() {
        back=findViewById(R.id.back_activity_cart);
        itemTotal = findViewById(R.id.item_total);
        deliveryFee = findViewById(R.id.delivery_services);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        checkout = findViewById(R.id.checkout);
        emptyText = findViewById(R.id.empty_text);
        linearLayout = findViewById(R.id.linear2_activityCart);
    }

    void initBackBtn()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initList() {
        mRecyclerView= findViewById(R.id.rcv_cartList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        CartListAdapter mCarlistAdapter= new CartListAdapter(MainHomeActivity.selectedItemList,this,this);
        mRecyclerView.setAdapter(mCarlistAdapter);
        if (MainHomeActivity.selectedItemList.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void change() {
        float percentTax = 0.02f;
        float delivery = 10;
        float  _tax =  Math.round((ManagementCart.getTotalFee(MainHomeActivity.selectedItemList) * percentTax*100.0)/100.0);
        float _totalPrice =  _tax + delivery + ManagementCart.getTotalFee(MainHomeActivity.selectedItemList);
        float round = (float) (Math.round(_totalPrice * 100.0) / 100.0);
        tax.setText(String.valueOf(_tax));
        deliveryFee.setText(String.valueOf(delivery));
        total.setText(String.valueOf(round));
    }
}