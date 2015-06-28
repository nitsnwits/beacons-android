package cmpe295.sjsu.edu.salesman;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;
    private Button login;
    private CheckBox checkBox;
    //Shared Preference:
    private SharedPreferences loginSharedpreferences ;
    private SharedPreferences.Editor loginEditor;
    private Boolean saveLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.loginBtn);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
         loginSharedpreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginEditor = loginSharedpreferences.edit();

        saveLogin = loginSharedpreferences.getBoolean("saveLogin",false);
        if(saveLogin == true){
            username.setText(loginSharedpreferences.getString("username",""));
            password.setText(loginSharedpreferences.getString("password",""));
            checkBox.setChecked(true);
        }

        //sharedpreferences = getBaseContext().getSharedPreferences("salesmanPreference", 0);

    }

//    public void addListenerOnCheckBox(){
//        checkBox = (CheckBox)findViewById(R.id.checkBox);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //is checkboc checked?
//                if(((CheckBox) v ).isChecked()){
//                    Toast.makeText(getApplicationContext(), "Checkbox is checked..",
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//    }

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
        //Get the access token for the user

        EditText usernameEditText = (EditText) findViewById(R.id.usernameET);
        final String username = usernameEditText.getText().toString();
        System.out.println("Username:" + username);

        RestClient.get().resetPwd(username, new Callback<resetPwdResponse>() {
            @Override
            public void success(resetPwdResponse resetPwdresponse, Response response) {
               //String url = resetPwdresponse.getUrl();
                String message = resetPwdresponse.getMessage();
                Toast.makeText(getApplicationContext(), resetPwdresponse.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Please verify your email before resetting the pwd",
                        Toast.LENGTH_SHORT).show();
                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                Log.v("failure", json.toString());
                if(error.getResponse()!=null){
                    System.out.println(error.getResponse());
                    resetPwdResponse body = (resetPwdResponse) error.getBodyAs(resetPwdResponse.class);
                   // System.out.print("Fail  errorbody" + body.getUrl());

                }
            }
        });
    }

    //This method is called when user submits login button
    public void authenticateUser(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameET);
        final String username = usernameEditText.getText().toString();
        System.out.println("Username:" + username);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordET);
        String password = passwordEditText.getText().toString();
        System.out.println("Pwd:" + password);

        if(checkBox.isChecked()== true){
            loginEditor.putString("username",username);
            loginEditor.putString("password",password);
            loginEditor.putBoolean("saveLogin",true);
        } else{
            loginEditor.clear();
            loginEditor.commit();
        }

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
                System.out.println("I am error");
                Toast.makeText(getApplicationContext(), "Incorrect username or password!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);
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

