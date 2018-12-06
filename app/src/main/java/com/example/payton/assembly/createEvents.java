package com.example.payton.assembly;

import android.app.Dialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createEvents extends AppCompatActivity {
    //variables to be used through different functions;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String eventid;
    Intent passcode;
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

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_events);

        submit = (Button)findViewById(R.id.submitButton);
        Typeface typefaces = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        submit.setTypeface(typefaces);

        mAuth = FirebaseAuth.getInstance();

        //EditText instances
        eventText = findViewById(R.id.eventText);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        locationText = findViewById(R.id.locationText);
        descText = findViewById(R.id.descText);
        submit = findViewById(R.id.submitButton);

        startDateLayout = findViewById(R.id.startDateLayout);
        endDateLayout = findViewById(R.id.endDateLayout);
        startTimeLayout = findViewById(R.id.startTimeLayout);
        endTimeLayout = findViewById(R.id.endTimeLayout);

        startDateLayout.setError("MM/DD/YYYY"); // show error
        endDateLayout.setError("MM/DD/YYYY"); // show error
        startTimeLayout.setError("24 Hour Time"); // show error
        endTimeLayout.setError("24 Hour Time"); // show error

        dl = (DrawerLayout)findViewById(R.id.activity_main_page);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open,R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //this sets the navigation header to the USER ID from firebase
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.navusername);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        navUsername.setTypeface(face);
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        String userEmail = user.getEmail();
        navUsername.setText(userEmail);
        //this sets the navigation header to the USER ID from firebase

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.gohome:
                        finish();
                        Intent homepage_redirect = new Intent(createEvents.this, MainPage.class);
                        startActivity(homepage_redirect);
                        return true;
                    case R.id.myevents:
                        Intent myevents_redirect = new Intent(createEvents.this, MyEvents.class);
                        startActivity(myevents_redirect);
                        return true;
                    case R.id.createEvent:
                        Intent createEvent_redirect = new Intent(createEvents.this, createEvents.class);
                        startActivity(createEvent_redirect);
                        return true;
                    case R.id.joinEvent:
                        Intent joinEvent_redirect = new Intent(createEvents.this, joinEvents.class);
                        startActivity(joinEvent_redirect);
                        return true;
                    case R.id.logout:
                        MyCustomAlertDialog();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void MyCustomAlertDialog(){
        final Dialog myDialog = new Dialog(createEvents.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.setTitle("My Custom Dialog");
        Button hello = (Button)myDialog.findViewById(R.id.hello);
        Button close = (Button)myDialog.findViewById(R.id.close);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        hello.setTypeface(typeface);
        close.setTypeface(typeface);
        hello.setEnabled(true);
        close.setEnabled(true);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                Intent signout_redirect = new Intent(createEvents.this, opening.class);
                startActivity(signout_redirect);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.cancel();
            }
        });

        myDialog.show();
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

            List<String> UserList = new ArrayList<>();

            Map<String, Object> eventData = new HashMap<>();
            eventData.put("Event Name", eventTitle);
            eventData.put("Location", location);
            eventData.put("Description", description);
            eventData.put("Users", UserList);

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
            eventid = db.collection("users").document().getId();
            db.collection("events").document(eventid)
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
            db.collection("users").document(userID).collection("myEvents").document(eventid)
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

            //pass the new event's id to code generator
            passcode = new Intent(createEvents.this, codeGenerator.class);
            passcode.putExtra("eventCode", eventid);
            startActivity(passcode);
        }
    }

}
