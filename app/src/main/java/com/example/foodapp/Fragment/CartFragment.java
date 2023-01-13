package com.example.foodapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodapp.Activities.CartActivity;
import com.example.foodapp.Activities.MainHomeActivity;
import com.example.foodapp.Activities.PaymentActivity;
import com.example.foodapp.Adapter.CartListAdapter;
import com.example.foodapp.Iterface.SQliteInterface.ISqliteLisener;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.SQLite.CartManagerSqLite;
import com.example.foodapp.SQLite.FavoriteFoodManagerSqLite;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;
import com.example.foodapp.Util.VietNameseCurrencyFormat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    Context context;
    MainHomeActivity mainHomeActivity;
    CartManagerSqLite cartManagerSqLite;

    TextView modifier, deleteChoice;
    RecyclerView foodListRCV;
    LinearLayout noneOfFood, buyBtn;
    CheckBox checkAllFood;
    TextView totalPrice, savingPrice, numberOfSelection;
    LinearLayout paymentState, modifierState;

    CartListAdapter cartListAdapter;

    List<ItemCartModel> foodList;

    int totalProduct=0;
    private FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    FavoriteFoodManagerSqLite favoriteFoodManagerSqLite;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            mainHomeActivity = (MainHomeActivity) getActivity();
            cartManagerSqLite = new CartManagerSqLite(mainHomeActivity);
            favoriteFoodManagerSqLite = new FavoriteFoodManagerSqLite(context);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout view_layout = (RelativeLayout) inflater.inflate(R.layout.fragment_cart, null);
        mappingID(view_layout);
        initRecycleView();
        setPrice();
        HanleCheckAllItem();
        handleModierClick();
        HandleBuyButton();
        return view_layout;
    }

    void mappingID(RelativeLayout viewGroup) {
        modifierState = viewGroup.findViewById(R.id.modifyState_cartFragemnt);
        paymentState = viewGroup.findViewById(R.id.paymentState_cartFragment);
        modifier = viewGroup.findViewById(R.id.modify_CartFragment);
        foodListRCV = viewGroup.findViewById(R.id.allFoof_CartFragment);
        checkAllFood = viewGroup.findViewById(R.id.totalCheckbox_cartFragment);
        noneOfFood = viewGroup.findViewById(R.id.noneOfFood_cartFragment);
        buyBtn = viewGroup.findViewById(R.id.buyItembtn_cartFragment);
        totalPrice = viewGroup.findViewById(R.id.total_moneyCartFragment);
        savingPrice = viewGroup.findViewById(R.id.saving_money_cartFragment);
        numberOfSelection = viewGroup.findViewById(R.id.totalFood_CartFragment);
        deleteChoice = viewGroup.findViewById(R.id.delete_cartFragment);
    }

    void HanleCheckAllItem() {
        checkAllFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < foodList.size(); i++) {
                        foodList.get(i).setSelected(true);
                        numberOfSelection.setText(String.valueOf(foodList.size()));
                    }
                } else {
                    for (int i = 0; i < foodList.size(); i++) {
                        foodList.get(i).setSelected(false);
                    }
                }
                cartListAdapter.notifyDataSetChanged();
            }
        });
    }

    void handleModierClick() {
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modifier.getText().toString().equals("Sửa")) {
                    modifier.setText("Xong");
                    paymentState.setVisibility(View.GONE);
                    modifierState.setVisibility(View.VISIBLE);
                } else {
                    modifier.setText("Sửa");
                    paymentState.setVisibility(View.VISIBLE);
                    modifierState.setVisibility(View.GONE);
                }
            }
        });

        deleteChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).isSelected()) {
                        cartManagerSqLite.deleteItem(foodList.get(i));
                        foodList.remove(foodList.get(i));
                        i--;
                        counter++;
                    }
                }
                cartListAdapter.notifyDataSetChanged();
                if (counter > 0) {
                    setPrice();
                    totalProduct=0;
                    numberOfSelection.setText("(0)");
                    NotificationDialog dialog=new NotificationDialog(context);
                    dialog.setContent("Đã xóa "+ counter+" sản phẩm");
                    dialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                    dialog.show();
                }
            }
        });
    }

    public void setPrice() {
        float _totalPrice = 0;
        float _savingMoney = 0;
        if (foodList != null) {
            for (int position = 0; position < foodList.size(); position++) {
                ItemCartModel item = foodList.get(position);
                if(item.isSelected())
                {
                    if (foodList.get(position).getDiscount() != 0) {
                        _savingMoney += (((float) item.getDiscount() / 100) * item.getPrice()) * item.getQuantity();
                    }
                    float currentPrice = item.getPrice() * (1 - (float) item.getDiscount() / 100);
                    _totalPrice += currentPrice * item.getQuantity();
                }
            }
        }
        totalPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_totalPrice));
        savingPrice.setText(VietNameseCurrencyFormat.getVietNameseCurrency(_savingMoney));
    }


    void initRecycleView() {
        cartListAdapter = new CartListAdapter(foodList, context, new ISqliteLisener() {
            @Override
            public void updateQuantity(ItemCartModel item, int newQuantity) {
                cartManagerSqLite.updateQuantity(item, newQuantity);
                setPrice();
            }

            @Override
            public void deleteItems(List<ItemCartModel> items) {
                cartManagerSqLite.deleteSomeItems(items);
            }

            @Override
            public void selectedItem(int index) {
                if(foodList.get(index).isSelected())
                {
                    totalProduct+=1;
                    numberOfSelection.setText("("+totalProduct+")");
                }
                else
                {
                    if(totalProduct>0)
                    {
                        totalProduct-=1;
                    }
                    numberOfSelection.setText("("+totalProduct+")");
                }
                setPrice();
            }
        });
        foodListRCV.setAdapter(cartListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        foodListRCV.setLayoutManager(linearLayoutManager);
        setDataForRecycleView();
    }

    void setDataForRecycleView() {
        foodList = cartManagerSqLite.getAllContacts();
        cartListAdapter.setData(foodList);

        if (foodList == null || foodList.size() == 0) {
            noneOfFood.setVisibility(View.VISIBLE);
            foodListRCV.setVisibility(View.GONE);
        } else {
            foodListRCV.setVisibility(View.VISIBLE);
            noneOfFood.setVisibility(View.GONE);
        }
    }

    void HandleBuyButton() {
        //Neu Chua co don hang Toast len ban chua chon san pham

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemCartModel> temp = new ArrayList<>();
                int counter = 0;
                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).isSelected()) {
                        temp.add(foodList.get(i));
                        counter++;
                    }
                }
                if (counter > 0) {
                    Intent intent = new Intent(context, PaymentActivity.class);
                    intent.putParcelableArrayListExtra("foodListChosend", temp);
                    startActivity(intent);
                }
            }
        });

    }

}