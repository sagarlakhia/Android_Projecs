package com.example.demo_app2;

/**
 * Created by Sagar on 14-08-2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
private static VolleySingleton mInstance = null;
private RequestQueue mRequestQueue;
private ImageLoader mImageLoader;
private static Context context;
    LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

    public VolleySingleton(Context context){
    this.context=context;
    mRequestQueue = Volley.newRequestQueue(context);

    mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {


        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
             return mCache.get(url);
             }
            });



        }

      public static VolleySingleton getInstance(Context context1){
      if(mInstance == null){
          context=context1;
      mInstance = new VolleySingleton(context);
      }
      return mInstance;
      }
    public RequestQueue getRequestQueue(){
    return this.mRequestQueue;
    }
        public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }


    public void deleteCache(){

        mCache.remove("http://loremflickr.com/320/240");

        mCache.evictAll();
    }


}