package com.example.adamgoldberg.beautifulbulldog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Adam Goldberg on 9/12/2017.
 */

public class BulldogArrayAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private RealmResults<Bulldog> mDataSource;

    public BulldogArrayAdapter(Context context, RealmResults<Bulldog> items){
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return mDataSource.size();
    }

    @Override
    public Bulldog getItem(int position){
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = mInflater.inflate(R.layout.bulldog_cell, parent, false);

        TextView name = rowView.findViewById(R.id.name_label);
        name.setText(getItem(position).getName());

        TextView age = rowView.findViewById(R.id.age_label);
        age.setText(getItem(position).getAge());

        return rowView;
    }
}
