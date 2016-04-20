package com.example.sagar.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    int flag=1;
    Button butn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butn=(Button)findViewById(R.id.button);

butn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        if(flag==1)
        {
            Fragment1 frag1 = new Fragment1();
            transaction.add(R.id.container,frag1);
            butn.setText("Load Fragment 2");
            transaction.commit();
            flag=0;
        }
        else
        {

            Fragment2 frag2 = new Fragment2();
            transaction.add(R.id.container,frag2);
            butn.setText("Load Fragment 1");
            transaction.commit();
            flag=1;
        }
    }
});
    }
}
