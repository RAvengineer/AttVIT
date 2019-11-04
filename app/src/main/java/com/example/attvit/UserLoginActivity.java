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

import java.util.Objects;

public class UserLoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etName, etLoginID, etPassword;

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
        etName = findViewById(R.id.etName);
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
                String user_name = etName.getText().toString();
                String user_id = etLoginID.getText().toString();
                String user_pwd = etPassword.getText().toString();

                SharedPreferences userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);

                if(userDetails.getBoolean("isSignedIn",false)){
                    if(userDetails.getString("user_pwd","error").equals(user_pwd) &&
                        userDetails.getString("user_id","12").equalsIgnoreCase(user_id)) {
                        changeActivity(Objects.requireNonNull(userDetails.getString("user_prof", "dean")));
                        return;
                    }else{
                        Toast.makeText(UserLoginActivity.this, "Invalid Credentials !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                SharedPreferences.Editor prefEditor = userDetails.edit();
                prefEditor.putString("user_name", user_name);
                prefEditor.putString("user_id", user_id);
                prefEditor.putString("user_pwd", user_pwd);
                prefEditor.putBoolean("isSignedIn",true);
                prefEditor.commit();

                Log.d("user_name", userDetails.getString("user_name", "null"));
                Log.d("user_id", userDetails.getString("user_id", "null"));
                Log.d("user_pwd", userDetails.getString("user_pwd", "null"));
                Log.d("isSignedIn", String.valueOf(userDetails.getBoolean("isSignedIn",false)));

                changeActivity(Objects.requireNonNull(userDetails.getString("user_prof", "dean")));
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
            temp = userDetails.getString("user_name","Error!");
            etName.setText(temp);
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

    // <editor-fold default="collapsed" desc="change Activity">
    private void changeActivity(String prof){
        if(prof.equalsIgnoreCase("Student")){
            Intent homeIntent = new Intent(UserLoginActivity.this, StudentLandingPage.class);
            startActivity(homeIntent);
        }else{
            Intent homeIntent = new Intent(UserLoginActivity.this, TeacherLandingPage.class);
            startActivity(homeIntent);
        }
        finish();
    }
    // </editor-fold>
}
