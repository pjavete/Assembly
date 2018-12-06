package com.example.payton.assembly;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class NavCalAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<StringBuffer> Names;
    private final ArrayList<StringBuffer> Desc;
    private final ArrayList<String> eventIDs;

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

        final String eventID = eventIDs.get(position);
        viewHolder.imageview.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final PopupMenu popmenu = new PopupMenu(context, v);
                popmenu.getMenuInflater().inflate(R.menu.popup_menu, popmenu.getMenu());

                popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.calendar:
                                Intent editPage = new Intent(context, editEvents.class);
                                editPage.putExtra("editID", eventID);
                                context.startActivity(editPage);
                                break;
                            case R.id.navigation:
                                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=address", location);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                //makes it so that it opens up in Google Maps
                                intent.setPackage("com.google.android.apps.maps");
                                context.startActivity(intent);
                                //ensures that user has Google Maps installed
                                try
                                {
                                    context.startActivity(intent);
                                }
                                catch(ActivityNotFoundException ex)
                                {
                                    try
                                    {
                                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        context.startActivity(unrestrictedIntent);
                                    }
                                    catch(ActivityNotFoundException innerEx)
                                    {
                                        Toast.makeText(context, "Please install Google Maps", Toast.LENGTH_LONG).show();
                                    }
                                }
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
