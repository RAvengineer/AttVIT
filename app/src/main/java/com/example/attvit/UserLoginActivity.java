package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etLoginID, etPassword;

    String signedIn = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initializeComponents();
        userHelper();
        executeClickListeners();

    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    private void initializeComponents() {
        btnLogin = findViewById(R.id.btnLogin);
        etLoginID = findViewById(R.id.etLoginID);
        etPassword = findViewById(R.id.etPassword);
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="executeClickListeners">
    private void executeClickListeners() {
        // <editor-fold default="collapsed" desc="btnLogin Click Listener">
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = etLoginID.getText().toString();
                String user_pwd = etPassword.getText().toString();

                SharedPreferences userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);

                if(userDetails.getBoolean("isSignedIn",false)){
                    if(userDetails.getString("user_pwd","error").equals(user_pwd)) {
                        Intent homeIntent = new Intent(UserLoginActivity.this, StudentLandingPage.class);
                        startActivity(homeIntent);
                        finish();
                        return;
                    }else{
                        Toast.makeText(UserLoginActivity.this, "Password Invalid!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                SharedPreferences.Editor prefEditor = userDetails.edit();
                prefEditor.putString("user_id", user_id);
                prefEditor.putString("user_pwd", user_pwd);
                prefEditor.putBoolean("isSignedIn",true);
                prefEditor.commit();

                Log.d("user_id", userDetails.getString("user_id", "null"));
                Log.d("user_pwd", userDetails.getString("user_pwd", "null"));
                Log.d("isSignedIn", String.valueOf(userDetails.getBoolean("isSignedIn",false)));

                Intent homeIntent=new Intent(UserLoginActivity.this,StudentLandingPage.class);
                startActivity(homeIntent);
                finish();
            }
        });
        // </editor-fold>
    }
// </editor-fold>

    // <editor-fold default="collapsed" desc="user Helper">
    private void userHelper(){
        SharedPreferences userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);
        if(userDetails.getBoolean("isSignedIn", false)){
            String temp = "";
            temp = userDetails.getString("user_id","Error!");
            etLoginID.setText(temp);
        }
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="check User Already Exists">
    private boolean checkUserAlreadyExists(String user_id){
        SharedPreferences userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String id = null;
        userDetails.getString("user_id",id);
        return(id.equals(user_id));
    }
    // </editor-fold>
}
