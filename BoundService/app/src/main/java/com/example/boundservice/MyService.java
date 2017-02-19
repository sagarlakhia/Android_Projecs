package com.example.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Sagar on 17-09-2016.
 */
public class MyService extends Service {

    private IBinder mBinder = new LocalBinder();


    public class LocalBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public long Factorial(int n){
        if(n==1){
            return 1;
        }
        return n*Factorial(n-1);
    }
}
