package com.example.payton.assembly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class joinEvents extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String TAG = "joinEvent";
    Button joinButton;
    EditText codeText;
    Boolean found = false;
    Map<String, Object> eventData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_events);
        mAuth = FirebaseAuth.getInstance();

        joinButton = findViewById(R.id.joinButton);
        codeText = findViewById(R.id.codeText);
    }


    public void joinEvent(View view){
        final FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        final String codeEvent = codeText.getText().toString();
        db = FirebaseFirestore.getInstance();

        Task<DocumentSnapshot> eventID = db.collection("users").document(user.getUid()).collection("createdEvents").document(codeEvent).get();
        eventID.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().getData() != null) {
                    found = true;
                } else {
                    found = false;
                }
                if(found == true){
                    //Toast.makeText(this, "Error. Event owner cannot join own event.", Toast.LENGTH_LONG).show();
                    codeText.getText().clear();
                }else {
                    Task<DocumentSnapshot> eventTask = db.collection("events").document(codeEvent).get();
                    eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                eventData = snapshot.getData();
                                db.collection("users").document(user.getUid()).collection("joinedEvents").document(codeEvent)
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
                            } else {
                                Log.d(TAG, "Error getting document.", task.getException());
                            }
                        }
                    });


                    //Toast.makeText(this, "Joined", Toast.LENGTH_LONG).show();
                    finish();
                    onBackPressed();
                }
            }
        });


    }
}
