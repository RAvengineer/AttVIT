package com.example.attvit;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attvit.DatabaseHelper.DatabaseHelper;

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
        tvRegNo = findViewById(R.id.tvEno);
        tvRegNo.setText(userDetails.getString("user_id","14000605"));

        btnCPwd = findViewById(R.id.btnChangePwd);
        btnGA = findViewById(R.id.btnTakeAttd);
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
                Intent intent = new Intent(getApplicationContext(), WifiP2P_Socket.class);
                startActivity(intent);
            }
        });

        btnVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showMessage();

            }
        });

    }
    public  void showMessage(){

        DatabaseHelper mydb = new DatabaseHelper(this);
        Cursor res = mydb.getSDates();
        StringBuffer buffer = new StringBuffer();
        for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            Log.d("Dates for student: ", res.getString(0));
            buffer.append("Attendance for : " + res.getString(0)  + "\n");
            //res[count++] = dbCursor.getString(0);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Info :");
        builder.setMessage(buffer.toString());
        builder.show();

    }
    // </editor-fold>

}
