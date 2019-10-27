package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class StudentLandingPage extends AppCompatActivity {
    TextView tvName, tvRegNo;
    SharedPreferences userDetails = getSharedPreferences("UserDetails",MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing_page);
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    private void initializeComponents() {
        tvName = findViewById(R.id.tvName);
        tvName.setText(userDetails.getString("user_name","Doctor Strange"));
        tvRegNo = findViewById(R.id.tvRno);
        tvRegNo.setText(userDetails.getString("user_id","14000605"));
    }
    // </editor-fold>
}
