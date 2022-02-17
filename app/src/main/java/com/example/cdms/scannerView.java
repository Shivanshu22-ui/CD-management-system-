package com.example.cdms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scannerView extends AppCompatActivity  implements View.OnClickListener {
Button scanBtn;
TextView tvScanContent, tvScanFormat;
DatabaseReference dbCD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_view);
        IntentIntegrator i=new IntentIntegrator(this);

        scanBtn=findViewById(R.id.scanner);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator i=new IntentIntegrator(this);
        i.setPrompt("Scan a barcode or QRcode");
        i.setOrientationLocked(false);
        i.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cacelled", Toast.LENGTH_SHORT).show();
            }else{
                String code =result.getContents();
                Query q=FirebaseDatabase.getInstance().getReference("cd").orderByChild("id").equalTo(code);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String gotname=snapshot.child(code).child("name").getValue(String.class);
                            String gotsummary=snapshot.child(code).child("summary").getValue(String.class);
                            Intent intent= new Intent(scannerView.this,UserCDInfo.class);
                            intent.putExtra("name",gotname);
                            intent.putExtra("summary",gotsummary);
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


//                tvScanFormat.setText(result.getFormatName());
//                tvScanContent.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}