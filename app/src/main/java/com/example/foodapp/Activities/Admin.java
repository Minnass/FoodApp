package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.foodapp.R;

public class Admin extends AppCompatActivity {
    RelativeLayout foodList,orderList;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mappingID();
        handleCLick();
    }
    void mappingID(){
        back=findViewById(R.id.back_AdminActivity);
        foodList=findViewById(R.id.foodList_admin);
        orderList=findViewById(R.id.order_admin);
    }

    void handleCLick()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        foodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this,FoodListAdminActivity.class);
                startActivity(intent);
            }
        });
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(Admin.this,OrderListAdminActivity.class);
                    startActivity(intent);
            }
        });
    }
}