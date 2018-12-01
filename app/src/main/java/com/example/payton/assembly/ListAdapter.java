package com.example.payton.assembly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;


import java.util.ArrayList;


public class ListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<StringBuffer> IDs;
    private final ArrayList<StringBuffer> Titles;

    public ListAdapter(Context context, ArrayList<StringBuffer> IDs, ArrayList<StringBuffer> Titles, ArrayList<StringBuffer> Images){
        //super(context, R.layout.single_list__item, utilsArrayList);
        this.context = context;
        this.IDs = IDs;
        this.Titles = Titles;
    }

    @Override
    public int getCount() {

        return IDs.size();
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
        viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);


        convertView.setTag(viewHolder);
        //viewHolder.txtVersion.setText("ID: "+IDs);

        viewHolder.txtVersion.setText(IDs.get(position));
        viewHolder.txtName.setText(Titles.get(position));


        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;

    }

}
