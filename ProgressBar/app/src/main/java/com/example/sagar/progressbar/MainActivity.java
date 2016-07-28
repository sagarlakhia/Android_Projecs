package com.example.sagar.progressbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    static String a="10000";
    private TextView tv,tv3,textView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =(Button) findViewById(R.id.button);
        Button button2 =(Button) findViewById(R.id.button2);
        tv = (TextView) findViewById(R.id.textView2);
        tv3= (TextView) findViewById(R.id.textView3);
        //textView=(TextView) findViewById(R.id,)
        button.setOnClickListener(this);
        button2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;
        String click = clicked.getText().toString();

        switch (click) {


            case "New Activity":
              //  Toast.makeText(getBaseContext(),"Button Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,Activity2.class);
                startActivity(intent);
                break;


            case "Progress Bar":

                new AsyncExample().execute(a);
                Toast.makeText(getBaseContext(), "Background Counting Starts", Toast.LENGTH_SHORT).show();

                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("In progress...");
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setCancelable(false);
                progressDialog.show();



                break;


        }
    }
    public class AsyncExample extends AsyncTask<String,Integer,String>{


        @Override
        protected String doInBackground(String... params) {

            for(int i=1;i<Integer.parseInt(params[0]);i++){

                i=i+1;
                publishProgress(i);
            }

            return "Hello";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           tv.setText("Done Counting");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress((values[0]) / 100);
           // if(values[0] != 10000) {
            //    progressDialog.setMessage("Loading " + (values[0])/100 +"%" );
            //}
           // else
            //{
            if(values[0] == 10000)
                progressDialog.hide();
            //}
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
}
