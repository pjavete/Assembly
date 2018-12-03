package com.example.payton.assembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class mySchedule extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String eventid;
    String TAG = "newSchedule";


    Spinner monstart;
    Spinner monend;
    Spinner tuestart;
    Spinner tuesend;
    Spinner wedstart;
    Spinner wedsend;
    Spinner thursstart;
    Spinner thurssend;
    Spinner fridaystart;
    Spinner fridayend;
    Spinner saturdaystart;
    Spinner saturdayend;
    Spinner sundaystart;
    Spinner sundayend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myschedule);
        mAuth = FirebaseAuth.getInstance();

        //this creates the spinner arrays
        setgetmonstart();
        setgetmonend();
        setgettuesstart();
        setgettuesend();
        setgetwedstart();
        setgetwedsend();
        setgetthursstart();
        setgetthurssend();
        setgetfridaystart();
        setgetfridayend();
        setgetsaturdaystart();
        setgetsaturdayend();
        setgetsundaystart();
        setgetsundayend();
        //this part lets you run the buttons
        mondayadddata();
        tuesdayadddata();
        wednesdayadddata();
        thursdayadddata();
        fridayadddata();
        saturdayadddata();
        sundayadddata();
        done();
    }

    private void done() {
        Button donebutton = (Button)findViewById(R.id.done);
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mondaystart = monstart.getSelectedItem().toString();
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                Intent intent = getIntent();
                db = FirebaseFirestore.getInstance();
                Map<String, Object> scheduleData = new HashMap<>();
                scheduleData.put("Monday Start", mondaystart);
                eventid = db.collection("users").document().getId();
                db.collection("users").document(user.getUid()).collection("schedules").document(eventid)
                        .set(scheduleData)
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

                finish();
                onBackPressed();
            }
        });
    }

    //this is what happens when you click the button at the end of the page there is a done
    //button that just leaves the schedule page

    public void mondayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.mondaysave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String startmonday = monstart.getSelectedItem().toString();
                String endmonday = monend.getSelectedItem().toString();
                //add to database
                 //this clears the spinners so you can add another selection
                monstart.setSelection(0);
                monend.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Monday", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void tuesdayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.tuessave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String starttuesday = tuestart.getSelectedItem().toString();
                String endtuesday = tuesend.getSelectedItem().toString();
                //add to database
                //this clears the spinners so you can add another selection
                tuestart.setSelection(0);
                tuesend.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Tuesday", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void wednesdayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.wedsave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String starttuesday = wedstart.getSelectedItem().toString();
                String endtuesday = wedsend.getSelectedItem().toString();
                //add to database
                //this clears the spinners so you can add another selection
                wedstart.setSelection(0);
                wedsend.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Wednesday", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void thursdayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.thursave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String starttuesday = thursstart.getSelectedItem().toString();
                String endtuesday = thurssend.getSelectedItem().toString();
                //add to database
                //this clears the spinners so you can add another selection
                thursstart.setSelection(0);
                thurssend.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Thursday", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void fridayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.fridaysave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String starttuesday = fridaystart.getSelectedItem().toString();
                String endtuesday = fridayend.getSelectedItem().toString();
                //add to database
                //this clears the spinners so you can add another selection
                fridaystart.setSelection(0);
                fridayend.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Friday", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saturdayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.saturdaysave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String starttuesday = saturdaystart.getSelectedItem().toString();
                String endtuesday = saturdayend.getSelectedItem().toString();
                //add to database
                //this clears the spinners so you can add another selection
                saturdaystart.setSelection(0);
                saturdayend.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Saturday", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sundayadddata(){
        //when they click
        Button mondaybutton = (Button)findViewById(R.id.sundaysave);
        mondaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get start time and end times
                String starttuesday = sundaystart.getSelectedItem().toString();
                String endtuesday = sundaystart.getSelectedItem().toString();
                //add to database
                //this clears the spinners so you can add another selection
                sundaystart.setSelection(0);
                sundaystart.setSelection(0);
                Toast.makeText(mySchedule.this, "You can add another chunk of time to Sunday", Toast.LENGTH_LONG).show();
            }
        });
    }

    //initializes each spinners
    private void setgetmonstart() {
        monstart = (Spinner) findViewById(R.id.monstart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        monstart.setAdapter(adapter);
    }
    private void setgetmonend() {
        monend = (Spinner) findViewById(R.id.monend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        monend.setAdapter(adapter);
    }

    private void setgettuesstart() {
        tuestart = (Spinner) findViewById(R.id.tuesstart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        tuestart.setAdapter(adapter);
    }
    private void setgettuesend() {
        tuesend = (Spinner) findViewById(R.id.tuesend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        tuesend.setAdapter(adapter);
    }

    private void setgetwedstart() {
        wedstart = (Spinner) findViewById(R.id.wedstart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        wedstart.setAdapter(adapter);
    }
    private void setgetwedsend() {
        wedsend = (Spinner) findViewById(R.id.wedend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        wedsend.setAdapter(adapter);
    }

    private void setgetthursstart() {
        thursstart = (Spinner) findViewById(R.id.thurstart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        thursstart.setAdapter(adapter);
    }
    private void setgetthurssend() {
        thurssend = (Spinner) findViewById(R.id.thursend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        thurssend.setAdapter(adapter);
    }

    private void setgetfridaystart() {
        fridaystart = (Spinner) findViewById(R.id.fridaystart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fridaystart.setAdapter(adapter);
    }
    private void setgetfridayend() {
        fridayend = (Spinner) findViewById(R.id.fridayend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fridayend.setAdapter(adapter);
    }

    private void setgetsaturdaystart() {
        saturdaystart = (Spinner) findViewById(R.id.saturdaystart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        saturdaystart.setAdapter(adapter);
    }
    private void setgetsaturdayend() {
        saturdayend = (Spinner) findViewById(R.id.saturdayend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        saturdayend.setAdapter(adapter);
    }

    private void setgetsundaystart() {
        sundaystart = (Spinner) findViewById(R.id.sundaystart);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sundaystart.setAdapter(adapter);
    }
    private void setgetsundayend() {
        sundayend = (Spinner) findViewById(R.id.sundayend);
        String[] arraySpinner = new String[]{
                //this is the array of times from 12 AM - 11 PM
                "12:00 AM ", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM","6:00 AM", "6:30 AM","7:00 AM", "7:30 AM","8:00 AM", "8:30 AM","9:00 AM", "9:30 AM","10:00 AM", "10:30 AM","11:00 AM", "11:30 AM","12:00 PM", "12:30 PM","1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM","6:00 PM", "6:30 PM","7:00 PM", "7:30 PM","8:00 PM", "8:30 PM","9:00 PM", "9:30 PM","10:00 PM", "10:30 PM","11:00 PM", "11:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sundayend.setAdapter(adapter);
    }

}
