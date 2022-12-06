package com.example.foodapp.Retrofit;

import com.example.foodapp.Model.DetailProduct;
import com.example.foodapp.Model.FoodModel;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodAppApi {
    @GET("getfood.php")
    public Observable<List<FoodModel>> getAllFood();

    @GET("getPopularFood.php")
    public Observable<List<FoodModel>> getTopPopularFood();

    @POST("searchingFood.php")
    @FormUrlEncoded
    public Observable<List<FoodModel>> searchFood(
            @Field("search") String search
    );

    @POST("getDetailFood.php")
    @FormUrlEncoded
    public Observable<DetailProduct> getDetalFood(
            @Field("foodName") String foodName
    );

    @POST("getRelativeFood.php")
    @FormUrlEncoded
    public Observable<List<FoodModel>>getRelativeFoods(
            @Field("foodName") String foodName
    );
}
