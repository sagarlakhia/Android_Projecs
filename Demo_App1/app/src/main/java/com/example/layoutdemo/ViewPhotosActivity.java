package com.example.layoutdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class ViewPhotosActivity extends AppCompatActivity {



    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPAdapter viewPAdapter;
    private int id;
    private Context mContext;
    private List<PhotoItem> photoItems = new ArrayList<PhotoItem>();
    private GridFragment gridFragment;
    private CardFragment cardFragment;
    private MultiPaneFragment multiPaneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id = getIntent().getIntExtra("albumId",0);

        new GetPhotosAsyncTask().execute("https://jsonplaceholder.typicode.com/photos");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.userInfo) {

            new AlertDialog.Builder(this)
                    .setTitle("Activity Information")
                    .setMessage("There are three Fragments. \n 1) Card Fragment, which shows simple cards of thumbnail and title using Recyclerview. \n" +
                            "2)Grid Fragment, which uses a Gridview to display all the photos, Long click on an item to preview it's url item and click on the " +
                            "image to download the image and display it on the new activity. \n 3) Multi-Pane fragment, click on the lower screen images and see it's corresponding url image on upper screen \n" +
                            "Also used Picasso library to load images.")
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                                                    }
                    })
                    .setCancelable(true)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }



    private class GetPhotosAsyncTask extends AsyncTask<String,Void,List<PhotoItem>> {


       ProgressDialog progressDialog ;

        @Override
        protected void onPreExecute() {
            /*progressDialog.setMessage("Loading Data");
            progressDialog.setCancelable(false);
            progressDialog.show();*/

            progressDialog = new ProgressDialog(ViewPhotosActivity.this);

            progressDialog.setMessage("Loading data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<PhotoItem> doInBackground(String... params) {

            if(ConnectionCheck.hasInternetAccess(ViewPhotosActivity.this)) {

                try {
                    URL url = new URL(params[0]);
                    InputStream inputStream = url.openConnection().getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line);
                    }

                    inputStream.close();
                    String result = buffer.toString();


                    try {
                        JSONArray jsonArray = new JSONArray(buffer.toString());

                        List<PhotoItem> tempItems = new ArrayList<PhotoItem>();

                        for (int index = 0; index < jsonArray.length(); index++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(index);

                            int albumId = jsonObject.getInt("albumId");

                            if (id == albumId) {


                                String urlphoto = jsonObject.getString("url");
                                String title = jsonObject.getString("title");
                                int id = jsonObject.getInt("id");
                                String thumbnailURL = jsonObject.getString("thumbnailUrl");


                                tempItems.add(new PhotoItem(urlphoto, title, thumbnailURL, id));

                            }


                        }


                        return tempItems;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<PhotoItem> photoItems) {

            if(progressDialog!=null) {
                progressDialog.dismiss();

            }

            if(photoItems==null)
            {
                Toast.makeText(ViewPhotosActivity.this, "Please connect to Internet and restart the application", Toast.LENGTH_LONG).show();
            }
            else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

                tabLayout = (TabLayout) findViewById(R.id.tab);
                viewPager = (ViewPager) findViewById(R.id.viewPager);
                viewPAdapter = new ViewPAdapter(getSupportFragmentManager());

                gridFragment = new GridFragment();
                gridFragment.setPhotoItems(photoItems);

                multiPaneFragment = new MultiPaneFragment();
                multiPaneFragment.setPhotoItems(photoItems);


                cardFragment = new CardFragment();
                cardFragment.setPhotoItems(photoItems);

                viewPAdapter.addFragments(cardFragment, "Card View");
                viewPAdapter.addFragments(gridFragment, "Grid View");
                viewPAdapter.addFragments(multiPaneFragment, "Multi-Pane");

                viewPager.setAdapter(viewPAdapter);
                tabLayout.setupWithViewPager(viewPager);


            }

        }
    }



}
