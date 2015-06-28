package cmpe295.sjsu.edu.salesman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RegistrationActivity extends Activity {

    private EditText first;
    private EditText last;
    private EditText password;
    private EditText email;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupVariables();
    }

    public void setupVariables(){
        first = (EditText) findViewById(R.id.editText_first);
        last = (EditText) findViewById(R.id.editText_last);
        password = (EditText) findViewById(R.id.editText_password);
        email = (EditText) findViewById(R.id.editText_email);
        register = (Button) findViewById(R.id.registerBtn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Create user
    //This is giving back the correct json but still not  a success.because we have to make two internal rest calls one for
    // sending the email to user and other with email verified.
    public void createUser(View view) {
        String firstStr = first.getText().toString();
        String lastStr = last.getText().toString();
        String pwd = password.getText().toString();
        String emailStr = email.getText().toString();
        Name name1 = new Name();
        User user1 = new User();


        name1.setFirst(firstStr);
        name1.setLast(lastStr);
        user1.setName(name1);
        user1.setPassword(pwd);
        user1.setEmail(emailStr);



        System.out.println("User is::" + firstStr + lastStr + pwd + emailStr);


        RestClient.get().registerUser(user1, new Callback<RegisterUserResponse>() {

            @Override
            public void success(RegisterUserResponse registerUserResponse, Response response) {

//                System.out.println("user created!!");

//                navigatetoHomeActivity();

                Toast.makeText(getApplicationContext(), "Welcome to Salesman!",
                        Toast.LENGTH_SHORT).show();
               // System.out.println("user created!!");
             //   navigatetoPhotoUplaodActivity();
                //navigatetoPhotoUplaodActivity();

                navigatetoPickYourFavouritesActivity();

            }


            @Override

//            public void failure(RetrofitError error) {
//                System.out.println("User creation errro");
//                error.getBody();

            public void failure(RetrofitError error) {

                RestError body = (RestError) error.getBodyAs(RestError.class);
                //dynamic error handling
                if(body.errorCode==400){
                    Toast.makeText(getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==401){
                    Toast.makeText(getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==404){
                    Toast.makeText(getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==500){
                    Toast.makeText(getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                if(body.errorCode==503){
                    Toast.makeText(getApplicationContext(), body.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
//                System.out.println("User creation error");
//
//                Toast.makeText(getApplicationContext(), "Sorry not able to create a user!",
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //After the registration the user navigates to photo upload activity
    /**
     * Method which navigates from Registration Activity to Home Activity
     */
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

//    /**
//     * Method which navigates from Registration Activity to Home Activity
//     */
//    public void navigatetoPhotoUplaodActivity(){
//        Intent photoIntent = new Intent(getApplicationContext(),PhotoUploadActivity.class);
////        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(photoIntent);
//    }

    public void navigatetoPickYourFavouritesActivity(){
        Intent favouritesIntent = new Intent(getApplicationContext(),PickYourFavouritesActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(favouritesIntent);
    }
    /**
     * Method which navigates from Registration Screen to Offer Screen
     */
    public void offerDetails(View view){
        Intent offerDetailsIntent = new Intent(getApplicationContext(),OfferActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(offerDetailsIntent);
    }


}
