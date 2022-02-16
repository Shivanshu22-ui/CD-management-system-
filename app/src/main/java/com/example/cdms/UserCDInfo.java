package com.example.cdms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class UserCDInfo extends AppCompatActivity {
public static TextView bookname,bookSummary;
    DatabaseReference dbCD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cdinfo);

        bookname=findViewById(R.id.bookname);
        bookSummary=findViewById(R.id.bookSummary);
        Bundle extras= getIntent().getExtras();
        bookname.setText(extras.getString("name"));
        bookSummary.setText(extras.getString("summary"));
    }
}