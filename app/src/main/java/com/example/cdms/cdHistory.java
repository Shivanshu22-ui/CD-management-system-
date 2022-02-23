package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class cdHistory extends AppCompatActivity {
    private String id;
    private ListView listview;
    private cdHistoryAdapter adapter;
    private ArrayList<UserTime> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd_history);

        id=getIntent().getExtras().getString("id");
        listview= findViewById(R.id.listview2);
        userlist=new ArrayList<>();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("cd/"+ id+"/borrower");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp:snapshot.getChildren()){
                    String name=snp.child("name").getValue(String.class);
                    String key=snp.child("id").getValue(String.class);
                    String time=snp.child("time").getValue(String.class);
                    String email=snp.child("email").getValue(String.class);

                    UserTime ut= new UserTime(name,email,time,key);
                    userlist.add(ut);
                }
                ArrayList<UserTime> revut=new ArrayList<>();
                for(int i=userlist.size()-1;i>=0;i--){
                    revut.add(userlist.get(i));
                }
                adapter=new cdHistoryAdapter(getApplicationContext(),revut);
                listview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}