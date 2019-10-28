package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attvit.util.ConnectionUtil;

public class TeacherLandingPage extends AppCompatActivity {

    TextView tvName, tvEmpNo;
    Button btnTA, btnVA, btnMA, btnCPwd, btnAddClass;

    public SharedPreferences userDetails;

    public ConnectionUtil wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_landing_page);

        userDetails = getSharedPreferences("UserDetails",MODE_PRIVATE);

        wifi = new ConnectionUtil();
        wifi.wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        initializeComponents();
        executeListeners();
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    private void initializeComponents() {
        tvName = findViewById(R.id.tvName);
        Log.d("User name Teacher",userDetails.getString("user_name","Doctor Strange"));
        tvName.setText(userDetails.getString("user_name","Doctor Strange"));
        tvEmpNo = findViewById(R.id.tvEno);
        tvEmpNo.setText(userDetails.getString("user_id","14000605"));

        btnCPwd = findViewById(R.id.btnChangePwd);
        btnTA = findViewById(R.id.btnTakeAttd);
        btnTA.setBackgroundColor(Color.RED);
        btnVA = findViewById(R.id.btnViewAttd);
        btnMA = findViewById(R.id.btnModifyAttd);
        btnAddClass = findViewById(R.id.btnAddClass);
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

        btnTA.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (wifi.isEnabled()) {
                    btnTA.setBackgroundColor(Color.RED);
                    btnTA.setText("Take Attendance");
                    wifi.disableWifi();
                } else {
                    btnTA.setBackgroundColor(Color.GREEN);
                    btnTA.setText("Taking...");
                    wifi.enableWifi();
                }
            }
        });

        btnVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddClassActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    // </editor-fold>
}
