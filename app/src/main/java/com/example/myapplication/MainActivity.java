package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler=new Handler();
        handler.postDelayed(() -> {
            Intent i=new Intent(getApplicationContext(),login.class);
            startActivity(i);
            finish();
        },1500);
    }
}