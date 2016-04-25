package com.example.sagar.linearlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RelativeLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative);
    }

    public void goToL(View view)
    {
        Intent intent2 = new Intent(this,LinearLayout.class);
        startActivity(intent2);

    }
}
