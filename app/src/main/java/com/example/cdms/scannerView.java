package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class scannerView extends AppCompatActivity  implements View.OnClickListener {
Button scanBtn, userLogout;
FirebaseAuth fAuth;
TextView username;
DatabaseReference dbCD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_view);
        IntentIntegrator i=new IntentIntegrator(this);

        scanBtn=findViewById(R.id.scanner);
        scanBtn.setOnClickListener(this);
        username=findViewById(R.id.Username);
        userLogout=findViewById(R.id.userLogout);


        // databse updates
        dbCD=FirebaseDatabase.getInstance().getReference("cd");

        // Firebase users
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user=  fAuth.getCurrentUser();
        String name = user.getEmail();
        String[] splited = name.split("@");
        name=splited[0];
        username.setText("Welcome "+name);
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator i=new IntentIntegrator(this);
        i.setPrompt("Scan a barcode or QRcode");
        i.setOrientationLocked(true);
        i.setCaptureActivity(CaptureActivityPortrait.class);
        i.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }else{
                String code =result.getContents();
                fAuth = FirebaseAuth.getInstance();
                FirebaseUser userUpdate=  fAuth.getCurrentUser();
                String borrower=userUpdate.getEmail();

                dbCD.child(code).child("user").setValue(borrower);
                Query q=FirebaseDatabase.getInstance().getReference("cd").orderByChild("id").equalTo(code);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            updateuser(code);

                            String gotname=snapshot.child(code).child("name").getValue(String.class);
                            String gotBranchCode=snapshot.child(code).child("BranchCode").getValue(String.class);
                            Intent intent= new Intent(scannerView.this,UserCDInfo.class);
                            intent.putExtra("name",gotname);
                            intent.putExtra("branchcode",gotBranchCode);
                            Log.d("luls",gotname);
                            Log.d("luls",gotBranchCode);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(scannerView.this,"something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(scannerView.this,"something wnt wrong",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateuser(String code) {
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("cd/"+code+"/borrower");
        String userKey=fAuth.getCurrentUser().getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users/"+userKey);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                UserTime ut=new UserTime(user.getName(),user.getEmail(),dtf.format(now),user.getKey());
                ref.child(ut.getTime()).setValue(ut);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); // signout user
        startActivity(new Intent(getApplicationContext(),UserLogin.class));
        finishAffinity();
    }
}