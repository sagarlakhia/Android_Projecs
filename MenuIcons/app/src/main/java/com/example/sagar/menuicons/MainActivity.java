package com.example.sagar.menuicons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] greetings;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> greetingsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      listView=(ListView) findViewById(R.id.listView);

        registerForContextMenu(listView);

        greetings=getResources().getStringArray(R.array.greetings);

        for(String temp:greetings)
        {
            greetingsList.add(temp);
        }
        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.row_listview,R.id.row_layout,greetingsList);

        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo  floatmenu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.delete:
                greetingsList.remove(floatmenu.position);
                arrayAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_menu,menu);

    }

    public boolean onOptionsItemSelected(MenuItem item){

        TextView textView =(TextView)findViewById(R.id.textView);
        switch(item.getItemId()){

            case R.id.action_settings:
                textView.setText("Search");
                return true;

            case R.id.action_refresh:
                textView.setText("Refresh");
                return true;

            case R.id.action_share:
                textView.setText("Share");
                return true;


        }
        return true;
    }
}
