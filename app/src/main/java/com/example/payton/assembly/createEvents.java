package com.example.payton.assembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class createEvents extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String TAG = "newEvent";

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

        mAuth = FirebaseAuth.getInstance();

        eventText = findViewById(R.id.eventText);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        locationText = findViewById(R.id.locationText);
        descText = findViewById(R.id.descText);
        submit = findViewById(R.id.submitButton);
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
        //String userID = user.getUid();
        Intent intent = getIntent();
        String code = intent.getStringExtra("eventCode");

        if (hasErrors(eventTitle, sDate, eDate, sTime, eTime, location, description)) {
            Toast.makeText(this, "Error. Enter text in all fields.", Toast.LENGTH_LONG).show();
        } else {
            db = FirebaseFirestore.getInstance();

            Map<String, Object> eventData = new HashMap<>();
            eventData.put("Event Name", eventTitle);
            eventData.put("Start Date", sDate);
            eventData.put("End Date", eDate);
            eventData.put("Start Time", sTime);
            eventData.put("End Time", eTime);
            eventData.put("Location", location);
            eventData.put("Description", description);
            eventData.put("Event Code", code);

            // Add a new document with a generated ID
            db.collection("events")
                    .add(eventData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
            eventText.getText().clear();
            startDate.getText().clear();
            endDate.getText().clear();
            startTime.getText().clear();
            endTime.getText().clear();
            locationText.getText().clear();
            descText.getText().clear();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(createEvents.this, codeGenerator.class));
        }
    }
}
