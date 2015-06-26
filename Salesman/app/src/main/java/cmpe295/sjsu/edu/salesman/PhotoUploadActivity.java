package cmpe295.sjsu.edu.salesman;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;


public class PhotoUploadActivity extends ActionBarActivity {
    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        imageView = (ImageView) findViewById(android.R.id.icon);
    }

    public void pickPhoto(View view) {
        //TODO: launch the photo picker
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            try {
                InputStream inputStream = getBaseContext().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

//
        }
    }

    private Bitmap getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    public void uploadPhoto(View view) {
        try {
            executeMultipartPost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void executeMultipartPost() throws Exception {

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

            Bitmap bitmap = drawable.getBitmap();

            bitmap.compress(CompressFormat.PNG, 50, bos);

            byte[] data = bos.toByteArray();

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost postRequest = new HttpPost(

                    "http://salesman-betasjsu.rhcloud.com/api/v1/files/upload_file.php");

            String fileName = String.format("File_%d.png",new Date().getTime());
            ByteArrayBody bab = new ByteArrayBody(data, fileName);

            // File file= new File("/mnt/sdcard/forest.png");

            // FileBody bin = new FileBody(file);

            MultipartEntity reqEntity = new MultipartEntity(

                    HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("file", bab);

            postRequest.setEntity(reqEntity);
            int timeoutConnection = 60000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpConnectionParams.setTcpNoDelay(httpParameters, true);

            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader reader = new BufferedReader(new InputStreamReader(

                    response.getEntity().getContent(), "UTF-8"));

            String sResponse;

            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {

                s = s.append(sResponse);

            }

            System.out.println("Response: " + s);

        } catch (Exception e) {

            // handle exception here
            e.printStackTrace();

            // Log.e(e.getClass().getName(), e.getMessage());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_upload, menu);
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

    public void skipPhoto(View view){
        Intent skipPhotoIntent = new Intent(getApplicationContext(),PickYourFavouritesActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(skipPhotoIntent);
    }


}
