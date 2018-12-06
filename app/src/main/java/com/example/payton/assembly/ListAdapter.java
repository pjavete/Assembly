package com.example.payton.assembly;

import android.content.Context;
import android.content.Intent;
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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<StringBuffer> Names;
    private final ArrayList<StringBuffer> Desc;
    private final ArrayList<String> eventIDs;

    public ListAdapter(Context context, ArrayList<StringBuffer> Names, ArrayList<StringBuffer> Desc, ArrayList<String> eventIDs){
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
                            case R.id.edit:
                                Intent editPage = new Intent(context, editEvents.class);
                                editPage.putExtra("editID", eventID);
                                context.startActivity(editPage);
                                break;
                            case R.id.destroy:
                                FirebaseFirestore db;
                                FirebaseAuth mAuth;
                                db = FirebaseFirestore.getInstance();
                                mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userID = user.getUid();
                                Delete delete = new Delete();
                                delete.leaveEvent(userID, eventID);

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
