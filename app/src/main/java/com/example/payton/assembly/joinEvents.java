package com.example.payton.assembly;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class joinEvents extends AppCompatActivity{

    //variables to be used over multiple functions
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String TAG = "joinEvent";
    Button joinButton;
    EditText codeText;
    Map<String, Object> eventData;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_events);
        mAuth = FirebaseAuth.getInstance();

        joinButton = findViewById(R.id.joinButton);
        codeText = findViewById(R.id.codeText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        joinButton.setTypeface(typeface);

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

                        Intent homepage_redirect = new Intent(joinEvents.this, MainPage.class);
                        startActivity(homepage_redirect);
                        return true;
                    case R.id.myevents:

                        Intent myevents_redirect = new Intent(joinEvents.this, MyEvents.class);
                        startActivity(myevents_redirect);
                        return true;
                    case R.id.createEvent:

                        Intent createEvent_redirect = new Intent(joinEvents.this, createEvents.class);
                        startActivity(createEvent_redirect);
                        return true;
                    case R.id.joinEvent:

                        Intent joinEvent_redirect = new Intent(joinEvents.this, joinEvents.class);
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
        final Dialog myDialog = new Dialog(joinEvents.this);
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

                Intent signout_redirect = new Intent(joinEvents.this, opening.class);
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


    public void joinEvent(View view){
        //variables to be used in the following code and declaring instance of database
        final FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();
        final String codeEvent = codeText.getText().toString();
        db = FirebaseFirestore.getInstance();

        //looks to see if the user has created the event and if so will not let the user join it
        Task<DocumentSnapshot> eventID = db.collection("users").document(userID).collection("myEvents").document(codeEvent).get();
        eventID.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                //checks if the user is the owner of the event they are trying to join
                boolean isOwner;
                if (task.getResult().getData() != null){
                    isOwner = (boolean) task.getResult().getData().get("Owner");
                } else {
                    isOwner = false;
                }
                if(isOwner){
                    toastMaker(0);
                    codeText.getText().clear();
                }else {
                    //retrieves the event for the events collection in the firestore database
                    Task<DocumentSnapshot> eventTask = db.collection("events").document(codeEvent).get();
                    eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                eventData = snapshot.getData();

                                List<String> UserList = (List<String>) eventData.get("Users");
                                UserList.add(user.getUid());
                                db.collection("events").document(codeEvent).update("Users", UserList);

                                //adds the owner boolean set to true to the event before adding it to myEvents
                                boolean owner = false;
                                eventData.put("Owner", owner);
                                eventData.remove("Users");

                                //puts the retrieved event into the users personal collection of joined events
                                db.collection("users").document(userID).collection("myEvents").document(codeEvent)
                                        .set(eventData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                toastMaker(1);
                                                startActivity(new Intent(joinEvents.this, MyEvents.class));
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });
                            } else {
                                Log.d(TAG, "Error getting document.", task.getException());
                                toastMaker(2);
                                onBackPressed();
                            }
                        }
                    });

                }
            }
        });
    }

    public void toastMaker(int state){
        if (state == 0)
            Toast.makeText(this, "Error. Event owner cannot join own event.", Toast.LENGTH_LONG).show();
        else if (state == 1)
            Toast.makeText(this, "Joined", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error retrieving event", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(joinEvents.this, MainPage.class);
        startActivity(intent);
    }
}
