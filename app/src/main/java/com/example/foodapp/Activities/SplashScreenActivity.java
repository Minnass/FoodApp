package com.example.foodapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    TextView login,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mappingID();
        handleCLick();
    }
    void  mappingID()
    {
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
    }
    void handleCLick()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SplashScreenActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}