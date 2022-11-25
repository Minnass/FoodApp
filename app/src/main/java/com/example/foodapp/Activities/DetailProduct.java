package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.Util.ManagementCart;


public class DetailProduct extends AppCompatActivity {
    LinearLayout addToCart;
    FoodModel foodModel;
    ImageView backActivity, foodFicture;
    TextView foodName, foodPrice,  foodQuantity, star, time, caloteries, totalPrice, plusClick;
    TextView minusClick;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
            initView();
        setDetailView();
        setQuantityItemChange();

        initBackActivity();
    }
    void initView()
    {
        addToCart=findViewById(R.id.addToCart_detailActivity);
        scrollView=findViewById(R.id.scroll_detailActivity);
        backActivity=findViewById(R.id.back_detailActivity);
        foodFicture=findViewById(R.id.image_detailActivity);
        foodPrice=findViewById(R.id.itemPrice_detailActivity);
        minusClick=findViewById(R.id.minus_detailActivity);
        plusClick=findViewById(R.id.plus_detailActivity);
        foodQuantity=findViewById(R.id.quantity_detailActivity);
        totalPrice=findViewById(R.id.totalPrice_detailActivity);
        foodName=findViewById(R.id.itemName_detailActitvy);
    }

    void initBackActivity() {

        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void setDetailView() {
        Bundle bundle = getIntent().getExtras();
        byte[] byteArray = getIntent().getByteArrayExtra("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        foodFicture.setImageBitmap(bmp);
        foodName.setText(bundle.getString("name"));
        foodPrice.setText(bundle.getString("price"));
        foodQuantity.setText("1");
        totalPrice.setText(bundle.getString("price"));
        scrollView.setVerticalScrollBarEnabled(false);
    }
    void setClickAddCart()
    {
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagementCart.insertFood(MainHomeActivity.selectedItemList, foodModel);
            }
        });

    }

    void setQuantityItemChange() {
        minusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusClick.setAlpha(1f);
                int temp = Integer.valueOf((foodQuantity.getText().toString()));
                if (temp > 0) {
                    temp--;
                    foodQuantity.setText(String.valueOf(temp));
                    if (temp == 0) {
                        minusClick.setAlpha(0.5f);
                    }
                    float _totalPrice= ManagementCart.getTotalFeeOfOneType(Float.valueOf(foodPrice.getText().toString()),temp);
                    totalPrice.setText(String.valueOf(_totalPrice));
                }
                else
                {
                    //do somthing
                }
            }
        });
        plusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusClick.setAlpha(1f);
                int temp = Integer.valueOf((foodQuantity.getText().toString()));
                if(temp<10)
                {
                    temp++;
                    foodQuantity.setText(String.valueOf(temp));
                    float _totalPrice= ManagementCart.getTotalFeeOfOneType(Float.valueOf(foodPrice.getText().toString()),temp);
                    totalPrice.setText(String.valueOf(_totalPrice));
                }
                else
                {
                    plusClick.setAlpha(0.5f);
                }
            }
        });
    }

}