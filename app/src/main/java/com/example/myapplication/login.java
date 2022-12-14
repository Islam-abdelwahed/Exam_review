package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn = findViewById(R.id.Login_btn);
        TextInputLayout textInputLayout = findViewById(R.id.text_input);
        EditText editText = textInputLayout.getEditText();
        btn.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),ResultsActivity.class);
            String uni = editText.getText().toString();
            if(!uni.isEmpty()) {
                i.putExtra("uni", uni);
                startActivity(i);
            }else {
                Toast t = Toast.makeText(this,"Please Enter Code",Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}