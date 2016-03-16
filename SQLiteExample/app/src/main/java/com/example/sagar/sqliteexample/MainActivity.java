package com.example.sagar.sqliteexample;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    EditText name;
    EditText location;
    SQLiteDatabase database;
    Button clicked,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        clicked = (Button) findViewById(R.id.click_me);
        delete = (Button) findViewById(R.id.delete);
        name = (EditText) findViewById(R.id.name);
        location=(EditText)findViewById(R.id.location);

        clicked.setOnClickListener(this);
        delete.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Cursor cursor;

        Button pressed = (Button) v;
        String selected= pressed.getText().toString();

        Toast.makeText(MainActivity.this,"Button Clicked: "+ selected,Toast.LENGTH_SHORT).show();

        if(name.getText().toString().equals("")||location.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this,"One or More Field is/are Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            // Toast.makeText(MainActivity.this, selected+ " Selected",Toast.LENGTH_SHORT).show();

            database = openOrCreateDatabase("sagar.db", 1, null);
            database.execSQL("create table if not exists sign_up_table(name text, location text)");


            switch (selected) {

                case "Sign Up":
                    cursor = database.rawQuery("SELECT name, location FROM sign_up_table WHERE name = ? AND location = ?", new String[]{name.getText().toString(), location.getText().toString()});
                  //  Toast.makeText(MainActivity.this,cursor.toString(),Toast.LENGTH_SHORT).show();
                    cursor.moveToFirst();
                    if (cursor.getCount()==0) {

                            database.execSQL("Insert into sign_up_table values('"+name.getText().toString()+"','"+location.getText().toString()+"')");
                            Toast.makeText(MainActivity.this,"SIGNED UP",Toast.LENGTH_SHORT).show();
                            cursor.close();
                            database.close();

                        } else {
                            Toast.makeText(MainActivity.this, "User already Exists", Toast.LENGTH_SHORT).show();
                            cursor.close();
                            database.close();
                        }

                    break;

                case "Delete":

                    cursor = database.rawQuery("SELECT name, location FROM sign_up_table WHERE name = ? AND location = ?", new String[]{name.getText().toString(), location.getText().toString()});
                    cursor.moveToFirst();

                    if (cursor.getCount()==0) {
                        Toast.makeText(MainActivity.this, "User does not Exist", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        database.close();

                    } else {
                        database.execSQL("DELETE FROM sign_up_table WHERE name=? AND location=?", new String[]{ name.getText().toString(),location.getText().toString()});
                        Toast.makeText(MainActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        database.close();
                    }


                    break;


            }
        }
    }
}

      /*  SQLiteDatabase database = openOrCreateDatabase("sagar.db",1,null);
        database.execSQL("create table if not exists sampletable(name text, location text)");
        database.execSQL("Insert into sampletable values('Sagar','Ahmedabad')" );
        Cursor cur=database.rawQuery("Select * from sampletable",null);

        cur.moveToFirst();

        String name = cur.getString(0);
        String location = cur.getString(1);

        TextView text = (TextView) findViewById(R.id.text);
        text.setText("My Name is "+name+" and I live in " +location);
        database.close();*/
