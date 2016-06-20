package com.example.sagar.startedservice;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    receiver myReceiver;
    boolean isRegisterd=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          tv1=(TextView)findViewById(R.id.tv);
    }


    public void startService(View view)
    {
        if(isMyServiceRunning(DemoService.class)){
            Toast.makeText(this,"Service is already Running",Toast.LENGTH_SHORT).show();
        }
        else {

            myReceiver = new receiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.sagar.CUSTOM_INTENT");
            registerReceiver(myReceiver, intentFilter);
            isRegisterd=true;
            Intent intent1 = new Intent(this,DemoService.class);
            startService(intent1);

        }
    }
   /* public void startService(View view)
    {

        Intent intent1 = new Intent(this,DemoService.class);
        startService(intent1);


    }

*/
    public void stopService(View view)
    {
        Intent intent = new Intent(this,DemoService.class);
        stopService(intent);
        if(isRegisterd) {
            unregisterReceiver(myReceiver);
            isRegisterd=false;

        }

    }

    @Override
    protected void onStart() {

        if (!isMyServiceRunning(DemoService.class)) {
          //  Toast.makeText(this,"Firs",Toast.LENGTH_SHORT).show();
            myReceiver = new receiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.sagar.CUSTOM_INTENT");
            registerReceiver(myReceiver, intentFilter);
            isRegisterd=true;

            Intent intent1 = new Intent(this, DemoService.class);
            startService(intent1);
        }
         if(!isRegisterd)
        {
        //    Toast.makeText(this,"Second",Toast.LENGTH_SHORT).show();
            myReceiver = new receiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.sagar.CUSTOM_INTENT");
            registerReceiver(myReceiver, intentFilter);
            isRegisterd=true;

        }
        else {

        }
        super.onStart();
    }

    @Override
    protected void onStop() {

       /* if(flag==0) {
            unregisterReceiver(myReceiver);
        }
       */ super.onStop();
    }

    private class receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String count = (String) intent.getExtras().get("count");

            Toast.makeText(MainActivity.this,count,Toast.LENGTH_SHORT).show();
            tv1.setText(count);
        }
    }

    //Referred StackOverflow code
    //http://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;

    }

    @Override
    protected void onPause() {
       if(isRegisterd)
       {
           unregisterReceiver(myReceiver);
           isRegisterd=false;
       }

        super.onPause();
    }

    @Override
    protected void onResume() {

        if(!isRegisterd)
        {
            myReceiver = new receiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.sagar.CUSTOM_INTENT");
            registerReceiver(myReceiver, intentFilter);
            isRegisterd=true;

        }

        super.onResume();
    }
}
