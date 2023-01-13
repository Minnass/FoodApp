package com.example.foodapp.Retrofit;

import com.example.foodapp.Model.CartHistoryItemModel;
import com.example.foodapp.Model.DeliveryModel;
import com.example.foodapp.Model.DetailProduct;
import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.GivenSaleCodeModel;
import com.example.foodapp.Model.LoginModel.ChangingPasswordModel;
import com.example.foodapp.Model.LoginModel.LoginModel;
import com.example.foodapp.Model.RegisterModel.RegisterResponse;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;
import com.example.foodapp.Model.UploadedImageModel;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;


import kotlin.jvm.JvmMultifileClass;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FoodAppApi {
    @GET("getAllDeliveryType.php")
    public Observable<List<DeliveryModel>> getAllDeliveryType();

    @GET("getfood.php")
    public Observable<List<FoodModel>> getAllFood();

    @GET("getPopularFood.php")
    public Observable<List<FoodModel>> getTopPopularFood();

    @GET("getOrderAdmin.php")
    public Observable<List<CartHistoryItemModel>>getUncheckedOrder();

    @POST("setCheckedOrder.php")
    @FormUrlEncoded
    public  Observable<Boolean> setCheckedOrder(
      @Field("orderCode") String orderCode
    );

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
    public Observable<List<FoodModel>> getRelativeFoods(
            @Field("foodName") String foodName
    );

    @POST("getCategory.php")
    @FormUrlEncoded
    public Observable<List<FoodModel>> getCategories(
            @Field("category") String category
    );

    //USER REGION
    @POST("login.php")
    @FormUrlEncoded
    public Observable<LoginModel> checkLogin(
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

    @POST("sendPersonalInfo.php")
    @FormUrlEncoded
    public Observable<Boolean> sendPersonalInfo(
            @Field("email") String email,
            @Field("name") String name,
            @Field("dateofbirth") String dateofbirth,
            @Field("sex") String sex,
            @Field("address") String address
    );

    @POST("changingPassword.php")
    @FormUrlEncoded
    public Observable<ChangingPasswordModel> ChangingPassword(
            @Field("email") String email,
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword
    );

    @POST("sendEmail.php")
    @FormUrlEncoded
    public Observable<Boolean> getForgotPassword(
            @Field("email") String email
    );

    @POST("checkSaleCode.php")
    @FormUrlEncoded
    public Observable<GivenSaleCodeModel> checkValidSaleCode(
            @Field("code") String code
    );

    @POST("getLoginType.php")
    @FormUrlEncoded
    public Observable<String> getLoginType(
            @Field("email") String email
    );

    @POST("getHistoryOrder.php")
    @FormUrlEncoded
    public Observable<List<CartHistoryItemModel>> getHistoryOrders(
            @Field("userID") int userID,
            @Field("startDate") String startDate,
            @Field("endDate") String endDate
    );

    @POST("getHistoryOrderedFoodList.php")
    @FormUrlEncoded
    public Observable<List<ItemCartModel>> getHistoryOrderedFoodList(
            @Field("orderCode") String orderCode
    );

    @POST("addHistoryOrder.php")
    @FormUrlEncoded
    public Observable<Boolean> addHistoryOrder(
            @Field("userID") int userID,
            @Field("orderCode") String orderCode,
            @Field("orderDate") String orderDate,
            @Field("totalPrice") float totalPrice,
            @Field("receivedUser") String receivedUser,
            @Field("phoneNumber") String phoneNumber,
            @Field("address") String address,
            @Field("saleCode") int saleCode,
            @Field("deliveryType") String deliveryType,
            @Field("paymentType") String paymentType,
            @Field("deliveryFee") int deliveryFee
    );

    @POST("sendOrderDetail.php")
    @FormUrlEncoded
    public Observable<Boolean> sendOrderedFoodDetail(
            @Field("orderCode") String orderCode,
            @Field("foodName") String foodName,
            @Field("quantity") int quantity,
            @Field("price") float price,
            @Field("discount") int discount,
            @Field("image") String image

    );

    @POST("filterFood.php")
    @FormUrlEncoded
    public Observable<List<FoodModel>> filterFood(
            @Field("FirstKeyword") String FirstKeyword,
            @Field("CategoryKeyword") String CategoryKeyword,
            @Field("OptionKeyword") String OptionKeyword
    );

    @POST("checkExistEmail.php")
    @FormUrlEncoded
    public Observable<Boolean> checkExistEmail(
            @Field("email") String email
    );


    @POST("loginWithGoogle.php")
    @FormUrlEncoded
    public Observable<LoginModel> loginWithGoogle(
            @Field("email") String email
    );

    @POST("deleteFood.php")
    @FormUrlEncoded
    public Observable<Boolean> deleteFood(
            @Field("foodName") String foodName
    );

    @POST("addNewFood.php")
    @FormUrlEncoded
    public Observable<Boolean> addNewFood(
            @Field("foodName") String foodName,
            @Field("image") String image,
            @Field("price") float price,
            @Field("discription") String discription,
            @Field("category") String category,
            @Field("quantitysold") int quantitysold,
            @Field("discount") int discount,
            @Field("eaterNumber") int eaterNumber,
            @Field("expiration") String expiration,
            @Field("preparationTime") 	String 	preparationTime,
            @Field("preservationGuide") String preservationGuide
    );


    @Multipart
    @POST("uploadImage.php")
    Call<UploadedImageModel> uploadFile(@Part MultipartBody.Part file);

}
