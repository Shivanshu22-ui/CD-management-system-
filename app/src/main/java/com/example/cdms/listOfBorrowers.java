package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listOfBorrowers extends AppCompatActivity {
ListView listView;
FirebaseDatabase database;
DatabaseReference ref;
ArrayList <String> list;
ArrayAdapter <String> adapter;
Borrower borrower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_borrowers);

        borrower = new Borrower();
        listView=findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("cd");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.activity_borrower_info, R.id.borrowerInfo,list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    borrower = ds.getValue(Borrower.class);
                    list.add(borrower.getName().toString() + "  -  " +borrower.getSummary().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(listOfBorrowers.this, "Item clicked -"+adapter.getItem(i), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),BorrowerHistory.class));
            }
        });
    }
}