package com.example.payton.assembly;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;



public class ListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<StringBuffer> Names;
    private final ArrayList<StringBuffer> Desc;
    private final ArrayList<String> eventIDs;

    Map<String, Object> eventData;
    String location;
    private FirebaseFirestore db;
    FirebaseAuth mAuth;


    public ListAdapter(Context context, ArrayList<StringBuffer> Names, ArrayList<StringBuffer> Desc, ArrayList<String> eventIDs) {
        //super(context, R.layout.single_list__item, utilsArrayList);
        this.context = context;
        this.Names = Names;
        this.Desc = Desc;
        this.eventIDs = eventIDs;
    }

    @Override
    public int getCount() {

        return Names.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {


            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder = new ViewHolder();

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.txtName = (TextView) convertView.findViewById(R.id.Eventname);
        viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.description);
        viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);

        convertView.setTag(viewHolder);

        viewHolder.txtName.setText(Names.get(position));
        viewHolder.txtDesc.setText(Desc.get(position));

        final String eventID = eventIDs.get(position);
        db = FirebaseFirestore.getInstance();
        viewHolder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popmenu = new PopupMenu(context, v);
                popmenu.getMenuInflater().inflate(R.menu.popup_menu, popmenu.getMenu());
                final Menu options = popmenu.getMenu();

                db = FirebaseFirestore.getInstance();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                final String userID = user.getUid();
                Task<DocumentSnapshot> eventTask = db.collection("users").document(userID).collection("myEvents").document(eventID).get();
                eventTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        final boolean isOwner = (boolean) snapshot.getData().get("Owner");
                        if(!isOwner){
                            options.getItem(1).setTitle("Leave");
                            options.getItem(0).setVisible(false);
                        }

                        popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                        {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit:
                                        Intent editPage = new Intent(context, editEvents.class);
                                        editPage.putExtra("editID", eventID);
                                        context.startActivity(editPage);
                                        break;
                                    case R.id.destroy:
                                        Intent deleteReturn = new Intent(context, Delete.class);
                                        deleteReturn.putExtra("userID", userID);
                                        deleteReturn.putExtra("eventID", eventID);
                                        context.startActivity(deleteReturn);
                                        break;
                                    case R.id.navigation:
                                        Task<DocumentSnapshot> navTask = db.collection("events").document(eventID).get();
                                        navTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot snapshot = task.getResult();
                                                    eventData = snapshot.getData();
                                                    location = eventData.get("Location").toString();
                                                    Uri uri = Uri.parse("geo:0,0?q=" + location);
                                                    Intent navIntent = new Intent(Intent.ACTION_VIEW, uri);
                                                    //makes it so that it opens up in Google Maps
                                                    navIntent.setPackage("com.google.android.apps.maps");
                                                    context.startActivity(navIntent);
                                                    //ensures that user has Google Maps installed
                                                    try {
                                                        context.startActivity(navIntent);
                                                    } catch (ActivityNotFoundException ex) {
                                                        try {
                                                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, uri);
                                                            context.startActivity(unrestrictedIntent);
                                                        } catch (ActivityNotFoundException innerEx) {
                                                            Toast.makeText(context, "Please install Google Maps", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting document.", task.getException());
                                                }
                                            }
                                        });
                                        break;
                                    default:
                                        break;
                                }
                                return true;
                            }
                        });
                        popmenu.show();
                    }
                });
            }
        });

        return convertView;
    }

    private static class ViewHolder {

        ImageView imageview;
        TextView txtName;
        TextView txtDesc;

    }

}
