package com.example.sagar.intentandtoast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Activity2(View view)
    {
        input=(EditText)findViewById(R.id.editText);
        String message=input.getText().toString();
        if(message.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Please Enter your name", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("Extra_message",message);
            startActivity(intent);
        }
    }
}
