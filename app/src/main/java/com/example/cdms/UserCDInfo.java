package com.example.cdms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
        String name=getIntent().getExtras().getString("branchcode");
        String summary=getIntent().getExtras().getString("name");
        bookname.setText(name);
        bookSummary.setText(summary);
    }
}