package com.example.boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyService mService;
    boolean isBound=false;
    private Button mButton;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mButton=(Button) findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText)findViewById(R.id.number);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    public void FindFact(View v) {

        if (isBound) {
            long fact = mService.Factorial(Integer.parseInt(editText.getText().toString()));
            textView.setText(String.valueOf(fact));
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        if(isBound){
            unbindService(mConnection);
            isBound=false;
        }

    }

    private ServiceConnection mConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            MyService.LocalBinder binder =(MyService.LocalBinder) iBinder;
            mService=binder.getService();
            Toast.makeText(getApplicationContext(),"Service Connected", Toast.LENGTH_SHORT).show();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(getApplicationContext(),"Service Connected", Toast.LENGTH_SHORT).show();
            isBound=false;
        }
    };

}
