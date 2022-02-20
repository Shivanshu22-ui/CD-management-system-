package com.example.cdms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button genbtn;
    private  Button scanbtn,listbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        genbtn=findViewById(R.id.generate);
        scanbtn=findViewById(R.id.scan);
        listbtn=findViewById(R.id.listOfUsers);


        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, listOfcds.class);
                startActivity(intent);
            }
        });


        genbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,generate.class);
                startActivity(intent);
            }
        });
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,scannerView.class);
                startActivity(intent);
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); // signout user
        startActivity(new Intent(getApplicationContext(),UserLogin.class));
        finishAffinity();
    }
}