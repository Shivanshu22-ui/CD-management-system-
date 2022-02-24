package com.example.cdms;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.utilities.PushIdGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;

public class generate extends AppCompatActivity {

    private ImageView qrimg;
    private EditText name;
    private TextView txt;
    private Button btn;
    private String fund="ITH";
    private final String[] branchlist ={"A", "G", "Q", "C", "DC", "E", "CP"};
    private Spinner spinner;
    private String BranchCode;
    private String Date="12/02/2022";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        qrimg= findViewById(R.id.qrgen);
        name= findViewById(R.id.name);
        txt= findViewById(R.id.text1);
        btn=findViewById(R.id.btn);
        spinner=findViewById(R.id.spinner);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(generate.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,branchlist);
        spinner.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cd");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().isEmpty()){
                    Toast.makeText(generate.this,"Name must not be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    String text=name.getText().toString();
                    try {
                        String Name=name.getText().toString();
                        String key=myRef.push().getKey();
                        String id=key;
                        String user=null;
                        BranchCode= (String) spinner.getSelectedItem();
                        cd cd =new cd(Name,id,user,Date,fund,BranchCode);

                        myRef.child(key).setValue(cd);

                        Bitmap qrcode=createBitmap(key);
                        qrimg.setImageBitmap(qrcode);
                        txt.setText("Here is the QR code");
                        Toast.makeText(generate.this,"Data inserted",Toast.LENGTH_SHORT).show();
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    Bitmap createBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 500, 500, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;

            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    public void onITHclicked(View view) {
        fund="ITH";
        Log.d("luls",fund);
    }

    public void onATGclicked(View view) {
        fund="ATG";
        Log.d("luls",fund);
    }
}