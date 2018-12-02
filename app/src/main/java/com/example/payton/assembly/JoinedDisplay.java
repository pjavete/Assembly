package com.example.payton.assembly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class JoinedDisplay extends AppCompatActivity {

    ListView display2;
    ListAdapter lAdapter;
    //DatabaseHelper imageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_display);

        display2 = (ListView) findViewById(R.id.display2);

        //imageDB = new DatabaseHelper(this);

        ArrayList<StringBuffer> ids = new ArrayList<>();
        ArrayList<StringBuffer> titles = new ArrayList<>();

        int i = 0;

        while (i < 3) {
            // Moving the buStringBuffer creation inside the loop solved the issue of repeating items
            StringBuffer buffer = new StringBuffer();
            StringBuffer buffer3 = new StringBuffer();
            buffer.append("ID: " + "Kevin" + "\n");
            buffer3.append("Title: " + "title" + "\n");

            ids.add(buffer);
            titles.add(buffer3);

            i ++;

        }
        lAdapter = new ListAdapter(JoinedDisplay.this, ids , titles);
        display2.setAdapter(lAdapter);




    }
}
