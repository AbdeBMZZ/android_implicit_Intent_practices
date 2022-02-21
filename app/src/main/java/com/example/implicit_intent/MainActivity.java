package com.example.implicit_intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText zone1, zone2;
    Button btn_dialer, btn_sms, btn_page_web, btn_web_search, btn_map, btn_call;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zone1 = findViewById(R.id.zone1);
        zone2 = findViewById(R.id.zone2);

        btn_dialer = findViewById(R.id.btn_dialer);
        btn_sms = findViewById(R.id.btn_sms);
        btn_page_web = findViewById(R.id.btn_page_web);
        btn_web_search = findViewById(R.id.btn_web_search);
        btn_map = findViewById(R.id.btn_map);
        btn_call = findViewById(R.id.btn_call);

        img = findViewById(R.id.imageView);

        btn_dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ zone1.getText().toString()));
                startActivity(i);
            }
        });
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ zone1.getText().toString()));
                i.putExtra("sms_body", zone2.getText().toString());
                startActivity(i);
            }
        });
        btn_page_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http:://"+ zone1.getText().toString()));
                startActivity(i);
            }
        });
        btn_web_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra("query", zone1.getText().toString());
                startActivity(i);
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+ zone1.getText().toString() + "," + zone2.getText().toString()));
                startActivity(i);
            }
        });
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + zone1.getText().toString()));
                startActivity(intent);            }
        });

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            img.setImageBitmap((Bitmap) result.getData().getExtras().get("data"));
                        }
                    }
                });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(i);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}