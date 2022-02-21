package com.example.cdms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class cdLayout extends BaseAdapter {

    List<cd> mData;
    Context context;

    public cdLayout(Context context, List<cd> mData) {
        this.mData = mData;
        this.context = context;

    }

    @NonNull
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View myView;

        if (convertView == null) {
            myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cd_list_layout, parent, false);
        } else {
            myView = convertView;
        }

        TextView cdName = myView.findViewById(R.id.cdName);
        TextView cdDescription = myView.findViewById(R.id.cdSummary);


        cdName.setText((CharSequence) mData.get(position).getName());
        cdDescription.setText((CharSequence) mData.get(position).getSummary());


        return myView;

    }
}
