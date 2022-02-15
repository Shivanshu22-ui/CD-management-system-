package com.example.cdms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scannerView extends AppCompatActivity  implements View.OnClickListener {
Button scanBtn;
TextView tvScanContent, tvScanFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_view);
        IntentIntegrator i=new IntentIntegrator(this);

        scanBtn=findViewById(R.id.scan);
        tvScanContent=findViewById(R.id.tvScanContent);
        tvScanFormat=findViewById(R.id.scanFormat);

        scanBtn.setOnClickListener(this);

//        scanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//        IntentIntegrator i=new IntentIntegrator(this);
//        i.setPrompt("Scan a barcode or QRcode");
//        i.setOrientationLocked(false);
//        i.initiateScan();
//            }
//        });
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
                tvScanFormat.setText(result.getFormatName());
                tvScanContent.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}