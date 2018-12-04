package com.example.payton.assembly;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MyEvents extends AppCompatActivity {

    ListView display;
    ListAdapter lAdapter;
    String TAG = "MyEvents";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<StringBuffer> titles = new ArrayList<>();
    private ArrayList<StringBuffer> description = new ArrayList<>();
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        display = (ListView) findViewById(R.id.displayAllEvents);

        db.collection("users").document(userID).collection("myEvents").orderBy("Start Date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                titles.clear();
                description.clear();
                for(DocumentSnapshot snapshot : documentSnapshots){
                    StringBuffer titleBuffer = new StringBuffer();
                    StringBuffer descriptionBuffer = new StringBuffer();

                    titleBuffer.append(snapshot.getString("Event Name"));
                    String Details = "Start Date and Time: " + snapshot.get("Start Date").toString() + "\n"
                            + "End Date and Time: " + snapshot.get("End Date").toString() + "\n"
                            + "Location: " + snapshot.getString("Location") + "\n"
                            + snapshot.getString("Description");
                    descriptionBuffer.append(Details);

                    titles.add(titleBuffer);
                    description.add(descriptionBuffer);
                }

                lAdapter = new ListAdapter(getApplicationContext(), titles, description);
                lAdapter.notifyDataSetChanged();
                display.setAdapter(lAdapter);
            }
        });

        dl = (DrawerLayout) findViewById(R.id.activity_my_events);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.created:
                        Intent intent = new Intent(MyEvents.this, CreatedDisplay.class);
                        startActivity(intent);
                        return true;
                    case R.id.joined:
                        Intent intent2 = new Intent(MyEvents.this, JoinedDisplay.class);
                        startActivity(intent2);
                        return true;

                    default:
                        return true;
                }


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
