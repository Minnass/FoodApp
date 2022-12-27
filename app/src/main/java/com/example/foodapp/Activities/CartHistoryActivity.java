package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CartHistoryAdapter;
import com.example.foodapp.Iterface.ICLickHistoryCartItemListener;
import com.example.foodapp.Model.CartHistoryItemModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.ConverterDateString;
import com.example.foodapp.Util.InternetConnection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartHistoryActivity extends AppCompatActivity {
    ImageView backBtn, fromDateBtn, toDateBtn, searchingBtn;
    TextView fromDate, toDate;
    LinearLayout resultContainer;
    RecyclerView historyListRCV;
    List<CartHistoryItemModel> historyList;
    CartHistoryAdapter cartHistoryAdapter;
    FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_history);
        mappingID();
        initView();
        initResultReturnedUI();
        handleClick();
    }

    void mappingID() {
        backBtn = findViewById(R.id.back_historyCart);
        fromDateBtn = findViewById(R.id.fromDateBtn);
        toDateBtn = findViewById(R.id.toDateBtn);
        searchingBtn = findViewById(R.id.searchingButton);
        fromDate = findViewById(R.id.dateFrom);
        toDate = findViewById(R.id.dateTo);
        resultContainer = findViewById(R.id.result_container);
        historyListRCV = findViewById(R.id.cartHistoryRCV);
    }

    void initView() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        fromDate.setText(currentDate);
        toDate.setText(currentDate);
    }

    void handleClick() {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        fromDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CartHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        fromDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });
        toDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CartHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        toDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, date + 7);
                datePickerDialog.show();

            }
        });

        searchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = fromDate.getText().toString();
                String endDate = toDate.getText().toString();
                getHistoryOrder(ConverterDateString.getConvertedDate(startDate),ConverterDateString.getConvertedDate(endDate));
                resultContainer.setVisibility(View.VISIBLE);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initResultReturnedUI() {
        cartHistoryAdapter = new CartHistoryAdapter(historyList, this, new ICLickHistoryCartItemListener() {
            @Override
            public void onClick(CartHistoryItemModel item)
             {
                 Intent intent=new Intent(CartHistoryActivity.this,HistoryOrderDetailActivity.class);
                 Bundle bundle=new Bundle();
                 bundle.putSerializable("historyOrder",item);
                 intent.putExtras(bundle);
                 startActivity(intent);
             }
        });
        historyListRCV.setAdapter(cartHistoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        historyListRCV.setLayoutManager(linearLayoutManager);
    }

    void getHistoryOrder(String startDate,String endDate) {
        compositeDisposable.add(mFoodAppApi.getHistoryOrders(MainHomeActivity.user.getUserID(),startDate,endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allDeliveryType -> {
                            if (allDeliveryType != null && allDeliveryType.size() > 0) {
                              historyList=allDeliveryType;
                              cartHistoryAdapter.setData(historyList);
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