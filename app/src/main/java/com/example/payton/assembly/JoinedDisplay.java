package com.example.payton.assembly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class JoinedDisplay extends AppCompatActivity {

    ListView display;
    ListAdapter lAdapter;
    String TAG = "JoinedDisplay";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<StringBuffer> titles = new ArrayList<>();
    private ArrayList<StringBuffer> startDate = new ArrayList<>();
    private ArrayList<StringBuffer> endDate = new ArrayList<>();
    private ArrayList<StringBuffer> location = new ArrayList<>();
    private ArrayList<StringBuffer> description = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_display);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        display = (ListView) findViewById(R.id.display2);

        db.collection("users").document(userID).collection("joinedEvents").orderBy("Start Date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                titles.clear();
                description.clear();
                for(DocumentSnapshot snapshot : documentSnapshots){
                    StringBuffer titleBuffer = new StringBuffer();
                    StringBuffer startDateBuffer = new StringBuffer();
                    StringBuffer endDateBuffer = new StringBuffer();
                    StringBuffer locationBuffer = new StringBuffer();
                    StringBuffer descriptionBuffer = new StringBuffer();
                    titleBuffer.append(snapshot.getString("Event Name"));
                    /*
                    String Details = "Start Date and Time: " + snapshot.get("Start Date").toString() + "\n"
                            + "End Date and Time: " + snapshot.get("End Date").toString() + "\n"
                            + "Location: " + snapshot.getString("Location") + "\n"
                            + snapshot.getString("Description");
                    */
                    startDateBuffer.append("Start Date and Time: " + snapshot.get("Start Date").toString());
                    endDateBuffer.append("End Date and Time: " + snapshot.get("End Date").toString());
                    locationBuffer.append("Location: " + snapshot.getString("Location"));
                    descriptionBuffer.append(snapshot.getString("Description"));

                    titles.add(titleBuffer);
                    startDate.add(startDateBuffer);
                    endDate.add(endDateBuffer);
                    location.add(locationBuffer);
                    description.add(descriptionBuffer);
                }

                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
                lAdapter = new ListAdapter(getApplicationContext(), titles, startDate, endDate, location, description);
                lAdapter.notifyDataSetChanged();
                display.setAdapter(lAdapter);
            }
        });
    }
}
