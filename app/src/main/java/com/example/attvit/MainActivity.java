package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // <editor-fold default="collapsed" desc="Splash Screen Code">
        int SPLASH_TIME_OUT = 1400;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent=new Intent(MainActivity.this,ChooseUserType.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        // </editor-fold>

    }
}
