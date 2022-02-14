package com.example.cdms;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class generate extends AppCompatActivity {

    private ImageView qrimg;
    private EditText name;
    private EditText summary;
    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        qrimg= findViewById(R.id.qrgen);
        name= findViewById(R.id.name);
        summary= findViewById(R.id.summary);
        txt= findViewById(R.id.text1);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.toString().isEmpty()){
                    Toast.makeText(generate.this,"kuchh daal to",Toast.LENGTH_SHORT).show();
                }
                else{
                    String text=name.getText().toString();
                    try {
                        Bitmap qrcode=createBitmap(text);
                        qrimg.setImageBitmap(qrcode);
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
                    BarcodeFormat.QR_CODE, 100, 100, null);
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
        bitmap.setPixels(pixels, 0, 100, 0, 0, w, h);
        return bitmap;
    }
}