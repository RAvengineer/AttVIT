package com.example.attvit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherViewAttdBySlotActivity extends AppCompatActivity {

    public String slot, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_attd_by_slot);

        Intent getData = getIntent();
        slot = getData.getStringExtra("slot");
        date = getData.getStringExtra("date");
        Toast.makeText(this, slot + " " + date, Toast.LENGTH_SHORT).show();
    }
}
