package com.example.payton.assembly;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Delete {
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    public void deleteEvent(){

    }

    public void leaveEvent(String UserID, String EventID){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("users").document(UserID).collection("myEvents").document(EventID).delete();

        DocumentReference reference = db.collection("events").document(EventID);
        DocumentSnapshot snapshot = reference.get().getResult();
        List<String> UserList = (List<String>) snapshot.getData().get("Users");
        UserList.remove(user.getEmail());
        reference.update("Users", UserList);

        return;
    }
}
