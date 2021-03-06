package com.example.payton.assembly;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class MainPage extends AppCompatActivity {

    ListView display;
    ListAdapter lAdapter;
    String TAG = "MyEvents";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<StringBuffer> titles = new ArrayList<>();
    private ArrayList<StringBuffer> description = new ArrayList<>();
    private ArrayList<String> eventIDs = new ArrayList<>();
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        display = (ListView) findViewById(R.id.displayUpcomingEvents);

        db.collection("users").document(userID).collection("myEvents").orderBy("Start Date").limit(5).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                titles.clear();
                description.clear();
                for(DocumentSnapshot snapshot : documentSnapshots){
                    StringBuffer titleBuffer = new StringBuffer();
                    StringBuffer sDateBuffer = new StringBuffer();
                    StringBuffer eDateBuffer = new StringBuffer();
                    StringBuffer sTimeBuffer = new StringBuffer();
                    StringBuffer eTimeBuffer = new StringBuffer();
                    StringBuffer descriptionBuffer = new StringBuffer();
                    StringBuffer locationBuffer = new StringBuffer();

                    titleBuffer.append(snapshot.getString("Event Name"));

                    String Details = "Start Date and Time: " + snapshot.get("Start Date").toString() + "\n"
                            + "End Date and Time: " + snapshot.get("End Date").toString() + "\n"
                            + "Location: " + snapshot.getString("Location") + "\n"
                            + snapshot.getString("Description");
                    descriptionBuffer.append(Details);

                    //storing all the eventID
                    eventIDs.add(snapshot.getId());
                    titles.add(titleBuffer);
                    description.add(descriptionBuffer);
                }

                lAdapter = new ListAdapter(getApplicationContext(), titles, description, eventIDs);
                lAdapter.notifyDataSetChanged();
                display.setAdapter(lAdapter);
            }
        });

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
        mAuth = FirebaseAuth.getInstance();
        String userEmail = user.getEmail();
        navUsername.setText(userEmail);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.gohome:

                        Intent homepage_redirect = new Intent(MainPage.this, MainPage.class);
                        startActivity(homepage_redirect);
                        return true;
                    case R.id.myevents:

                        Intent myevents_redirect = new Intent(MainPage.this, MyEvents.class);
                        startActivity(myevents_redirect);
                        return true;
                    case R.id.createEvent:

                        Intent createEvent_redirect = new Intent(MainPage.this, createEvents.class);
                        startActivity(createEvent_redirect);
                        return true;
                    case R.id.joinEvent:

                        Intent joinEvent_redirect = new Intent(MainPage.this, joinEvents.class);
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
        final Dialog myDialog = new Dialog(MainPage.this);
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

                Intent signout_redirect = new Intent(MainPage.this, opening.class);
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
}


