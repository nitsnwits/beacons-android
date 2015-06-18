package cmpe295.sjsu.edu.salesman;



import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
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

    //2.USERS

    @FormUrlEncoded
    @POST("/users")
    public void registerUser(@Field("first") String first,
                             @Field("last") String last,
                             @Field("email")String email,
                             @Field("password")String password,
                             Callback<RegisterUserResponse> cb);

//    @GET("/users/{userId}")
//    void listUsers(@Path("userId") int id, Callback<> cb);



}
