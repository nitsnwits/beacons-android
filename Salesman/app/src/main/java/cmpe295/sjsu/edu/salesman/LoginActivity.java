package cmpe295.sjsu.edu.salesman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class LoginActivity extends Activity {

    private EditText usernameET;
    private EditText passwordET;

    private String username;
    private String password;

    private Button login;
    private CheckBox loginCheckBox;
    //Shared Preference:
    private SharedPreferences loginSharedpreferences ;
    private SharedPreferences.Editor loginEditor;
    private Boolean saveLogin;

    private String key;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        TextView forgotPwd = (TextView)findViewById(R.id.resetBtn);
        ImageView backImage = (ImageView)findViewById(R.id.backArrow);


        // Method will be called on Back button click
        backImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });


        forgotPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetPwd(v);
            }

        });

        login = (Button) findViewById(R.id.loginBtn);

        loginCheckBox = (CheckBox)findViewById(R.id.checkBox);
        loginSharedpreferences = getBaseContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginEditor = loginSharedpreferences.edit();
        saveLogin = loginSharedpreferences.getBoolean("saveLogin", false);
        System.out.println("SaveLogin1:" + saveLogin);
        if (saveLogin==true) {

            System.out.println(loginSharedpreferences.getString("username", ""));
            usernameET.setText(loginSharedpreferences.getString("username", ""));
            passwordET.setText(loginSharedpreferences.getString("password", ""));
            loginCheckBox.setChecked(true);

        }


        //sharedpreferences = getBaseContext().getSharedPreferences("salesmanPreference", 0);

    }


@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    //This method is called when user wants to reset the pwd
    public void resetPwd(View view){

        username = usernameET.getText().toString();
        System.out.println("Username:" + username);
       if(username.length()==0) {
           Toast.makeText(getApplicationContext(), "Please enter your email.",
                   Toast.LENGTH_SHORT).show();
           return;
       }




        RestClient.get().resetPwd(username, new Callback<resetPwdResponse>() {
            @Override
            public void success(resetPwdResponse resetPwdresponse, Response response) {

                String message = resetPwdresponse.getMessage();
                final String url = resetPwdresponse.getUrl();


                Toast.makeText(getApplicationContext(), resetPwdresponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
                if(url!=null) {
                    //Linkify
                    final SpannableString s = new SpannableString("Click here to reset password.");
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    s.setSpan(new URLSpan(url),6,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                    //added a TextView
                    final TextView tx1=new TextView(LoginActivity.this);

                    tx1.setText(s);
                    tx1.setAutoLinkMask(RESULT_OK);
                    tx1.setMovementMethod(LinkMovementMethod.getInstance());


                    Linkify.addLinks(s, Linkify.WEB_URLS);

                //Display an alert box with the url
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            LoginActivity.this);

                    // set title   .setView(tx1)
                    alertDialogBuilder.setTitle("Reset Password");

                    // set dialog message
                    alertDialogBuilder
                            .setView(tx1)
                            .show();

                    tx1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String[] urlArr = url.split("/");
                            key = urlArr[urlArr.length-1];
                            RestClient.get().getPwd(key, new Callback<resetPwdLinkResponse>() {
                                @Override
                                public void success(resetPwdLinkResponse resetPwdLinkResponse, Response response) {
                                    Toast.makeText(getApplicationContext(),resetPwdLinkResponse.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
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
                                }
                            });

                        }
                    });


//                    // create alert dialog
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//
//                    // show it
//                    alertDialog.show();
//                    Toast.makeText(getApplicationContext(),"Please click on this url to reset your password",
//                            Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), url,
//                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
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
            }
        });
    }


    //This method is called when user submits login button
    public void authenticateUser(View view) {
        username = usernameET.getText().toString();
        System.out.println("Username:" + username);
        password = passwordET.getText().toString();
        System.out.println("Pwd:" + password);

        if(loginCheckBox.isChecked()){
            loginEditor.putString("username", username);
            loginEditor.putString("password", password);
            loginEditor.putBoolean("saveLogin", true);
            loginEditor.commit();


        }else {
            loginEditor.clear();
            loginEditor.commit();
        }

            System.out.println("SaveLogin2:" + loginCheckBox.isChecked());

            RestClient.get().loginUser(username, password, new Callback<LoginUserResponse>() {
                @Override
                public void success(LoginUserResponse loginUserResponse, Response response) {
                    String uid = loginUserResponse.getUserId(); //for preference
                    String accessToken = loginUserResponse.getAccessToken();//for preference
                    //now save the preferences using ediot object
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("userId",uid);
//                editor.putString("accessToken",accessToken);
//                editor.commit();
                    System.out.println("I am success");
                    Toast.makeText(getApplicationContext(), "Hello" + username + "!!",
                            Toast.LENGTH_SHORT).show();
                    navigatetoHomeActivity();
                }

                @Override
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
//                    System.out.println("I am error");
//                    Toast.makeText(getApplicationContext(), "Incorrect username or password!",
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }


    private void setupVariables() {
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        login = (Button) findViewById(R.id.loginBtn);
    }

    /**
     * Method which navigates from Login Activity to Home Activity
     */
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }


    public void registerUser(View view){
        Intent registerUserIntent = new Intent(getApplicationContext(),RegistrationActivity .class);
        startActivity(registerUserIntent);
    }

}

