package com.example.payton.assembly;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class NavCalAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<StringBuffer> Names;
    private final ArrayList<StringBuffer> Desc;
    private final ArrayList<String> eventIDs;
    String TAG = "newEvent";
    private FirebaseFirestore db;
    Map<String, Object> eventData;
    String eventName;
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    String location;
    String description;
    Date sDate;
    Date eDate;

    public NavCalAdapter(Context context, ArrayList<StringBuffer> Names, ArrayList<StringBuffer> Desc, ArrayList<String> eventIDs){
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

        final String navcalID = eventIDs.get(position);
        db = FirebaseFirestore.getInstance();
        viewHolder.imageview.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final PopupMenu popmenu = new PopupMenu(context, v);
                popmenu.getMenuInflater().inflate(R.menu.navcal_menu, popmenu.getMenu());

                popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.calendar:
                                Task<DocumentSnapshot> calendarTask = db.collection("events").document(navcalID).get();
                                calendarTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            eventData = snapshot.getData();

                                            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm");
                                            Date StartDate = (Date) eventData.get("Start Date");
                                            String strDate = dateFormat.format(StartDate);
                                            try {
                                                Date sDate = dateFormat.parse(strDate);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            Date EndDate = (Date) eventData.get("End Date");
                                            String enDate = dateFormat.format(EndDate);
                                            try {
                                                eDate = dateFormat.parse(enDate);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }


                                            eventName = eventData.get("Event Name").toString();
                                            location = eventData.get("Location").toString();
                                            description = eventData.get("Description").toString();

                                            Calendar cal = Calendar.getInstance();
                                            Intent intent = new Intent(Intent.ACTION_EDIT)
                                                    .setData(CalendarContract.Events.CONTENT_URI)
                                                    .putExtra(CalendarContract.Events.TITLE, eventName)
                                                    .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sDate)
                                                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eDate)
                                                    .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                                                    .putExtra(CalendarContract.Events.DESCRIPTION, description);
                                            intent.setType("vnd.android.cursor.item/event");
                                            context.startActivity(intent);
                                        } else {
                                            Log.d(TAG, "Error getting document.", task.getException());
                                        }
                                    }
                                });
                                break;
                            case R.id.navigation:
                                Task<DocumentSnapshot> navTask = db.collection("events").document(navcalID).get();
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
                                            try
                                            {
                                                context.startActivity(navIntent);
                                            }
                                            catch(ActivityNotFoundException ex)
                                            {
                                                try
                                                {
                                                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, uri);
                                                    context.startActivity(unrestrictedIntent);
                                                }
                                                catch(ActivityNotFoundException innerEx)
                                                {
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

        return convertView;
    }

    private static class ViewHolder {

        ImageView imageview;
        TextView txtName;
        TextView txtDesc;

    }

}
