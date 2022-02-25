package com.example.cdms;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class cdHistoryAdapter extends ArrayAdapter<UserTime> {
    private ArrayList<UserTime> userArrayList;
    private Context context;
    public cdHistoryAdapter(@NonNull Context context, ArrayList<UserTime> userArrayList) {
        super(context,0,userArrayList);
        this.userArrayList=userArrayList;
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
        TextView remark=convertView.findViewById(R.id.li_remark);
        String timedate=userArrayList.get(position).getTime();

        name.setText(Html.fromHtml("<b>CD number : </b>"+userArrayList.get(position).getName()));;
        key.setText(Html.fromHtml("<b>id : </b>"+userArrayList.get(position).getuserId()));
        email.setText(userArrayList.get(position).getEmail());
        time.setText(Html.fromHtml("<b>Scanned on: </b>"+timedate.substring(0,10)+"&nbsp;&nbsp;&nbsp;"+timedate.substring(11,19)));
        remark.setText(Html.fromHtml("<b>REMARK : </b>"+userArrayList.get(position).getRemark()));

        return convertView;
    }
}
