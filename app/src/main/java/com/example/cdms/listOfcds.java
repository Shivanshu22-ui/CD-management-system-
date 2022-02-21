package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listOfcds extends AppCompatActivity {
ListView listView;
FirebaseDatabase database;
DatabaseReference ref;
ArrayList <String> list;
ArrayAdapter <String> adapter;
ArrayList<String> idlist;

List<cd> cdList = new ArrayList<>();
cdLayout CdLayout;
//ArrayAdapter<String> adapter;

cd cd;
//ArrayList<String> list;
EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cds);

        cd = new cd();
        listView=findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("cd");
        list = new ArrayList<>();
        idlist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.activity_cd_info, R.id.borrowerInfo,list);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

//        adapter = new ArrayAdapter<String>(this,R.layout.activity_cd_info,R.id.borrowerInfo,list);
//        listView.setAdapter(adapter);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<cd> cdList= new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    cd = ds.getValue(cd.class);

                    list.add(cd.getName().toString() + "  -  " +cd.getSummary().toString());
                    idlist.add(cd.getId());
                    cdList.add(cd);

//                    list.add(cd.getName().toString() + "  -  " +cd.getSummary().toString());

                }
                CdLayout = new cdLayout(getApplicationContext(),cdList);
                listView.setAdapter(CdLayout);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(listOfcds.this, "Item clicked -"+adapter.getItem(i), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(listOfcds.this,cdHistory.class);
                intent.putExtra("id",idlist.get(i));
                startActivity(intent);
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
//                listOfcds.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}