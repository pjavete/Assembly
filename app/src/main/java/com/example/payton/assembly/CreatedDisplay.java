package com.example.payton.assembly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreatedDisplay extends AppCompatActivity {

    ListView display;
    ListAdapter lAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_display);

        display = (ListView) findViewById(R.id.display);

        //imageDB = new DatabaseHelper(this);

        ArrayList<StringBuffer> ids = new ArrayList<>();
        ArrayList<StringBuffer> titles = new ArrayList<>();

        int i = 0;

        while (i < 3) {
            // Moving the buStringBuffer creation inside the loop solved the issue of repeating items
            StringBuffer buffer = new StringBuffer();
            StringBuffer buffer3 = new StringBuffer();
            buffer.append("ID: " + "ID" + "\n");
            buffer3.append("Title: " + "title" + "\n");

            ids.add(buffer);
            titles.add(buffer3);

            i ++;

        }
        lAdapter = new ListAdapter(CreatedDisplay.this, ids , titles);
        display.setAdapter(lAdapter);

        Toast.makeText(getBaseContext(), "Displayed successfully!",Toast.LENGTH_SHORT).show();


    }
}
