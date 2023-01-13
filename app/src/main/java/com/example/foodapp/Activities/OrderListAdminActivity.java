package com.example.foodapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CartHistoryAdapter;
import com.example.foodapp.Iterface.ICLickHistoryCartItemListener;
import com.example.foodapp.Model.CartHistoryItemModel;
import com.example.foodapp.R;
import com.example.foodapp.Retrofit.FoodAppApi;
import com.example.foodapp.Retrofit.RetrofitClient;
import com.example.foodapp.Util.InternetConnection;
import com.example.foodapp.Util.NotificationDialog;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class OrderListAdminActivity extends AppCompatActivity {
    RecyclerView orderAdminRCV;
    ImageView back;

    FoodAppApi mFoodAppApi = RetrofitClient.getInstance(InternetConnection.BASE_URL).create(FoodAppApi.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<CartHistoryItemModel> historyList;
    CartHistoryAdapter cartHistoryAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            historyList.remove(historyList.get(position));
            Log.d("Loi",position+"");
            cartHistoryAdapter.notifyDataSetChanged();
            approveOrder(historyList.get(position).getOrderCode());
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(OrderListAdminActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftLabel("Phê duyệt")
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_check_circle_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_admin);
        back = findViewById(R.id.back_orderAdmin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        orderAdminRCV = findViewById(R.id.orderAdmin);
        initRCV();
    }

    void initRCV() {
        cartHistoryAdapter = new CartHistoryAdapter(historyList, this, new ICLickHistoryCartItemListener() {
            @Override
            public void onClick(CartHistoryItemModel item) {
                Intent intent = new Intent(OrderListAdminActivity.this, HistoryOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("historyOrder", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        orderAdminRCV.setAdapter(cartHistoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        orderAdminRCV.setLayoutManager(linearLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(orderAdminRCV);
        getData();
    }

    void getData() {
        compositeDisposable.add(mFoodAppApi.getUncheckedOrder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allOrder -> {
                            if (allOrder != null && allOrder.size() > 0) {
                                historyList=allOrder;
                                cartHistoryAdapter.setData(historyList);
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }
    void approveOrder(String orderCode)
    {
        compositeDisposable.add(mFoodAppApi.setCheckedOrder(orderCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                       success -> {
                            if (success) {
                                NotificationDialog notificationDialog=new NotificationDialog(this);
                                notificationDialog.setDialogTypeResource(R.drawable.ic_baseline_check_circle_24);
                                notificationDialog.setContent("Phê duyệt đơn hàng thành công");
                                notificationDialog.show();
                            }
                        },
                        error ->
                        {
                            Log.d("Loi", error.getMessage());
                        }
                )
        );
    }
}