package com.example.foodapp.Activities;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Fragment.DefaultSearchingFragment;
import com.example.foodapp.Fragment.ReturnedFoodsFragment;
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

public class FindItemActivity extends FragmentActivity {
    private EditText searchingBox;
    private ImageView back;
    private DefaultSearchingFragment defaultSearchingFragment;
    private ReturnedFoodsFragment returnedFoodsFragment;
    private FragmentTransaction ft;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi;
    List<FoodModel> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_item);
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        ft = getSupportFragmentManager().beginTransaction();
        returnedFoodsFragment = ReturnedFoodsFragment.newInstance();
        ft.replace(R.id.fragment_search, returnedFoodsFragment);
        ft.commit();
        ft = getSupportFragmentManager().beginTransaction();
        defaultSearchingFragment = DefaultSearchingFragment.newInstance();
        ft.replace(R.id.fragment_search, defaultSearchingFragment);
        ft.commit();
        intSearchingBox();
    }

    void intSearchingBox() {
        searchingBox = findViewById(R.id.searchFood_searchActivity);
        searchingBox.requestFocus();
        searchingBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_search, defaultSearchingFragment);
                ft.commit();
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

    void searchFoodName(String keyword) {

        compositeDisposable.add(mFoodAppApi.searchFood(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFood -> {
                            if(allFood.size()>0)
                            {
                                result=new ArrayList<>(allFood);
                                returnedFoodsFragment.setData(result);
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_search, returnedFoodsFragment);
                                ft.commit();
                            }
                        },
                        error->
                        {
                            Log.d("Loi",error.getMessage());
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