package com.example.layoutdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class ViewAlbumsActivity extends AppCompatActivity {

    private ListView albumListView;
    List<AlbumItem>  albumItems = new ArrayList<AlbumItem>();
    private int id;
    private String name;
    TextView albTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_albums);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        albumListView=(ListView)findViewById(R.id.albumListView);

        Intent intent = getIntent();
        id=intent.getIntExtra("id", 0);
        name=intent.getStringExtra("name");

        albTitle = (TextView)findViewById(R.id.albTitle);

        new GetAlbumsAsyncTask().execute("https://jsonplaceholder.typicode.com/albums");
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
                    .setMessage("Displaying selected user's albums. \nUsed Listview to display and Asynctask to get data from server")
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setCancelable(true)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }



    private class CustomListAlbumAdapter extends ArrayAdapter<AlbumItem> {

        public CustomListAlbumAdapter(Context context) {
            super(context, R.layout.album_row,albumItems);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View albumItemView = convertView;
            if(albumItemView==null){
                albumItemView = getLayoutInflater().inflate(R.layout.album_row,parent,false);

            }
            AlbumItem album = albumItems.get(position);

            ImageView imgThumb = (ImageView)albumItemView.findViewById(R.id.albumThumbnail);
            imgThumb.setImageResource(album.getAlbumThumbnail());

            TextView title = (TextView)albumItemView.findViewById(R.id.albumTitle);
            title.setText(album.getAlbumTitle());

            return albumItemView;
        }


    }


    private class GetAlbumsAsyncTask extends AsyncTask<String,Void, String>{

        private ProgressDialog progressDialog = new ProgressDialog(ViewAlbumsActivity.this);
        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Loading Data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            try {

                if (ConnectionCheck.hasInternetAccess(ViewAlbumsActivity.this)) {


                URL url = new URL(params[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                inputStream.close();
                return buffer.toString();
                }
                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }


            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

           if(s==null)
           {

               Toast.makeText(ViewAlbumsActivity.this,"Please connect to Internet and restart the application",Toast.LENGTH_LONG).show();
           }
           else {

               try {
                   JSONArray jsonArray = new JSONArray(s);

                   for (int index = 0; index < jsonArray.length(); index++) {

                       JSONObject jsonObject = jsonArray.getJSONObject(index);

                       int userId = jsonObject.getInt("userId");
                       if (userId == id) {
                           String albumTitle = jsonObject.getString("title");
                           int albumId = jsonObject.getInt("id");

                           albumItems.add(new AlbumItem(albumId, R.drawable.ic_albums, albumTitle));
                       }

                   }
                   albTitle.setText(name + "'s Albums");
                   ArrayAdapter<AlbumItem> adapter = new CustomListAlbumAdapter(getBaseContext());
                   albumListView.setAdapter(adapter);

                   albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                           AlbumItem selectedAlbum = albumItems.get(position);

                           //  Toast.makeText(ViewAlbumsActivity.this,String.valueOf(selectedAlbum.getAlbumID()),Toast.LENGTH_SHORT).show();
                           Intent photoIntent = new Intent(ViewAlbumsActivity.this, ViewPhotosActivity.class);
                           photoIntent.putExtra("albumId", selectedAlbum.getAlbumID());
                           startActivity(photoIntent);
                       }
                   });

               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
            super.onPostExecute(s);
        }
    }



}
