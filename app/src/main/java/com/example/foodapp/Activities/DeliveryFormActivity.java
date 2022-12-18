package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Adapter.DeliveryAdapter;
import com.example.foodapp.Model.DeliveryModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeliveryFormActivity extends AppCompatActivity {
    private final int REQUEST_CODE=998;
    private ImageView backBtn;
    private RecyclerView deliveryListRCV;
    private TextView doneBtn;
    private List<DeliveryModel> DeliveryList;
    private DeliveryAdapter deliveryAdapter;
    private FoodAppApi mFoodAppApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_form);
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        mappindID();
        initDeliveryListRCV();
        HandleClick();
    }

    void mappindID() {
        backBtn = findViewById(R.id.back_deliveryForm_activity);
        doneBtn = findViewById(R.id.doneBtn_deliveryActivity);
        deliveryListRCV = findViewById(R.id.deliveryList_RCV);

    }

    void HandleClick() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedDelivery", deliveryAdapter.getSelectedItem());
                setResult(REQUEST_CODE, intent);
                finish();
            }
        });
    }

    void initDeliveryListRCV() {
        deliveryAdapter = new DeliveryAdapter(DeliveryList, this);
        deliveryListRCV.setAdapter(deliveryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        deliveryListRCV.setLayoutManager(linearLayoutManager);
        setData();
    }

    void setData() {
        compositeDisposable.add(mFoodAppApi.getAllDeliveryType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allDeliveryType -> {
                            if (allDeliveryType != null && allDeliveryType.size() > 0) {
                                DeliveryList = allDeliveryType;
                                deliveryAdapter.setData(DeliveryList);
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