package cmpe295.sjsu.edu.salesman;



import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by Rucha on 6/16/15.
 */
public interface SalesmanAPI {

    //1.AUTH
    @FormUrlEncoded
    @POST("/auth")
    public void loginUser(@Field("email")String email,@Field("password")String password, Callback<LoginUserResponse> cb);

    //Ask neeraj regarding forgot password
    @FormUrlEncoded
    @POST("/auth/reset/password")
    public void resetPwd(@Field("email")String email, Callback<resetPwdResponse> cb);


    //2.USERS

    @POST("/users")
    public void registerUser(@Body User user,
                             Callback<RegisterUserResponse> cb);

    @GET("/users/{userId}")
    public void getUserStatus(@Path("userId")String userId,Callback<userStatusResponse> cb);

    @GET("/users/{userId}/verify")
    public void verifyUser(@Path("userId")String userId,Callback<String> cb);


}
