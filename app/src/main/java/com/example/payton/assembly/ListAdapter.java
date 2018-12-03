package com.example.payton.assembly;

import android.content.Context;
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


public class ListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<StringBuffer> Names;
    private final ArrayList<StringBuffer> Desc;

    public ListAdapter(Context context, ArrayList<StringBuffer> Names, ArrayList<StringBuffer> Desc){
        //super(context, R.layout.single_list__item, utilsArrayList);
        this.context = context;
        this.Names = Names;
        this.Desc = Desc;
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

        viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.Eventname);
        viewHolder.txtName = (TextView) convertView.findViewById(R.id.description);
        viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);


        convertView.setTag(viewHolder);

        viewHolder.txtVersion.setText(Names.get(position));
        viewHolder.txtName.setText(Desc.get(position));


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

                                //Or Some other code you want to put here.. This is just an example.

                                break;
                            case R.id.destroy:


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
        TextView txtVersion;

    }

}
