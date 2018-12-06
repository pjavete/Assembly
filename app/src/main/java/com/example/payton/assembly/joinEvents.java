package com.example.payton.assembly;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_events);
        mAuth = FirebaseAuth.getInstance();

        joinButton = findViewById(R.id.joinButton);
        codeText = findViewById(R.id.codeText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        joinButton.setTypeface(typeface);

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

                                //puts the retrieved event into the users personal collection of joined events
                                db.collection("users").document(userID).collection("myEvents").document(codeEvent)
                                        .set(eventData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                toastMaker(1);
                                                finish();
                                                startActivity(new Intent(joinEvents.this, MainPage.class));
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
                                finish();
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
        else
            Toast.makeText(this, "Joined", Toast.LENGTH_LONG).show();
        return;
    }
}
