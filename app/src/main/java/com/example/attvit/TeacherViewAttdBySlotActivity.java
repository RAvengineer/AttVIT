package com.example.attvit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attvit.DatabaseHelper.DatabaseHelper;

public class TeacherViewAttdBySlotActivity extends AppCompatActivity {

    ListView lvAttendance;

    public String slot, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_attd_by_slot);

        Intent getData = getIntent();
        slot = getData.getStringExtra("slot");
        date = getData.getStringExtra("date");
        setTitle(slot + " " + date.substring(0, 19));
        Toast.makeText(this, slot + " " + date, Toast.LENGTH_SHORT).show();

        initializeComponents();
        addDataInListView();
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    public void initializeComponents() {
        lvAttendance = findViewById(R.id.lvAttendance);
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="Add data in ListView">
    public void addDataInListView() {
        DatabaseHelper mydb = new DatabaseHelper(this);

        String[] studentsAttnd = mydb.getAttendance(slot, date);
        String[] allStudents = mydb.getColumnNames(slot);

        int countPresent = 0;
        for (int i = 1; i < studentsAttnd.length; i++)
            if (studentsAttnd[i].equals("P"))
                countPresent++;
        String[] studentsPresent = new String[countPresent];
        countPresent = 0;
        for (int i = 1; i < studentsAttnd.length; i++)
            if (studentsAttnd[i].equals("P")) {
                studentsPresent[countPresent++] = allStudents[i];
                Log.d("Student Added", allStudents[i]);
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TeacherViewAttdBySlotActivity.this, android.R.layout.simple_list_item_1, studentsPresent);
        lvAttendance.setAdapter(adapter);
    }
    // </editor-fold>
}
