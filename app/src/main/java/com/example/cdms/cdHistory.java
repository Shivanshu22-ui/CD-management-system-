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

import java.util.ArrayList;

public class cdHistory extends AppCompatActivity {
    private String id;
    private ArrayList<User> userlist;
    private ListView listview;
    private ArrayList<String> timelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd_history);

        id=getIntent().getExtras().getString("id");
        userlist=new ArrayList<>();
        timelist=new ArrayList<>();
        listview= findViewById(R.id.listview2);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("cd/"+id+"/borrower");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cdHistoryAdapter adapter =new cdHistoryAdapter(getApplicationContext(),userlist,timelist);
                for(DataSnapshot snp :snapshot.getChildren()){
                    finduser(snp.getValue(String.class),snp.getKey());
                }

                adapter.notifyDataSetChanged();
                listview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finduser(String key, String dat) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userlist.add(user);
                timelist.add(dat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}