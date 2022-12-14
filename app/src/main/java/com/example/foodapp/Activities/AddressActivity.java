package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Adapter.AddressAdapter;
import com.example.foodapp.Iterface.Address.IClickAddressListener;

import com.example.foodapp.Model.SQLiteModel.AddressModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.AddressManagerSqLite;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AddressActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 999;
    ImageView back;
    TextView add;
    RecyclerView addListRCV;
    AddressAdapter addressAdapter;
    List<AddressModel> addressList;
    AddressManagerSqLite addressManagerSqLite = new AddressManagerSqLite(this);

    ActivityResultLauncher<Intent> activityResultLauncher;

    String type;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            addressManagerSqLite.deleteItem(addressList.get(position));
            addressList.remove(position);
            addressAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(AddressActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mappingID();
        initAddressRCV();
        handleClick();
        Bundle _bundle = getIntent().getExtras();
        if (_bundle != null) {
            type = _bundle.getString("type");
        }
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
        addressAdapter = new AddressAdapter(addressList, this, new IClickAddressListener() {
            @Override
            public void onSettingItemClick(int index) {
                HandleAddressSettingClick(index);
            }

            @Override
            public void onChosingItemClick(int index) {
               if(!type.equals("view"))
               {
                   AddressModel temp = addressList.get(index);
                   addressList.set(index, addressList.get(0));
                   addressList.set(0, temp);
                   addressAdapter.notifyDataSetChanged();
                   sendBackAddressChosen(addressList.get(0));
               }
            }
        });
        addListRCV.setAdapter(addressAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        addListRCV.setLayoutManager(linearLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(addListRCV);
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

    public void sendBackAddressChosen(AddressModel chosenAddress) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chosenAddress", chosenAddress);
        if (type.equals("init")) {
            bundle.putString("type","init");
        }
        else
        {
            bundle.putString("type","setting");
        }
        intent.putExtras(bundle);
        setResult(REQUEST_CODE, intent);
        super.onBackPressed();
    }
}