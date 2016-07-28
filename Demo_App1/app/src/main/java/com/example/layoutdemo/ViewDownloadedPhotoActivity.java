package com.example.layoutdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.UUID;

public class ViewDownloadedPhotoActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ImageView imageView;
    private TextView title,savedImageLocation;
    private String title_string,imageLoc;
    private Bitmap bitmapOrg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_downloaded_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String downloadUrl = getIntent().getExtras().getString("downloadURL");
        downloadUrl=downloadUrl.replace("http", "https");
        title_string = getIntent().getExtras().getString("title");
        imageView=(ImageView)findViewById(R.id.imageView);
        title=(TextView)findViewById(R.id.titleH);
        savedImageLocation=(TextView)findViewById(R.id.savedImageLocation);
        new DownloadDisplayPhotoAsyncTask().execute(downloadUrl);


    }

    private class DownloadDisplayPhotoAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressDialog progressDialog = new ProgressDialog(ViewDownloadedPhotoActivity.this);

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Downloading selected image");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap image = null;

            if (ConnectionCheck.hasInternetAccess(ViewDownloadedPhotoActivity.this)) {
                try {
                    InputStream inputStream = new java.net.URL(params[0]).openStream();
                    image = BitmapFactory.decodeStream(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return image;
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            progressDialog.dismiss();

            if (bitmap == null) {
                Toast.makeText(ViewDownloadedPhotoActivity.this, "Please connect to Internet and restart the application", Toast.LENGTH_LONG).show();
            }
        else
        {
            bitmapOrg=bitmap;

            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    setViews();
                }
                else
                {
                    ActivityCompat.requestPermissions(ViewDownloadedPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
            else
            {
                setViews();
            }




        }
    }
    }

    public void setViews(){

        String file_name= UUID.randomUUID().toString()+".jpg";
        //MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, file_name, "Download");
        String location= MediaStore.Images.Media.insertImage(getContentResolver(), bitmapOrg, file_name, "Download");

        String loc= getRealPathFromUri(ViewDownloadedPhotoActivity.this,(Uri.parse(location)));
        imageView.setImageBitmap(bitmapOrg);

        title.setText("Title: " + title_string);


        savedImageLocation.setText("File saved at : " + loc);

        Toast.makeText(this,"Image saved",Toast.LENGTH_LONG).show();

    }

    //Referred code


    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
               // Log.v(TAG,"Permission is granted");
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
               return false;
            }
        }
        else {
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

            setViews();
        }
        else {
            Toast.makeText(ViewDownloadedPhotoActivity.this,"You denied permission to save images.",Toast.LENGTH_LONG).show();
        }
    }

}
