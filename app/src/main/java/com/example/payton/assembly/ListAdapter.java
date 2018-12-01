package com.example.payton.assembly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


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


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {


            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder = new ViewHolder();

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.aIDtxt);
        viewHolder.txtName = (TextView) convertView.findViewById(R.id.Eventname);


        convertView.setTag(viewHolder);
        //viewHolder.txtVersion.setText("ID: "+IDs);

        viewHolder.txtVersion.setText(Names.get(position));
        viewHolder.txtName.setText(Desc.get(position));


        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;

    }

}
