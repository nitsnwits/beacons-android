package cmpe295.sjsu.edu.salesman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PickYourFavouritesActivity extends Activity {

    SharedPreferences userSharedpreferences ;
    String userId;
    String accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_your_favourites);
        userSharedpreferences = getBaseContext().getSharedPreferences("userPrefs",0);
        userId = userSharedpreferences.getString("userId","default");
        accessToken = userSharedpreferences.getString("accessToken","default");
        RestClient.get().getCategories(accessToken, new Callback<ArrayList<categoryResponse>>() {
            @Override
            public void success(ArrayList<categoryResponse> categoryResponses, Response response) {
                ScrollView sv = new ScrollView(getApplicationContext());
                LinearLayout ll = (LinearLayout)findViewById(R.id.pickcategory);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setVerticalScrollBarEnabled(true);
                //sv.addView(ll);
                int count = categoryResponses.size(); //total number of items
                System.out.println("Total categories::" + count);
                Toast.makeText(getApplicationContext(), "Success in pick your favorite categories",
                        Toast.LENGTH_SHORT).show();
               for(categoryResponse cr : categoryResponses){

                   customCheckbox chk = new customCheckbox(getApplicationContext());
                    chk.setText(cr.getName());
                    chk.setTextColor(Color.BLACK);
                    ll.addView(chk);
                }

//                Button button1 = new Button(getApplicationContext());
//                button1.setText("Skip");
//
//                button1.setBackgroundColor(Color.argb(255, 0, 188, 212));
//                ll.addView(button1, lp);
                Button button2 = new Button(getApplicationContext());
                button2.setText("Next");
                button2.setBackgroundColor(Color.argb(255, 0, 188, 212));
                ll.addView(button2, lp);


//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//                        startActivity(homeIntent);
//                    }
//                });

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(homeIntent);
                    }
                });

                //  sv.removeAllViews();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_your_favourites, menu);
        userId = userSharedpreferences.getString("userId","default");
        accessToken = userSharedpreferences.getString("accessToken","default");
        Log.d("SalesmanPref:userId", userId);
        Log.d("SalesmanPref:aT", accessToken);
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

    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(homeIntent);
    }

}
