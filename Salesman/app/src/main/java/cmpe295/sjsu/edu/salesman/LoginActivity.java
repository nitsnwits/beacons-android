package cmpe295.sjsu.edu.salesman;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;


public class LoginActivity extends ActionBarActivity {

    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.loginBtn);
      //  setupVariables();
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


    //This method is called when user submits login button
    public void authenticateUser(View view) {

        EditText usernameEditText = (EditText) findViewById(R.id.usernameET);
        final String username = usernameEditText.getText().toString();
        System.out.println("Username:" + username);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordET);
        String password = passwordEditText.getText().toString();
        System.out.println("Pwd:" + password);

        RestClient.get().loginUser(username, password, new Callback<LoginUserResponse>() {
            @Override
            public void success(LoginUserResponse loginUserResponse, Response response) {
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

