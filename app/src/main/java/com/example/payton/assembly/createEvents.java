package com.example.payton.assembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class createEvents extends AppCompatActivity {
    private static final String FILE_NAME = "photos.txt";
    EditText eventText;
    EditText startTime;
    EditText endTime;
    EditText startDate;
    EditText endDate;
    EditText locationText;
    EditText descText;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_events);
        eventText = findViewById(R.id.eventText);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        locationText = findViewById(R.id.locationText);
        descText = findViewById(R.id.descText);
        submit = findViewById(R.id.submitButton);
    }

    public void submit(View view) {
        String eventTitle = eventText.getText().toString() + "\n";
        String sDate = startDate.getText().toString() + "\n";
        String eDate = endDate.getText().toString() + "\n";
        String sTime = startTime.getText().toString() + "\n";
        String eTime = endTime.getText().toString() + "\n";
        String location = locationText.getText().toString()  + "\n";
        String description = descText.getText().toString() + "\n";


        FileOutputStream stream = null;

        try {
            stream = openFileOutput(FILE_NAME, MODE_APPEND);
            stream.write(eventTitle.getBytes());
            stream.write(sDate.getBytes());
            stream.write(eDate.getBytes());
            stream.write(sTime.getBytes());
            stream.write(eTime.getBytes());
            stream.write(location.getBytes());
            stream.write(description.getBytes());


            eventText.getText().clear();
            startDate.getText().clear();
            endDate.getText().clear();
            startTime.getText().clear();
            endTime.getText().clear();
            locationText.getText().clear();
            descText.getText().clear();

            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        finish();
        startActivity(new Intent(createEvents.this, codeGenerator.class));
    }
}
