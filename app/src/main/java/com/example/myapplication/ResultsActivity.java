package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ImageRequest;

public class ResultsActivity extends AppCompatActivity {
    ImageButton im;
    Button sr;
    ImageView fp,sp;
    ProgressBar p;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        fp = findViewById(R.id.first_page);
        sp = findViewById(R.id.second_page);
        im=findViewById(R.id.imageButton);
        sr=findViewById(R.id.send_req);
        p=findViewById(R.id.progressBar);

        sr.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle((R.string.send_request));
            builder.setMessage(R.string.message);
            builder.setPositiveButton(getString(R.string.send), (dialogInterface, i) -> finish());
            builder.setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).show();
        });

        im.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String uni = intent.getStringExtra("uni");


        String URL = "http://192.168.1.3/test/55_1.png";
        ImageRequest imageRequest = new ImageRequest(URL, response -> fp.setImageBitmap(response), 200, 200, null, null);
    }
}