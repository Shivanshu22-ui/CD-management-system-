package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    EditText Email, Password;
    Button LoginBtn;
    TextView BackMenuBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Email = findViewById(R.id.AdminEmail);
        Password = findViewById(R.id.AdminPassword);
        LoginBtn= findViewById(R.id.AdminLogin);
        BackMenuBtn = findViewById(R.id.BackMenu);
        progressBar = findViewById(R.id.progressBar3);
        fAuth = FirebaseAuth.getInstance();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Password.setError("Password is required");
                    return;
                }

                if(password.length()<6){
                    Password.setError("Password length should be more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminLogin.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(AdminLogin.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        BackMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AdminUserVerification.class));
            }
        });
    }
}