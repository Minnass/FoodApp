package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Adapter.HistoryOrderAdapter;
import com.example.foodapp.Model.CartHistoryItemModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.text.SimpleDateFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoryOrderDetailActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView receivedUser, phoneNumber, address;
    TextView orderTime, deliveryType, orderCode;

    RecyclerView orderListRCV;
    List<ItemCartModel> foodList;
    HistoryOrderAdapter historyOrderAdapter;

    TextView totalProduct, totalProductPrice;

    TextView deliveryFee, saleCode, finalPrice;
    TextView paymentType;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi= RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);

    private CartHistoryItemModel cartHistoryItemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_detail);
        mappingID();
        initView();
        initFoodListRCV();
    }

    private void mappingID() {
        backBtn = findViewById(R.id.back_historyOrder);
        receivedUser = findViewById(R.id.receivedUser_orderDetail);
        phoneNumber = findViewById(R.id.phone_detailOrder);
        address = findViewById(R.id.address_detailOrder);
        orderTime = findViewById(R.id.date_historyDetail);
        deliveryType = findViewById(R.id.deliveryForm_orderDetail);
        orderCode = findViewById(R.id.orderCode_historyOrder);
        orderListRCV = findViewById(R.id.foodListRCV_historyOrder);
        totalProduct = findViewById(R.id.totalFoodNumber_historyOrder);
        totalProductPrice = findViewById(R.id.totalPrice_historyOrder);
        deliveryFee = findViewById(R.id.deliveryFee_historyOrder);
        saleCode = findViewById(R.id.saleCode_historyOrder);
        finalPrice = findViewById(R.id.finalPrice_historyOrder);
        paymentType = findViewById(R.id.paymentForm_historyOrder);
    }
    void initView()
    {
        Bundle bundle=getIntent().getExtras();
        cartHistoryItemModel= (CartHistoryItemModel) bundle.getSerializable("historyOrder");
        receivedUser.setText(cartHistoryItemModel.getReceivedUser());
        phoneNumber.setText(cartHistoryItemModel.getPhoneNumber());
        address.setText(cartHistoryItemModel.getAddress());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"  );
        orderTime.setText(simpleDateFormat.format(cartHistoryItemModel.getOrderDate()));
        deliveryType.setText(cartHistoryItemModel.getDeliveryType());
        orderCode.setText(cartHistoryItemModel.getOrderCode());
        totalProductPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(cartHistoryItemModel.getTotalPrice()));
        deliveryFee.setText(VietNameseCurrencyFormat.getVietNameseCurrency(cartHistoryItemModel.getDeliveryFee()));
        saleCode.setText(VietNameseCurrencyFormat.getVietNameseCurrency(cartHistoryItemModel.getSaleCode()));
        float _finalPrice=cartHistoryItemModel.getTotalPrice()+cartHistoryItemModel.getDeliveryFee()-cartHistoryItemModel.getSaleCode();
        finalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_finalPrice));
        paymentType.setText(cartHistoryItemModel.getPaymentType());
    }


    void initFoodListRCV() {
        historyOrderAdapter = new HistoryOrderAdapter(this, foodList);
        orderListRCV.setAdapter(historyOrderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        orderListRCV.setLayoutManager(linearLayoutManager);
        getData();
    }

    void getData() {
        String _orderCode=cartHistoryItemModel.getOrderCode();
        compositeDisposable.add(mFoodAppApi.getHistoryOrderedFoodList(_orderCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodListReturned -> {
                            if (foodListReturned!=null&&foodListReturned.size()>0)
                            {
                                foodList=foodListReturned;
                                historyOrderAdapter.setData(foodList);
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );

    }
    void handleClick()
    {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}