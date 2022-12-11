package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    private  Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn = findViewById(R.id.Login_btn);
        TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.text_input);
        EditText editText = textInputLayout.getEditText();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ResultsActivity.class);

                String uni =  editText.getText().toString();
                i.putExtra("uni", uni);
                startActivity(i);
            }
        });
    }
}