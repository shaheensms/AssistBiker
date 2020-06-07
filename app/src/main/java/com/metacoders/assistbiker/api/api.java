package com.metacoders.assistbiker.api;

import com.metacoders.assistbiker.models.NewsFeedModel;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.models.ResponseModel;
import com.metacoders.assistbiker.models.Response_login;
import com.metacoders.assistbiker.models.Response_register;
import com.metacoders.assistbiker.models.Sent_Response_cart;
import com.metacoders.assistbiker.models.Sent_Response_login;
import com.metacoders.assistbiker.models.Sent_Response_mobile;
import com.metacoders.assistbiker.models.Sent_Response_register;
import com.metacoders.assistbiker.models.testModel;
import com.metacoders.assistbiker.models.zoneResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface api {

    @GET("users")
    Call<List<testModel>> getUsers();
    @POST("customers/login")
    Call<Response_login>postUserLoagin(@Body Sent_Response_login body ) ;
    @POST("customers/create")
    Call<Response_register>postUserRegister(@Body Sent_Response_register body ) ;

    @POST("cart/create")
    Call<Response_register>postCartList(@Body Sent_Response_cart body ) ;

    @GET("customers/newsfeed")
    Call<List<NewsFeedModel>> getNewsFeed ();
    @POST("customers/check_number")
    Call<ResponseModel> checkNumberValid(@Body Sent_Response_mobile body );
    @GET("products")
    Call<List<ProductsModel>> getProducts ();
    @GET("products/latest")
    Call<List<ProductsModel>> getLatestProduct();

    @GET("products/latest")
    Observable<List<ProductsModel>> getLatestProducts();//   Flowable<List<ProductsModel>> getProductss ();
    @GET("customers/newsfeed")
    Observable<List<NewsFeedModel>> getNewsFeeds ();

    @GET("products/{id}")
    Call<List<ProductsModel>> getSingleProduct(@Path("id") int ID);

    @GET("cart/zone")
    Call<List<zoneResponse>> getZone() ;


}
