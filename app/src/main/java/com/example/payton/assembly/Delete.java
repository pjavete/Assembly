package com.example.payton.assembly;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Delete extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userID;
    String eventID;
    String EventName;
    boolean isOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        eventID = intent.getStringExtra("eventID");

        db = FirebaseFirestore.getInstance();

        final TextView deletePrompt = (TextView) findViewById(R.id.deletePrompt);
        final Button deleteYes = (Button) findViewById(R.id.deleteYes);
        final Button deleteNo = (Button) findViewById(R.id.deleteNo);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        deleteYes.setTypeface(typeface);
        deleteNo.setTypeface(typeface);

        Task<DocumentSnapshot> eventTask = db.collection("users").document(userID).collection("myEvents").document(eventID).get();
        eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                isOwner = (boolean) snapshot.getData().get("Owner");
                EventName = snapshot.getData().get("Event Name").toString();
                deletePrompt.setText("Are you sure you want to " + (isOwner ? "delete " : "leave ") + EventName + "?");
                deleteYes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(!isOwner){
                            leaveEvent(userID, eventID);
                            toastMaker(0);
                        } else {
                            deleteEvent(userID, eventID);
                            toastMaker(1);
                        }
                        Intent deletedoneIntent = new Intent(Delete.this, MyEvents.class);
                        startActivity(deletedoneIntent);
                    }
                });
            }
        });
    }

    public void deleteNo(View view){
        Intent deletedoneIntent = new Intent(this, MyEvents.class);
        startActivity(deletedoneIntent);
    }

    public void deleteEvent(String UserID, final String EventID){
        db = FirebaseFirestore.getInstance();

        db.collection("users").document(UserID).collection("myEvents").document(EventID).delete();

        Task<DocumentSnapshot> eventTask = db.collection("events").document(EventID).get();
        eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                List<String> UserList = (List<String>) snapshot.getData().get("Users");
                for(String ListUserID: UserList){
                    db.collection("users").document(ListUserID).collection("myEvents").document(EventID).delete();
                }
                db.collection("events").document(EventID).delete();
                return;
            }
        });

    }

    public void leaveEvent(final String UserID, String EventID){
        db = FirebaseFirestore.getInstance();

        db.collection("users").document(UserID).collection("myEvents").document(EventID).delete();

        final DocumentReference reference = db.collection("events").document(EventID);
        Task<DocumentSnapshot> eventTask = reference.get();
        eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                List<String> UserList = (List<String>) snapshot.getData().get("Users");
                UserList.remove(UserID);
                reference.update("Users", UserList);
                return;
            }
        });

    }

    public void toastMaker(int state){
        if (state == 0)
            Toast.makeText(this, "Successfully left event", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Successfully deleted event", Toast.LENGTH_LONG).show();
        return;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Delete.this, MainPage.class);
        startActivity(intent);
    }
}
