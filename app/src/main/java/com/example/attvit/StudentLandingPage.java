package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentLandingPage extends AppCompatActivity {
    TextView tvName, tvRegNo;
    Button btnGA, btnVA, btnCPwd;

    public SharedPreferences userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing_page);

        userDetails = getSharedPreferences("UserDetails",MODE_PRIVATE);
        initializeComponents();
        executeListeners();
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    private void initializeComponents() {
        tvName = findViewById(R.id.tvName);
        tvName.setText(userDetails.getString("user_name","Doctor Strange"));
        tvRegNo = findViewById(R.id.tvRno);
        tvRegNo.setText(userDetails.getString("user_id","14000605"));

        btnCPwd = findViewById(R.id.btnChangePwd);
        btnGA = findViewById(R.id.btnGiveAttd);
        btnVA = findViewById(R.id.btnViewAttd);
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="executeListeners">
    private void executeListeners(){

        btnCPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor prefEditor = userDetails.edit();
                prefEditor.putBoolean("isSignedIn",false);
                prefEditor.commit();

                Intent intent = new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    // </editor-fold>

}
