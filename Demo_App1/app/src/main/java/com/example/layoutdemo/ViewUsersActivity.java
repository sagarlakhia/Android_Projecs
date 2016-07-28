package com.example.layoutdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class ViewUsersActivity extends AppCompatActivity {

    final List<UserItem> userItems = new ArrayList<UserItem>();
    ListView userListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userListView = (ListView)findViewById(R.id.userListView);


        new GetUserDataAsyncTask().execute("https://jsonplaceholder.typicode.com/users");


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
                    .setMessage("Used Listview to display and Asynctask to get data from server")
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


    private class CustomListAdapter extends ArrayAdapter<UserItem>{

        public CustomListAdapter() {
            super(ViewUsersActivity.this, R.layout.item_row,userItems);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View userItemView = convertView;
            if(userItemView==null){
                userItemView = getLayoutInflater().inflate(R.layout.item_row,parent,false);

            }
                UserItem user = userItems.get(position);

            ImageView imgThumb = (ImageView)userItemView.findViewById(R.id.imgThumbnail);
            imgThumb.setImageResource(user.getThumbnail());

            TextView title = (TextView)userItemView.findViewById(R.id.txtTitle);
            title.setText(user.getName());

            TextView emailId= (TextView)userItemView.findViewById(R.id.email);
            emailId.setText(user.getEmail());

        //    TextView subTitle = (TextView)userItemView.findViewById(R.id.txtSubTitle);
         //   subTitle.setText(String.valueOf(user.getIdN()));


            return userItemView;
        }


    }


    private class GetUserDataAsyncTask extends AsyncTask<String,Void,String>{

        private ProgressDialog progressDialog= new ProgressDialog(ViewUsersActivity.this);
        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Loading Data");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {



            if(ConnectionCheck.hasInternetAccess(ViewUsersActivity.this)) {
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
                    return buffer.toString();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

             return null;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();

            if(s!=null) {
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int index = 0; index < jsonArray.length(); index++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(index);

                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        userItems.add(new UserItem(id, R.drawable.ic_profile, name, email));

                    }


                    ArrayAdapter<UserItem> adapter = new CustomListAdapter();

                    userListView.setAdapter(adapter);

                    userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            UserItem selectedUser = userItems.get(position);
                            Intent intent = new Intent(ViewUsersActivity.this, ViewAlbumsActivity.class);
                            intent.putExtra("id", selectedUser.getIdN());
                            intent.putExtra("name", selectedUser.getName());
                            startActivity(intent);

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else {
                Toast.makeText(ViewUsersActivity.this,"Please connect to Internet and restart the application",Toast.LENGTH_LONG).show();            }
        }
    }
}
