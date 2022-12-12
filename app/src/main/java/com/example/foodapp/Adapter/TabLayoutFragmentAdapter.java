package com.example.foodapp.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodapp.Enum.Categories;
import com.example.foodapp.Fragment.FoodCategoryFragment;

public class TabLayoutFragmentAdapter extends FragmentStateAdapter {

    private String categoryType;

    public TabLayoutFragmentAdapter(@NonNull FragmentActivity fragmentActivity, String CategoryType) {
        super(fragmentActivity);
        this.categoryType = CategoryType;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return FoodCategoryFragment.newInstance(Categories.NOODLE);
            case 1:
                return FoodCategoryFragment.newInstance(Categories.FASTFOOD);
            case 2:
                return FoodCategoryFragment.newInstance(Categories.BEVERAGE);
            case 3:
                return FoodCategoryFragment.newInstance(Categories.RICE);
            case 4:
                return FoodCategoryFragment.newInstance(Categories.MEATANDFISH);
            case 5:
                return FoodCategoryFragment.newInstance(Categories.DESSERT);
            default:
                return  FoodCategoryFragment.newInstance(Categories.NOODLE);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
