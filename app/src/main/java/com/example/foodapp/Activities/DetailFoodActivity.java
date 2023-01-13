package com.example.foodapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.Adapter.FoodListAdapter;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.SQLite.FavoriteFoodManagerSqLite;
import com.example.foodapp.Util.GridSpacingItemDecoration;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;
import com.example.foodapp.Util.PermissionAlertDialog;
import com.example.foodapp.Util.SpacingHorizontalItemDecoration;
import com.example.foodapp.Util.VietNameseCurrencyFormat;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DetailFoodActivity extends AppCompatActivity {
    private ImageView back, foodImg;
    private TextView foodName, quantitySold, currentPrice, originalPrice, sale, eaterNumber, expire, preparationTime, description, preservationTool, buyNow;
    private ImageView call, interest;
    private LinearLayout addTocart;
    private RecyclerView RelativefoodListRCV;
    private List<FoodModel> relativeFoods;
    private FoodListAdapter relativeFoodListAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FoodAppApi mFoodAppApi;
    private FavoriteFoodManagerSqLite favoriteFoodManagerSqLite = new FavoriteFoodManagerSqLite(this);
    private CartManagerSqLite cartManagerSqLite = new CartManagerSqLite(this);
    private ItemCartModel itemCartModel;
    private FoodModel selectedFood;
    private final String PHONE_NO = "0862877320";
    private final int PERMISSION_PHONECALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        mappingID();
        intView();
        getDetailFood(foodName.getText().toString());
        createRelativeFoodList(foodName.getText().toString());
        handleButonClick();
    }

    void mappingID() {
        back = findViewById(R.id.back_detailActivity);
        foodImg = findViewById(R.id.foodImageDetail);
        foodName = findViewById(R.id.foodNameDetailActivity);
        quantitySold = findViewById(R.id.quantity_sold_DetailActivity);
        currentPrice = findViewById(R.id.currentPrice_DetailActivity);
        originalPrice = findViewById(R.id.originalPriceDetailActivty);
        sale = findViewById(R.id.discount_DetailActivity);
        eaterNumber = findViewById(R.id.eaterNumber);
        expire = findViewById(R.id.dateNumber);
        preparationTime = findViewById(R.id.preparationTime);
        description = findViewById(R.id.description);
        preservationTool = findViewById(R.id.preserveGuide);
        call = findViewById(R.id.calling);
        interest = findViewById(R.id.interest);
        addTocart = findViewById(R.id.btnAddToCard);
        buyNow = findViewById(R.id.buyNow);
        RelativefoodListRCV = findViewById(R.id.relative_foodRCV);
    }

    void handleButonClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONECALL);
                    }

                } else {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + PHONE_NO));
                    startActivity(i);
                }
            }
        });
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteFoodManagerSqLite.addFavoriteFood(selectedFood);
                // them vao gio hang
                NotificationDialog notificationDialog = new NotificationDialog(DetailFoodActivity.this);
                notificationDialog.setContent("Đã thêm vào mục yêu thích");
                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                notificationDialog.show();

            }
        });
        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartManagerSqLite.addCart(itemCartModel);
                NotificationDialog notificationDialog = new NotificationDialog(DetailFoodActivity.this);
                notificationDialog.setContent("Thêm vào giỏ hàng thành công");
                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                notificationDialog.show();
            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickOpenBottomSheetDialog();
            }
        });
    }

    private void ClickOpenBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.buynow_bottomsheet_dialog, null);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        ImageView foodImage = view.findViewById(R.id.foodImage_buynow);
        TextView foodName_dialog = view.findViewById(R.id.foodName_buynow);
        TextView currentPrice_dialog = view.findViewById(R.id.currentPrice_buyNow);
        TextView originalPrice_dialog = view.findViewById(R.id.originalPrice_buyNow);
        TextView discount_dialog = view.findViewById(R.id.sale_buyNow);
        ImageView close_dialog = view.findViewById(R.id.close_buynow);
        ImageView decrease_dialog = view.findViewById(R.id.decrease_buynow);
        ImageView increase_dialog = view.findViewById(R.id.increase_buyNow);
        TextView quantity_dialog = view.findViewById(R.id.quantityItemCart_buyNow);
        TextView buyNowBtn_dialog = view.findViewById(R.id.btnBuyNow_buyNow);

        if (itemCartModel.getImage().contains("http")) {
            Glide.with(this).load(itemCartModel.getImage()).into(foodImage);
        } else {
            String path= InternetConnection.BASE_URL+"images/"+itemCartModel.getImage();
            Glide.with(this).load(path).into(foodImage);
        }
        foodName_dialog.setText(itemCartModel.getFoodName());
        originalPrice_dialog.setText(VietNameseCurrencyFormat.getVietNameseCurrency(itemCartModel.getPrice()));
        float currentPrice = itemCartModel.getPrice() * (1 - (float) itemCartModel.getDiscount() / 100);
        currentPrice_dialog.setText(VietNameseCurrencyFormat.getVietNameseCurrency(currentPrice));
        discount_dialog.setText("Giảm "+String.valueOf(itemCartModel.getDiscount())+"%");
        quantity_dialog.setText(String.valueOf(itemCartModel.getQuantity()));
        buyNowBtn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemCartModel> _selectedFood = new ArrayList<>();
                _selectedFood.add(itemCartModel);
                Intent intent = new Intent(DetailFoodActivity.this, PaymentActivity.class);
                intent.putParcelableArrayListExtra("foodListChosend", _selectedFood);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
        decrease_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = itemCartModel.getQuantity();
                if (current == 1) {
                    return;
                }
                int previous = current - 1;
                quantity_dialog.setText(String.valueOf(previous));
                itemCartModel.setQuantity(previous);
            }
        });
        increase_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = itemCartModel.getQuantity();
                int next = current + 1;
                itemCartModel.setQuantity(next);
                quantity_dialog.setText(String.valueOf(next));
            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    void intView() {
        Bundle bundle = getIntent().getExtras();
        description.setText(bundle.getString("description"));
        foodName.setText(bundle.getString("foodname"));
        currentPrice.setText(bundle.getString(VietNameseCurrencyFormat.getVietNameseCurrency(Float.parseFloat(bundle.getString("currentprice")))));
        originalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(Float.parseFloat(bundle.getString("originalprice"))));
        quantitySold.setText(String.valueOf(bundle.getInt("quantitySold")));
        sale.setText("Giảm " + String.valueOf(bundle.getInt("sale")) + "%");
        if (bundle.getString("image").contains("http")) {
            Glide.with(getApplicationContext()).load(bundle.getString("image")).into(foodImg);
        } else {
            String path= InternetConnection.BASE_URL+"images/"+bundle.getString("image");
            Glide.with(this).load(path).into(foodImg);
        }

        itemCartModel = new ItemCartModel(
                bundle.getString("foodname"),
                1, (int) Float.parseFloat(bundle.getString("originalprice"))
                , bundle.getInt("sale"), bundle.getString("image")
        );
        selectedFood = new FoodModel(
                bundle.getString("foodname"),
                Float.parseFloat(bundle.getString("originalprice")),
                bundle.getString("image"),
                bundle.getString("description"),
                bundle.getInt("sale"),
                bundle.getInt("quantitySold"),
                0
        );
    }

    void getDetailFood(String foodName) {
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        compositeDisposable.add(mFoodAppApi.getDetalFood(foodName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodDetail -> {
                            if (foodDetail != null) {
                                expire.setText(foodDetail.getExpiration().toString());
                                eaterNumber.setText(String.valueOf(foodDetail.getEaterNumber()) + " người");
                                preparationTime.setText(foodDetail.getPreparationTime());
                                preservationTool.setText(foodDetail.getPreservationGuide());
                                selectedFood.setEaterNumber(foodDetail.getEaterNumber());
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    void createRelativeFoodList(String foodName) {
        mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
        compositeDisposable.add(mFoodAppApi.getRelativeFoods(foodName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allFoods -> {
                            if (allFoods != null) {
                                relativeFoods = new ArrayList<>(allFoods);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailFoodActivity.this, RecyclerView.HORIZONTAL, false);
                                RelativefoodListRCV.setLayoutManager(linearLayoutManager);

                                relativeFoodListAdapter = new FoodListAdapter(DetailFoodActivity.this, relativeFoods, new IClickFoodItemListener() {
                                    @Override
                                    public void onItemClickHandler(FoodModel food) {
                                        Intent intent = new Intent(DetailFoodActivity.this, DetailFoodActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("foodname", food.getName().toString());
                                        bundle.putString("image", food.getImage().toString());
                                        bundle.putString("description", food.getDescription());
                                        bundle.putString("originalprice", String.valueOf(food.getPrice()));
                                        bundle.putInt("sale", food.getDiscount());
                                        bundle.putInt("quantitySold", food.getQuantity());
                                        float currentPrice = food.getPrice() * (1 - (float) food.getDiscount() / 100);
                                        bundle.putString("currentprice", String.valueOf(currentPrice));
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                RelativefoodListRCV.setAdapter(relativeFoodListAdapter);
                                SpacingHorizontalItemDecoration spacingHorizontalItemDecoration = new SpacingHorizontalItemDecoration(30);
                                RelativefoodListRCV.addItemDecoration(spacingHorizontalItemDecoration);
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_PHONECALL) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    PermissionAlertDialog.requestPermissionAgain(DetailFoodActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONECALL);
                } else {
                    PermissionAlertDialog.showAlerDialogWarning(DetailFoodActivity.this);
                }
            } else {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + PHONE_NO));
                startActivity(i);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
