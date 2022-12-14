package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Adapter.AddressAdapter;
import com.example.foodapp.Iterface.Address.IClickAddressSettingListener;
import com.example.foodapp.Model.SQLiteModel.AddressModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.AddressManagerSqLite;

import java.util.List;

public class AddressActivity extends AppCompatActivity {
    ImageView back;
    TextView add;
    RecyclerView addListRCV;
    AddressAdapter addressAdapter;
    List<AddressModel> addressList;
    AddressManagerSqLite addressManagerSqLite = new AddressManagerSqLite(this);

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mappingID();
        initAddressRCV();
        handleClick();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 100) {
                            Intent intent = result.getData();
                            Bundle bundle = intent.getExtras();
                            if (bundle.getString("type").equals("setting")) {
                                int index = bundle.getInt("index");

                                AddressModel _address = addressList.get(index);
                                AddressModel oldItem = new AddressModel(
                                        _address.getName(), _address.getPhone(), _address.getAddress()
                                );
                                _address.setAddress(bundle.getString("address"));
                                _address.setName(bundle.getString("name"));
                                _address.setPhone(bundle.getString("phone"));
                                updateAddressSQLite(oldItem, _address);
                            } else {
                                AddressModel item = new AddressModel(
                                        bundle.getString("name"),
                                        bundle.getString("phone"),
                                        bundle.getString("address")
                                );
                                addressList.add(item);
                                addAddressSQLite(item);
                            }
                            addressAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void addAddressSQLite(AddressModel item) {
        addressManagerSqLite.addAddress(item);
    }

    private void updateAddressSQLite(AddressModel oldItem, AddressModel newItem) {
        addressManagerSqLite.upDateAddress(oldItem, newItem);
    }

    void mappingID() {
        back = findViewById(R.id.backAddressActivity);
        add = findViewById(R.id.addAdress);
        addListRCV = findViewById(R.id.addressRCV);
    }

    void handleClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "newItem");
                intent.putExtras(bundle);
                activityResultLauncher.launch(intent);
            }
        });
    }

    void initAddressRCV() {
        addressList = addressManagerSqLite.getAllContacts();
        addressAdapter = new AddressAdapter(addressList, this, new IClickAddressSettingListener() {
            @Override
            public void onItemClick(int index) {
                HandleAddressSettingClick(index);
            }
        });
        addListRCV.setAdapter(addressAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        addListRCV.setLayoutManager(linearLayoutManager);
    }

    private void HandleAddressSettingClick(int index) {
        AddressModel item = addressList.get(index);
        Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", item.getName());
        bundle.putString("phone", item.getPhone());
        bundle.putString("address", item.getAddress());
        bundle.putString("type", "setting");
        bundle.putInt("index", index);
        intent.putExtras(bundle);
        activityResultLauncher.launch(intent);
    }
}