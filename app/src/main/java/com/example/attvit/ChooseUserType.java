package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.InetAddress;

public class ChooseUserType extends AppCompatActivity {
    Button btnTeacher, btnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        initializeComponents();
        executeClickListeners();
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    private void initializeComponents(){
        btnTeacher = findViewById(R.id.btnTeacher);
        btnStudent = findViewById(R.id.btnStudent);
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="executeClickListeners">
    private void executeClickListeners(){
        // <editor-fold default="collapsed" desc="btnTeacher Click Listener">
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = userDetails.edit();
                prefEditor.putString("user_prof", "Teacher");
                prefEditor.commit();

                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // </editor-fold>

        // <editor-fold default="collapsed" desc="btnStudent Click Listener">
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = userDetails.edit();
                prefEditor.putString("user_prof", "Student");
                prefEditor.commit();

                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // </editor-fold>


    }
    // </editor-fold>
}
