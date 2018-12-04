package com.example.payton.assembly;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class editEvents extends AppCompatActivity {
    //variables to be used through different functions;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String editID;
    String TAG = "newEvent";
    EditText eventText;
    EditText startTime;
    EditText endTime;
    EditText startDate;
    EditText endDate;
    EditText locationText;
    EditText descText;
    Button submit;
    TextInputLayout startDateLayout;
    TextInputLayout endDateLayout;
    TextInputLayout startTimeLayout;
    TextInputLayout endTimeLayout;
    Map<String, Object> eventData;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_events);

        submit = (Button)findViewById(R.id.submitButton2);
        //Typeface typefaces = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        //submit.setTypeface(typefaces);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        //EditText instances
        eventText = findViewById(R.id.eventText2);
        startTime = findViewById(R.id.startTime2);
        endTime = findViewById(R.id.endTime2);
        startDate = findViewById(R.id.startDate2);
        endDate = findViewById(R.id.endDate2);
        locationText = findViewById(R.id.locationText2);
        descText = findViewById(R.id.descText2);
        submit = findViewById(R.id.submitButton2);

        startDateLayout = findViewById(R.id.startDateLayout2);
        endDateLayout = findViewById(R.id.endDateLayout2);
        startTimeLayout = findViewById(R.id.startTimeLayout2);
        endTimeLayout = findViewById(R.id.endTimeLayout2);

        startDateLayout.setError("MM/DD/YYYY"); // show error
        endDateLayout.setError("MM/DD/YYYY"); // show error
        startTimeLayout.setError("24 Hour Time"); // show error
        endTimeLayout.setError("24 Hour Time"); // show error

        Intent intent = getIntent();
        editID = intent.getStringExtra("editID");

        Task<DocumentSnapshot> eventTask = db.collection("events").document(editID).get();
        eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    eventData = snapshot.getData();

                    Date StartDate = (Date) eventData.get("Start Date");
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    String strDate = dateFormat.format(StartDate);
                    String[] Start = strDate.split(" ");

                    Date EndDate = (Date) eventData.get("Start Date");
                    String enDate = dateFormat.format(EndDate);
                    String[] End = enDate.split(" ");

                    eventText.setText(eventData.get("Event Name").toString());
                    startTime.setText(Start[1]);
                    endTime.setText(End[1]);
                    startDate.setText(Start[0]);
                    endDate.setText(End[0]);
                    locationText.setText(eventData.get("Location").toString());
                    descText.setText(eventData.get("Description").toString());

                } else {
                    Log.d(TAG, "Error getting document.", task.getException());
                }
            }
        });
    }

    //tests to make sure all fields have something filled out (no empty)
    protected boolean hasErrors(String title, String startDate, String endDate, String startTime, String endTime, String location, String description) {
        if (title.equals("") || startDate.equals("") || endDate.equals("") || startTime.equals("") || endTime.equals("") || location.equals("") || description.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void submit(View view) {
        //get text from each field
        String eventTitle = eventText.getText().toString();
        String sDate = startDate.getText().toString();
        String sTime = startTime.getText().toString();
        String eDate = endDate.getText().toString();
        String eTime = endTime.getText().toString();
        String location = locationText.getText().toString();
        String description = descText.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        Intent intent = getIntent();

        //if error, show error toast
        if (hasErrors(eventTitle, sDate, eDate, sTime, eTime, location, description)) {
            Toast.makeText(this, "Error. Enter text in all fields.", Toast.LENGTH_LONG).show();
        } else {
            db = FirebaseFirestore.getInstance();

            Map<String, Object> eventData = new HashMap<>();
            eventData.put("Event Name", eventTitle);
            eventData.put("Location", location);
            eventData.put("Description", description);

            try {
                sDate = sDate + " " + sTime;
                Date StartDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(sDate);
                eventData.put("Start Date", StartDate);
                eDate = eDate + " " + eTime;
                Date EndDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(eDate);
                eventData.put("End Date", EndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Add a new document into the events collection
            db.collection("events").document(editID)
                    .set(eventData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            //adds the owner boolean set to true to the event before adding it to myEvents
            boolean owner = true;
            eventData.put("Owner", owner);

            //adds new subcollection into users/userID called createdEvents and puts the new event in the collection
            db.collection("users").document(userID).collection("myEvents").document(editID)
                    .set(eventData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            //clear all fields
            eventText.getText().clear();
            startDate.getText().clear();
            endDate.getText().clear();
            startTime.getText().clear();
            endTime.getText().clear();
            locationText.getText().clear();
            descText.getText().clear();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            finish();

            startActivity(new Intent(editEvents.this, MyEvents.class));
        }
    }

}
