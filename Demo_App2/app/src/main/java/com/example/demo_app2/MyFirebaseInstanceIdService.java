package com.example.demo_app2;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sagar on 12-08-2016.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG="REG_TOKEN";
    @Override
    public void onTokenRefresh() {

        //String token = FirebaseInstanceId.getInstance().getToken();

     //   Log.d(TAG,"HELLO" + token);
      //  Log.e(TAG, "HELLO" + token);
        Log.e(TAG, "HELLO " +FirebaseInstanceId.getInstance().getToken() );
     //   Log.d("JK", "JK");
      //  Log.d(TAG, "Refreshed token: " + token);
       // Log.v(TAG,token);
        //Toast.makeText(getApplicationContext(),token, Toast.LENGTH_LONG).show();
    //    super.onTokenRefresh();
    }
}
