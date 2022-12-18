package com.example.foodapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.Adapter.PaymentAdapter;
import com.example.foodapp.Iterface.IClickItemCartListener;
import com.example.foodapp.Model.DeliveryModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.Model.SaleCodeModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.VietNameseCurrencyFormat;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextView nochoiceCaption, noAddressLabel, receivedPerson, phoneNumber, address, totalProductNumber;
    private LinearLayout addNewAddressBtn, locationButton, changingDeliveryButton;

    private RelativeLayout delivery_form;
    private TextView deliveryName, deliveryPrice, deliveryTime;
    private EditText saleCodeEdit;
    private TextView applySaleBtn, saleResult;
    private TextView finalProductPrice, finalDeliveryFee, saleCode, finalTotalPrice;
    private TextView orderBtn;

    private ArrayList<ItemCartModel> foodList;
    private PaymentAdapter paymentAdapter;
    private RecyclerView listfoodRCV;

    private SaleCodeModel saleCodeModel;
    private DeliveryModel deliveryModel;

    private CartManagerSqLite cartManagerSqLite=new CartManagerSqLite(this);
    private FoodAppApi mFoodAppAPi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mappingID();
        saleCodeModel = new SaleCodeModel("","", 0,false);
        HandleAddAdressButton();
        initFoodListRecycleView();
        handleDeliveryType();
        handleDeliveryTime();
        handleCheckValidSaleCode();
    }

    void mappingID() {
        backBtn = findViewById(R.id.back_paymentActivity);
        nochoiceCaption = findViewById(R.id.noChoice);
        addNewAddressBtn = findViewById(R.id.addLocation_paymentActivity);
        noAddressLabel = findViewById(R.id.noAddressLabel);
        receivedPerson = findViewById(R.id.userName_paymentActivity);
        phoneNumber = findViewById(R.id.phoneNumber_payment);
        address = findViewById(R.id.address_paymentActivity);
        listfoodRCV = findViewById(R.id.listFood_paymentActivity);
        locationButton = findViewById(R.id.locationContainer);
        totalProductNumber = findViewById(R.id.totalItem_paymentActivity);
        delivery_form = findViewById(R.id.delivery_form);
        deliveryName = findViewById(R.id.dileveryType_name);
        deliveryPrice = findViewById(R.id.dileveryType_fee);
        saleCodeEdit = findViewById(R.id.saleCode_paymentActivity);
        saleResult = findViewById(R.id.saleCode_result);
        applySaleBtn = findViewById(R.id.apply);
        finalProductPrice = findViewById(R.id.totalPrice_paymentActivity);
        finalDeliveryFee = findViewById(R.id.total_delivery_fee);
        saleCode = findViewById(R.id.total_saleCode);
        finalTotalPrice = findViewById(R.id.final_Price);
        orderBtn = findViewById(R.id.pay_paymentActivity);
        deliveryTime = findViewById(R.id.deliveryTime);
        changingDeliveryButton = findViewById(R.id.dileveryTime_changing);
    }

    void initFoodListRecycleView() {
        paymentAdapter = new PaymentAdapter(foodList, this, new IClickItemCartListener() {
            @Override
            public void onItemClick(ItemCartModel item, PaymentAdapter.PaymentViewHolder viewHolder) {
                ClickOpenBottomSheetDialog(item, viewHolder);
            }
        });
        listfoodRCV.setAdapter(paymentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listfoodRCV.setLayoutManager(linearLayoutManager);
        setData();
    }

    public void setData() {
        foodList = getIntent().getParcelableArrayListExtra("foodListChosend");
        paymentAdapter.setData(foodList);
        totalProductNumber.setText("Tổng" + foodList.size() + " sản phẩm");
        setPrice();
    }

    void setPrice()
    {
        float _totalProductPrice=0;
        for(int i=0;i<foodList.size();i++)
        {
            _totalProductPrice+=foodList.get(i).getPrice()*foodList.get(i).getQuantity();
        }
        finalProductPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_totalProductPrice));
        finalDeliveryFee.setText(VietNameseCurrencyFormat.getVietNameseCurrency(deliveryModel.getFee()));
        saleCode.setText(VietNameseCurrencyFormat.getVietNameseCurrency(saleCodeModel.getSaleValue()));
        float _totalPrice=_totalProductPrice+deliveryModel.getFee()-saleCodeModel.getSaleValue();
        finalTotalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_totalPrice));
    }
    void handleButton()
    {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartManagerSqLite.deleteALl();
                Toast.makeText(PaymentActivity.this,"Dat Hang thanh cong",Toast.LENGTH_SHORT).show();
                finish();;
                //

            }
        });
    }

    void ClickOpenBottomSheetDialog(ItemCartModel item, PaymentAdapter.PaymentViewHolder viewHolder) {
        ItemCartModel cacheItem = new ItemCartModel(
                item.getFoodName(), item.getQuantity(), item.getPrice(), item.getDiscount(), item.getImage()
        );
        View view = getLayoutInflater().inflate(R.layout.update_payment_bottomsheet, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        ImageView Back_sheetBtn = view.findViewById(R.id.close_updateSheet);
        ImageView foodImg = view.findViewById(R.id.fooImg_updateSheet);
        TextView foodName = view.findViewById(R.id.foodName_updateSheet);
        ImageView decrease_quantity = view.findViewById(R.id.decrease_updateSheet);
        ImageView increase_quantity = view.findViewById(R.id.increase_updateSheet);
        TextView quantity_sheet = view.findViewById(R.id.quantityItemCart_updateSheet);
        LinearLayout notionContainer = view.findViewById(R.id.notion_container_sheet);
        TextView notion = view.findViewById(R.id.notion_sheet);
        TextView totalPrice_sheet = view.findViewById(R.id.totalPrice_updateItem);
        TextView updateButton = view.findViewById(R.id.updateBtn_updateSheet);
        foodName.setText(item.getFoodName());
        quantity_sheet.setText(String.valueOf(item.getQuantity()));
        float _totalPrice = cacheItem.getPrice() * cacheItem.getQuantity();
        totalPrice_sheet.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_totalPrice));
        Glide.with(PaymentActivity.this).load(item.getImage()).into(foodImg);

        Back_sheetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        decrease_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = cacheItem.getQuantity();
                if (current == 1) {
                    return;
                }
                int previous = current - 1;
                cacheItem.setQuantity(previous);
                quantity_sheet.setText(String.valueOf(previous));
            }
        });
        increase_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = cacheItem.getQuantity();
                int next = current + 1;
                cacheItem.setQuantity(next);
                quantity_sheet.setText(String.valueOf(next));
            }
        });


        notionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotionContent(notion);
                if (!notion.getText().toString().equals("Ghi chú cho quán")) {
                    viewHolder.notion.setText(notion.getText().toString());
                }
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacheItem.getQuantity() != item.getQuantity()) {
                    item.setQuantity(cacheItem.getQuantity());
                    paymentAdapter.notifyDataSetChanged();
                }
                if (!notion.getText().toString().equals("")) {

                }
            }
        });
    }

    void handleDeliveryTime() {
        changingDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chon ngay o day
            }
        });
    }

    void handleCheckValidSaleCode() {
        applySaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saleCodeEdit.getText().toString().equals("")) {
                    saleResult.setText("Vui lòng nhập mã giảm giá");
                    saleResult.setTextColor(Color.BLUE);
                } else {
                    compositeDisposable.add(mFoodAppAPi.checkValidSaleCode(saleCodeEdit.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    givenCheckedSaleCode -> {
                                        if (givenCheckedSaleCode.getSuccessful()) {
                                            saleResult.setText(givenCheckedSaleCode.getSaleCodeModel().getCodeName());
                                            saleResult.setTextColor(Color.BLUE);
                                        }
                                        else
                                        {
                                            saleResult.setText("Mã giảm giá không tồn tại.");
                                            saleResult.setTextColor(Color.RED);
                                        }
                                    },
                                    error -> {
                                        Log.d("Loi", error.getMessage());
                                    }
                            )
                    );

                }
            }
        });
        //saleResult
        //applySaleBtn
    }

    void handleDeliveryType() {
        compositeDisposable.add(mFoodAppAPi.getAllDeliveryType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allType -> {
                            if (allType != null && allType.size() > 0) {
                                String _deliveryName = allType.get(0).getDeliveryName();
                                int _deliveryFee = allType.get(0).getFee();
                                deliveryName.setText(_deliveryName);
                                deliveryPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_deliveryFee));
                                deliveryModel = allType.get(0);
                            }
                        },
                        error -> {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );

        delivery_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == 998) {
                                    Intent intent = result.getData();
                                    Bundle bundle = intent.getExtras();
                                    deliveryModel = (DeliveryModel) bundle.getSerializable("selectedDelivery");
                                    String _deliveryName = deliveryModel.getDeliveryName();
                                    int _deliveryFee = deliveryModel.getFee();
                                    deliveryName.setText(_deliveryName);
                                    deliveryPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_deliveryFee));

                                }
                            }
                        });
            }
        });
    }

    void getNotionContent(TextView textview) {
        View view = getLayoutInflater().inflate(R.layout.adding_notion_sheet, null);

        final BottomSheetDialog _bottomSheetDialog = new BottomSheetDialog(this);
        _bottomSheetDialog.setContentView(view);
        _bottomSheetDialog.show();
        TextView cancelButton = view.findViewById(R.id.cancle_notionSheet);
        TextView doneButton = view.findViewById(R.id.done_notionSheet);
        EditText notionEditText = view.findViewById(R.id.notion_content);
        TextView textCounter = view.findViewById(R.id.textCounter);
        notionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = notionEditText.length();
                String convert = String.valueOf(length);
                textCounter.setText(convert + "/100");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _bottomSheetDialog.dismiss();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notionEditText.getText().toString().equals("")) {
                    textview.setText(notionEditText.getText().toString());
                }
            }
        });
    }

    void HandleAddAdressButton() {
        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityResultLauncher<Intent> addingAddressActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == 999) {
                                    Intent intent = result.getData();
                                    Bundle bundle = intent.getExtras();
                                    if (bundle != null) {
                                        receivedPerson.setText(bundle.getString("name"));
                                        phoneNumber.setText(bundle.getString("phone"));
                                        address.setText(bundle.getString("address"));
                                        locationButton.setVisibility(View.VISIBLE);
                                        nochoiceCaption.setVisibility(View.GONE);
                                        noAddressLabel.setVisibility(View.GONE);
                                    }
                                }
                            }
                        });
                Intent intent = new Intent(PaymentActivity.this, AddressActivity.class);
                addingAddressActivityResultLauncher.launch(intent);
            }


        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityResultLauncher<Intent> addingAddressActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == 999) {
                                    Intent intent = result.getData();
                                    Bundle bundle = intent.getExtras();
                                    if (bundle != null) {
                                        receivedPerson.setText(bundle.getString("name"));
                                        phoneNumber.setText(bundle.getString("phone"));
                                        address.setText(bundle.getString("address"));
                                    }
                                }
                            }
                        });
                Intent intent = new Intent(PaymentActivity.this, AddressActivity.class);
                addingAddressActivityResultLauncher.launch(intent);
            }
        });
    }

}