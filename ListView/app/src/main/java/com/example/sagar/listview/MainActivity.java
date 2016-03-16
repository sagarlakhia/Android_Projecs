package com.example.sagar.listview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity {

        private String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        names=getResources().getStringArray(R.array.names);
        this.setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_main,R.id.TextView,names));


        ListView listview = getListView();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = names[position];
              //  Toast.makeText(MainActivity.this,String.format("This %s was chose",name),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,Activity2.class);
                intent.putExtra("Name_Selected",name);
                startActivity(intent);

            }
        });

    }

}
