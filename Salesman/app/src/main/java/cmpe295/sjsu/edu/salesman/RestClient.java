package cmpe295.sjsu.edu.salesman;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.security.auth.callback.Callback;
import retrofit.client.OkClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import static retrofit.RestAdapter.Builder;


public class RestClient {

    private static SalesmanAPI REST_CLIENT;
    private static String ROOT = "http://salesman-betasjsu.rhcloud.com/api/v1";

    static {
        setupRestClient();
    }

    private RestClient() {

    }

    public static SalesmanAPI get() {
        System.out.println("I am in api");
        return REST_CLIENT;
    }



    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()));
                 builder.setLogLevel(RestAdapter.LogLevel.FULL);
        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(SalesmanAPI.class);
    }
}