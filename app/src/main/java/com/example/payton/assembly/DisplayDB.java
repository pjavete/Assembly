package com.example.payton.assembly;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayDB extends AppCompatActivity {

    ListView display2;
    ListAdapter lAdapter;
    DatabaseHelper imageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.DisplayDB);

        display2 = (ListView) findViewById(R.id.display2);

        imageDB = new DatabaseHelper(this);

        ArrayList<StringBuffer> ids = new ArrayList<>();
        ArrayList<StringBuffer> titles = new ArrayList<>();
        ArrayList<StringBuffer> images = new ArrayList<>();

        while (range.moveToNext()) {
            // Moving the buStringBuffer creation inside the loop solved the issue of repeating items
            StringBuffer buffer = new StringBuffer();
            StringBuffer buffer2 = new StringBuffer();
            StringBuffer buffer3 = new StringBuffer();
            buffer.append("ID: " + range.getString(0) + "\n");
            buffer2.append(range.getString(1));
            buffer3.append("Title: " + range.getString(2) + "\n");

            ids.add(buffer);
            images.add(buffer2);
            titles.add(buffer3);

        }
        lAdapter = new ListAdapter(DisplayDB.this, ids , titles, images);
        //ArrayAdapter<StringBuffer> adapter = new ArrayAdapter<StringBuffer>(this, android.R.layout.simple_list_item_1, images);
        display2.setAdapter(lAdapter);

        Toast.makeText(getBaseContext(), "Displayed successfully!",Toast.LENGTH_SHORT).show();


    }
}
