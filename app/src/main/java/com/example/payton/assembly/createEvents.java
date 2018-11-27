package com.example.payton.assembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class createEvents extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    EditText eventText;
    EditText startTime;
    EditText endTime;
    EditText startDate;
    EditText endDate;
    EditText locationText;
    EditText descText;
    Button submit;
    createEvents newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_events);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        eventText = findViewById(R.id.eventText);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        locationText = findViewById(R.id.locationText);
        descText = findViewById(R.id.descText);
        submit = findViewById(R.id.submitButton);
    }

    //this is a super dumb question but i forgot how to create objects LOL
    //need to make sure to store the event code
    public createEvents(String title, String startDate, String endDate, String startTime, String endTime, String location, String description, String eventCode) {
        String event_title = title;
        String start_date = startDate;
        String end_date = endDate;
        String start_time = startTime;
        String end_time = endTime;
        String event_location = location;
        String event_description = description;
        String event_code = eventCode;
    }


    protected boolean hasErrors(String title, String startDate, String endDate, String startTime, String endTime, String location, String description) {
        if (title.equals("") || startDate.equals("") || endDate.equals("") || startTime.equals("") || endTime.equals("") || location.equals("") || description.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void submit(View view) {
        String eventTitle = eventText.getText().toString();
        String sDate = startDate.getText().toString();
        String eDate = endDate.getText().toString();
        String sTime = startTime.getText().toString();
        String eTime = endTime.getText().toString();
        String location = locationText.getText().toString();
        String description = descText.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        Intent intent = getIntent();
        String code = intent.getStringExtra("eventCode");

        if (hasErrors(eventTitle, sDate, eDate, sTime, eTime, location, description)) {
            Toast.makeText(this, "Error. Enter text in all fields.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Assembly");
            newEvent = new createEvents(eventTitle, sDate, eDate, sTime, eTime, location, description, code);
            myRef.child("Users").child(userID).child("Events").push().setValue(newEvent);

            eventText.getText().clear();
            startDate.getText().clear();
            endDate.getText().clear();
            startTime.getText().clear();
            endTime.getText().clear();
            locationText.getText().clear();
            descText.getText().clear();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            startActivity(new Intent(createEvents.this, codeGenerator.class));
        }
    }
}
