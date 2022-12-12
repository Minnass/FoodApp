package com.example.foodapp.Retrofit;

import com.example.foodapp.Model.DetailProduct;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.LoginModel.LoginModel;
import com.example.foodapp.Model.RegisterModel.RegisterResponse;


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
    @POST("getCategory.php")
    @FormUrlEncoded
    public  Observable<List<FoodModel>>getCategories(
            @Field("category") String category
    );

    //USER REGION
    @POST("login.php")
    @FormUrlEncoded
    public Observable<LoginModel>checkLogin(
            @Field("userName") String userName,
            @Field("passWord") String passWord
    );
    @POST("registerAccount.php")
    @FormUrlEncoded
    public Observable<RegisterResponse> registerAccount(
            @Field("type") String type,
            @Field("userName") String userName,
            @Field("passWord") String passWord,
            @Field("email") String email
    );
    @POST ("sendPersonalInfo.php")
    public Observable<Boolean> sendPersonalInfo(
            @Field("name") String name,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("sex") String sex,
            @Field("address") String address
    );
}
