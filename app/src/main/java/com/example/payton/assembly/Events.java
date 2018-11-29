package com.example.payton.assembly;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Events extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        setContentView(R.layout.activity_events);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button1.setTypeface(typeface);
        button2.setTypeface(typeface);
        button3.setTypeface(typeface);


        //Enter info button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Events.this, createEvents.class));
            }
        });

        //view info button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Events.this, joinEvents.class));
            }
        });

        //exit button
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Events.this, MainActivity.class));
            }
        });
    }
}
