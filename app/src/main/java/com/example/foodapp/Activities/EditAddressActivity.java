package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Iterface.Address.IClickAddressSettingListener;
import com.example.foodapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditAddressActivity extends AppCompatActivity {
    ImageView back;
    TextInputLayout nameLayout, phoneLayout, addressLayout;
    TextInputEditText name, phone, address;
    TextView saving;
    private String type;
    private int index;

    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        mappingID();
        initView();
        HandleClick();
    }

    void mappingID() {
        back = findViewById(R.id.backEditAddressActivity);
        saving = findViewById(R.id.saving_editAddressActivity);
        nameLayout = findViewById(R.id.nameLayout_editAddress);
        phoneLayout = findViewById(R.id.phoneLayout_editAddress);
        addressLayout = findViewById(R.id.addressLayout_editAddress);
        name = findViewById(R.id.name_editAdressActivity);
        phone = findViewById(R.id.phone_editAdressActivity);
        address = findViewById(R.id.address_editAdressActivity);

    }

    void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("type").equals("setting")) {
            name.setText(bundle.getString("name"));
            phone.setText(bundle.getString("phone"));
            address.setText(bundle.getString("address"));
        }
        index = bundle.getInt("index");
        type = bundle.getString("type");
    }

    void HandleClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    nameLayout.setHelperText("Required*");
                    nameLayout.setBoxBackgroundColor(Color.RED);
                    nameLayout.setHelperTextEnabled(true);
                    return;
                }
                if (phone.getText().toString().equals("")) {
                    phoneLayout.setHelperText("Required*");
                    phoneLayout.setBoxBackgroundColor(Color.RED);
                    phoneLayout.setHelperTextEnabled(true);
                    return;
                }
                if (address.getText().toString().equals("")) {
                    addressLayout.setHelperText("Required*");
                    addressLayout.setBoxBackgroundColor(Color.RED);
                    addressLayout.setHelperTextEnabled(true);
                    return;
                }
                sendBackEdditedAddress();
            }
        });
    }

    private void sendBackEdditedAddress() {
        Intent intent = new Intent();
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("phone", phone.getText().toString());
        intent.putExtra("address", address.getText().toString());
        intent.putExtra("type", type);
        if (type.equals("setting")) {
            intent.putExtra("index", index);

        }
        setResult(REQUEST_CODE, intent);
        super.onBackPressed();
    }


}