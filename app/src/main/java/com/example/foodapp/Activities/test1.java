package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;

import java.text.SimpleDateFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class test1 extends AppCompatActivity {

    private FoodAppApi mFoodAppApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        getList();
    }

    void getList()
    {
        compositeDisposable.add(mFoodAppApi.getHistoryOrders("2022/12/21","2022/12/31")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allDeliveryType -> {
                            if (allDeliveryType != null && allDeliveryType.size() > 0){
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"  );
                                Toast.makeText(this, simpleDateFormat.format(allDeliveryType.get(0).getOrderDate()), Toast.LENGTH_SHORT).show();
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