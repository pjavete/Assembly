package com.example.payton.assembly;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Delete {
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    public void deleteEvent(String UserID, final String EventID){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

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

    public void leaveEvent(String UserID, String EventID){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        db.collection("users").document(UserID).collection("myEvents").document(EventID).delete();

        final DocumentReference reference = db.collection("events").document(EventID);
        Task<DocumentSnapshot> eventTask = reference.get();
        eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                List<String> UserList = (List<String>) snapshot.getData().get("Users");
                UserList.remove(user.getUid());
                reference.update("Users", UserList);
                return;
            }
        });

    }
}
