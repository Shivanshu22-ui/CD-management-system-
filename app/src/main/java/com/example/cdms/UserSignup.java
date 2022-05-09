package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Scanner;

public class UserSignup extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mConfirmPassword ;
    Button mSignup;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    EditText muserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        mFullName = findViewById(R.id.PersonName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        mSignup = findViewById(R.id.signUp);
        mLoginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        muserid=findViewById(R.id.userid);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),UserLogin.class));
            finish();
        }

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                String userid=muserid.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)){
                    mConfirmPassword.setError("Confirm password");
                    return;
                }

                if(password.length()<6){
                    mPassword.setError("Password length should be more than 6 characters");
                    return;
                }

                if(!(password.toString().equals(confirmPassword.toString()))){
                    mConfirmPassword.setError("Passwords do not match please re-enter");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            insertdata(name,email,password,task.getResult().getUser().getUid(),userid);
                            Toast.makeText(UserSignup.this,"Signup Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), scannerView.class));
                        }
                        else{
                            Toast.makeText(UserSignup.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserLogin.class));
            }
        });
    }

    private void insertdata(String name, String email, String password, String key,String userid) {
        User user=new User(name,email,password,key,false,userid);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
        ref.child(key).setValue(user);
    }
}