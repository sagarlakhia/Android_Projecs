package com.example.sagar.intentandtoast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.*;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv;
        Intent intent = getIntent();
        String message=intent.getStringExtra("Extra_message");

        tv= (TextView)findViewById(R.id.secondActName);
        tv.setText("Welcome " +message);



    }

    public void goBack(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
