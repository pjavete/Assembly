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
    EditText timeText;
    EditText dateText;
    EditText locationText;
    EditText descText;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_events);
        eventText = findViewById(R.id.eventText);
        timeText = findViewById(R.id.timeText);
        dateText = findViewById(R.id.dateText);
        locationText = findViewById(R.id.locationText);
        descText = findViewById(R.id.descText);
        submit = findViewById(R.id.submitButton);
    }

    public void submit(View view) {
        String eventTitle = eventText.getText().toString() + "\n";
        String time = timeText.getText().toString() + "\n";
        String date = dateText.getText().toString() + "\n";
        String location = locationText.getText().toString()  + "\n";
        String description = descText.getText().toString() + "\n";


        FileOutputStream stream = null;

        try {
            stream = openFileOutput(FILE_NAME, MODE_APPEND);
            stream.write(eventTitle.getBytes());
            stream.write(time.getBytes());
            stream.write(date.getBytes());
            stream.write(location.getBytes());
            stream.write(description.getBytes());


            eventText.getText().clear();
            timeText.getText().clear();
            dateText.getText().clear();
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
        startActivity(new Intent(createEvents.this, codeGenerator.class));
    }
}
