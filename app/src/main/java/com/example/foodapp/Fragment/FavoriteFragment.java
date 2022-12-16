package com.example.foodapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodapp.Activities.DetailFoodActivity;
import com.example.foodapp.Activities.FindItemActivity;
import com.example.foodapp.Activities.MainHomeActivity;
import com.example.foodapp.Adapter.SearchedItemAdapter;
import com.example.foodapp.Iterface.IClickFoodItemListener;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.R;
import com.example.foodapp.SQLite.FavoriteFoodManagerSqLite;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment  {

    private Context context;
    private MainHomeActivity mainHomeActivity;


    TextView deleteAll;

    RecyclerView favoriteFoodsRCV;
    SearchedItemAdapter searchedItemAdapter;
    List<FoodModel> foodList;

    FavoriteFoodManagerSqLite favoriteFoodManagerSqLite;

    public FavoriteFragment() {
        // Required empty public constructor

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            favoriteFoodManagerSqLite.deleteItem(foodList.get(position));
            foodList.remove(position);
            searchedItemAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(context, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            mainHomeActivity = (MainHomeActivity) getActivity();
            favoriteFoodManagerSqLite = new FavoriteFoodManagerSqLite(context);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout view_layout_returnedFood = (LinearLayout) inflater.inflate(R.layout.fragment_favorite, null);
        favoriteFoodsRCV = view_layout_returnedFood.findViewById(R.id.foodList_favoriteFragment);
        deleteAll = view_layout_returnedFood.findViewById(R.id.deleteAll_favoriteFragment);
        initFavoritefoodsRecylceView();
        HandleDeleteAll();
        return view_layout_returnedFood;
    }

    void initFavoritefoodsRecylceView() {
        searchedItemAdapter = new SearchedItemAdapter(foodList, context, new IClickFoodItemListener() {
            @Override
            public void onItemClickHandler(FoodModel food) {
                Intent intent = new Intent(context, DetailFoodActivity.class);
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
        getAllFavoriteFood();
        favoriteFoodsRCV.setAdapter(searchedItemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        favoriteFoodsRCV.setLayoutManager(linearLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(favoriteFoodsRCV);
    }

    void getAllFavoriteFood() {
        foodList = favoriteFoodManagerSqLite.getAllContacts();
        searchedItemAdapter.setData(foodList);
    }
    void HandleDeleteAll()
    {
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteFoodManagerSqLite.deleteALl();
                foodList.clear();
                searchedItemAdapter.notifyDataSetChanged();
            }
        });
    }

}