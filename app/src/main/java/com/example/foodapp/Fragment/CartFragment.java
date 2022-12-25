package com.example.foodapp.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.foodapp.Activities.MainHomeActivity;
import com.example.foodapp.Adapter.CartListAdapter;
import com.example.foodapp.Iterface.SQliteInterface.ISqliteLisener;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.CartManagerSqLite;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    Context context;
    MainHomeActivity mainHomeActivity;
    CartManagerSqLite cartManagerSqLite;

    TextView modifier;
    RecyclerView foodListRCV;
    LinearLayout noneOfFood, buyBtn;
    CheckBox checkAllFood;
    TextView totalPrice, savingPrice, numberOfSelection;
    LinearLayout paymentState, modifierState;

    CartListAdapter cartListAdapter;

    List<ItemCartModel> foodList;

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
    }

    public void setPrice() {
        float _totalPrice = 0;
        float _savingMoney = 0;
        if (foodList != null) {
            for (int position = 0; position < foodList.size(); position++) {
                ItemCartModel item = foodList.get(position);
                if (foodList.get(position).getDiscount() != 0) {
                    _savingMoney += ((float) item.getDiscount() / 100) * item.getPrice();
                }
                float currentPrice = item.getPrice() * (1 - (float) item.getDiscount() / 100);
                _totalPrice += currentPrice;
            }
        }
        totalPrice.setText(String.valueOf(_totalPrice));
        savingPrice.setText(String.valueOf(_savingMoney));
    }

    void HanleCheckAllItem() {
        checkAllFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < foodList.size(); i++) {
                        foodList.get(i).setSelected(true);
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
                setPrice();
            }

            @Override
            public void selectedItem(int index) {

            }
        });
        foodListRCV.setAdapter(cartListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        foodListRCV.setLayoutManager(linearLayoutManager);
        setDataForRecycleView();
    }
    void setDataForRecycleView()
    {
        foodList=cartManagerSqLite.getAllContacts();
        cartListAdapter.setData(foodList);
        if (foodList == null || foodList.size() == 0) {
            noneOfFood.setVisibility(View.VISIBLE);
            foodListRCV.setVisibility(View.GONE);
        } else {
            foodListRCV.setVisibility(View.VISIBLE);
            noneOfFood.setVisibility(View.GONE);
        }
    }
}