package com.example.payton.assembly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.database.Cursor;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class teststore extends AppCompatActivity {

    database peopleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teststore);

        ListView listView = (ListView) findViewById(R.id.listView);

        peopleDB = new database(this);
        ArrayList<String> theList = new ArrayList<>();

        Cursor data = peopleDB.showData();
        if (data.getCount() == 0) {
            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add("Name for the Photo: " + data.getString(1) + ", " + "Date: " + data.getString(2)+"\n");
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);

            }
        }
    }
}
