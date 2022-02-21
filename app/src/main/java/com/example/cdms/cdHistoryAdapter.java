package com.example.cdms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class cdHistoryAdapter extends ArrayAdapter<User> {
    private ArrayList<User> userArrayList;
    private ArrayList<String> timelist;
    private Context context;
    public cdHistoryAdapter(@NonNull Context context, ArrayList<User> userArrayList,ArrayList<String> time) {
        super(context, R.layout.list_items,userArrayList);
        this.userArrayList=userArrayList;
        this.timelist=time;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.historylist_item,parent,false);
        }
        TextView name=convertView.findViewById(R.id.li_name);
        TextView key=convertView.findViewById(R.id.li_key);
        TextView email=convertView.findViewById(R.id.li_email);
        TextView time=convertView.findViewById(R.id.li_time);

        name.setText(userArrayList.get(position).getName());
        key.setText(userArrayList.get(position).getKey());
        email.setText(userArrayList.get(position).getEmail());
        time.setText(timelist.get(position));

        notifyDataSetChanged();
        return convertView;
    }
}
