package com.example.demo_app2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.util.LruCache;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView user_image;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Button loadImgBtn,deleteCacheButton;
    private TextView welcomeText,userName,emailId;
    private static final int SIGNIN_REQUEST = 1;
    private static final String TAG = "MyActivity";
    private int n=0;
    private Boolean flag=true;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navView;
    private NetworkImageView networkImageView;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruCache<String, Bitmap> mCache=null;
    VolleySingleton volleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        welcomeText=(TextView) findViewById(R.id.welcome);
        mAuth=FirebaseAuth.getInstance();
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navView=(NavigationView)findViewById(R.id.navView);
        View header = navView.getHeaderView(0);
        user_image=(CircleImageView)header.findViewById(R.id.user_photo);
        userName=(TextView)header.findViewById(R.id.userId);
        emailId=(TextView)header.findViewById(R.id.emailId);
        loadImgBtn =(Button) findViewById(R.id.loadImgButton);
        networkImageView=(NetworkImageView)findViewById(R.id.loadImage);
        deleteCacheButton=(Button) findViewById(R.id.deleteButton);

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if(mUser!=null )
                {
                  //  Toast.makeText(getApplicationContext(),String.valueOf(n),Toast.LENGTH_SHORT).show();


                    n++;
                    if(mUser.getPhotoUrl()==null)
                    {
                        Glide.with(MainActivity.this)
                                .load(R.drawable.ic_profilephoto)
                                .into(user_image);


                    }
                    else {
                        Glide.with(MainActivity.this)
                                .load(mUser.getPhotoUrl())
                                .into(user_image);
                    }

                    userName.setText(mUser.getDisplayName());
                    welcomeText.setText("Welcome " + mUser.getDisplayName());
                    emailId.setText(mUser.getEmail());
                   // Toast.makeText(getApplicationContext(), String.valueOf(mUser.getPhotoUrl()), Toast.LENGTH_LONG).show();



                }
                else {

                    startActivityForResult(AuthUI .getInstance().createSignInIntentBuilder().setProviders(AuthUI.GOOGLE_PROVIDER,AuthUI.FACEBOOK_PROVIDER,AuthUI.EMAIL_PROVIDER).build(),SIGNIN_REQUEST);
                    welcomeText.setText("Please Wait...");
                }

            }
        };

        navView.setItemIconTintList(null);

                loadImgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Loading Image", Toast.LENGTH_LONG).show();
                         volleySingleton = VolleySingleton.getInstance(getApplicationContext());
                                mImageLoader=volleySingleton.getImageLoader();

                                //RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
                                //mImageLoader = VolleySingleton.getInstance(/*MainActivity.this*/).getImageLoader();
                        networkImageView.setImageUrl("http://loremflickr.com/320/240", mImageLoader);
                        //Setting up Request Queue

                        //  networkImageView.setImageUrl("http://lorempixel.com/400/200/", mImageLoader);
                    }
                });

        deleteCacheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                VolleySingleton.getInstance(MainActivity.this).deleteCache();
                VolleySingleton.getInstance(MainActivity.this).getRequestQueue().getCache().clear();*/
                volleySingleton.deleteCache();
                volleySingleton.getRequestQueue().getCache().clear();

                networkImageView.setImageUrl(null, mImageLoader);

            }
        });
        }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGNIN_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {

            }
            else {
                welcomeText.setText("Error Ocurred");
            }
        }
        else {

            welcomeText.setText("Error Ocurred");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void finish() {
        mAuth.removeAuthStateListener(mAuthListener);
        super.finish();
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
        if (id == R.id.log_out) {

            AuthUI.getInstance().signOut(this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
