package cmpe295.sjsu.edu.salesman;



import java.util.ArrayList;
import java.util.List;

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


}
