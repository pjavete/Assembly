package com.example.payton.assembly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CreatedDisplay extends AppCompatActivity {

    ListView display;
    ListAdapter lAdapter;
    String TAG = "CreatedDisplay";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<StringBuffer> titles = new ArrayList<>();
    private ArrayList<StringBuffer> description = new ArrayList<>();
    private ArrayList<String> eventIDs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_display);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        display = (ListView) findViewById(R.id.display);

        db.collection("users").document(userID).collection("myEvents").orderBy("Start Date").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                titles.clear();
                description.clear();
                for(DocumentSnapshot snapshot : documentSnapshots){
                    if (snapshot.get("Owner").equals(false)){
                        continue;
                    }
                    StringBuffer titleBuffer = new StringBuffer();
                    StringBuffer descriptionBuffer = new StringBuffer();

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
    }


}
