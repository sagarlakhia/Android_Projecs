package com.example.sagar.startedservice;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Sagar on 16-06-2016.
 */
public class DemoService extends Service {

    private boolean isClose=false;
    final class MyThread implements Runnable{

        int serviceId;

        MyThread(int serviceId){
            this.serviceId=serviceId;
        }
        @Override
        public void run() {


            synchronized (this){


                for(int count=0;count<20;count++){

                        try{

                            if(!isClose) {
                                Thread.sleep(3000);

                                Intent intent = new Intent();
                                intent.setAction("com.sagar.CUSTOM_INTENT");
                                intent.putExtra("count", String.valueOf(count));
                                //intent.putExtra("count",count);
                                sendBroadcast(intent);
                            }
                            else
                            {
                                Thread.interrupted();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                }

               stopSelf(serviceId);
            }





        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            Thread thread = new Thread(new MyThread(startId));
            thread.start();
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();

            return START_NOT_STICKY;



    }


    @Override
    public void onCreate() {
        isClose=false;
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        isClose=true;
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;

    }



}
