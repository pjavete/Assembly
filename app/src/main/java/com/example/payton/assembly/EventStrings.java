package com.example.payton.assembly;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class EventStrings {
    String TAG = "eventstrings";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    List<Map<String, Object>> createdEventData = getCreatedEventData();

    //public static String[] createdEventTitles = getCreatedEventTitles();


    public static String[] createdEventDetails = {
            "Meet at 3:00 on Tuesday for tea with Felicia at FSH 314.",
            "Meeting at courts outside Eastgym at 5:00 on Wednesday.",
            "Meet to finalize the details on or projects.",
            "test"
    };

    public static String[] joinedEventTitles = {
            "Group Meeting."
    };

    public static String[] joinedEventDetails = {
            "Determine what we want to add to the project. Meet at SandE library at 5 on Thursday."
    };

    public String[] getCreatedEventTitles() {
        String[] titles ={};
        int i = 0;
        for(Map<String, Object> event: createdEventData){
            titles[i++] = event.get("Event Name").toString();
        }
        return titles;
    }

    public List<Map<String, Object>> getCreatedEventData() {
        //retrieves the created events as a list of maps from the users createdEvents collection
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        Task<QuerySnapshot> querySnapshotTask = db.collection("users").document(userID).collection("createdEvents").get();

        querySnapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    for(DocumentSnapshot snapshot : documentSnapshots){
                        createdEventData.add(snapshot.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting document.", task.getException());
                }
            }
        });

        return createdEventData;
    }
}

