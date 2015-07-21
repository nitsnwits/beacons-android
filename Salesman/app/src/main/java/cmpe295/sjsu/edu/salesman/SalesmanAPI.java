package cmpe295.sjsu.edu.salesman;



import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.pojo.Product;
import cmpe295.sjsu.edu.salesman.pojo.OfferResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by Rucha on 6/16/15.
 */
public interface SalesmanAPI {

    //1.User Login
    @FormUrlEncoded
    @POST("/auth")
    public void loginUser(@Field("email")String email,@Field("password")String password, Callback<LoginUserResponse> cb);

    //2.USERS

    @POST("/users")
    public void registerUser(@Body User user,
                             Callback<RegisterUserResponse> cb);

    //3.Reset Password

    @FormUrlEncoded
    @POST("/auth/reset/password")
    public void resetPwd(@Field("email")String email, Callback<resetPwdResponse> cb);


    //4.Send Password to User

    @GET("/auth/reset/password/{key}")
    public void getPwd(@Path("key") String key,Callback<resetPwdLinkResponse> cb);

    //5.Get all the categories
    @GET("/categories")
    public void getCategories(@Header("Authorization") String accessToken,Callback<ArrayList<categoryResponse>> cb);


    @GET("/users/{userId}")
    public void getUserStatus(@Path("userId")String userId,Callback<userStatusResponse> cb);

    @GET("/users/{userId}/verify")
    public void verifyUser(@Path("userId")String userId,Callback<String> cb);


    //Get all offers

    @GET("/offers")
    public void getOffers(Callback<ArrayList<OfferResponse>> cb);

    //6. Get Offer Details
    @GET("/offers/{offerId}")
    public  void getOfferDetails(@Path("offerId")String offerId, Callback<OfferResponse> cb);

    //7.Get the products on the product fragment
    @GET("/products/search")
    public void searchProduct(@Header("Authorization") String accessToken,@Query("query") String query,Callback<ArrayList<Product>> cb);




}
