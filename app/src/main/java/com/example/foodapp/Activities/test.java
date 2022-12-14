package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.CartManagerSqLite;

import java.util.List;

public class test extends AppCompatActivity {
    TextView create, check, add, count;
    CartManagerSqLite cartManagerSqLite;
    EditText quantity, foodName, price, discoutn, iamge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        quantity = findViewById(R.id.quantity);
        discoutn = findViewById(R.id.dicountTextda);
        iamge = findViewById(R.id.img);
        foodName = findViewById(R.id.foodName);
        create = findViewById(R.id.create);
        price = findViewById(R.id.price);
        check = findViewById(R.id.checkExist);
        add = findViewById(R.id.add);
        count = findViewById(R.id.count);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartManagerSqLite = new CartManagerSqLite(test.this);
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(test.this, "" + cartManagerSqLite.getContactsCount(), Toast.LENGTH_SHORT).show();

                List<ItemCartModel> list = cartManagerSqLite.getAllContacts();
                for (ItemCartModel a : list) {
                    Log.d("Showall", a.getFoodName() + "   " + a.getPrice() + "  " + a.getQuantity() + "  " +
                            a.getDiscount() + "  " + a.getImage().toString());
                }

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartManagerSqLite.addCart(new ItemCartModel(
                        foodName.getText().toString(),
                        Integer.parseInt(quantity.getText().toString()),
                        Integer.parseInt(price.getText().toString()),
                        Integer.parseInt(discoutn.getText().toString()),
                        iamge.getText().toString()
                ));
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int i=   cartManagerSqLite.getItemQuantity(new ItemCartModel(
                        foodName.getText().toString(),
                        Integer.parseInt(quantity.getText().toString()),
                        Integer.parseInt(price.getText().toString()),
                        Integer.parseInt(discoutn.getText().toString()),
                        iamge.getText().toString()));
                Toast.makeText(test.this, ""+i, Toast.LENGTH_SHORT).show();
            }
        });
    }
}