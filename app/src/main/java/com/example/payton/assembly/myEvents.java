package com.example.payton.assembly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



public class myEvents extends AppCompatActivity {
    Spinner eventSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_events);
        eventSpinner = findViewById(R.id.eventSpinner);

        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this, R.array.eventChoices, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(myAdapter);
    }

    public void decision(View view) {
        String choice = eventSpinner.getSelectedItem().toString() + "\n";

    }
}
