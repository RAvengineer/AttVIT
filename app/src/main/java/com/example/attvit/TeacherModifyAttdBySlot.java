package com.example.attvit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attvit.DatabaseHelper.DatabaseHelper;

public class TeacherModifyAttdBySlot extends AppCompatActivity {

    TextView txtSlot, txtRegNo;
    Spinner spnChooseTimeStamp;
    Button btnGetDates, btnSubmit;
    DatabaseHelper myDb;

    public String timeStamp;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_modify_attd_by_slot);

        myDb = new DatabaseHelper(this);

        initializeComponents();
        executeListeners();
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    private void initializeComponents() {
        txtSlot = findViewById(R.id.txtSlot);
        txtRegNo = findViewById(R.id.txtRegNo);

        spnChooseTimeStamp = findViewById(R.id.spnCTimeStamp);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnGetDates = findViewById(R.id.btnGetDates);

    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="executeListeners">
    private void executeListeners() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String slot, reg, date;
                slot = txtSlot.getText().toString();
                reg = txtRegNo.getText().toString();
                date = timeStamp;
//                date = date + " GMT+05:30 2019";
                boolean isUpdate = myDb.updateData(slot, reg, date);
                if (isUpdate == true)
                    Toast.makeText(getApplicationContext(), "Data Update", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Data not Updated", Toast.LENGTH_LONG).show();

            }
        });

        btnGetDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtSlot.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(getApplicationContext(), "Go fu*k yourself!", Toast.LENGTH_SHORT).show();
                else
                    addItemOnSpinner();
            }
        });

        spnChooseTimeStamp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnChooseTimeStamp.setSelection(position);
                if (parent.getItemAtPosition(position).equals("Choose Slot")) {
                    // do nothing
                } else {
                    timeStamp = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="Add Item on Spinner">
    public void addItemOnSpinner() {
        DatabaseHelper mydb = new DatabaseHelper(this);
        String[] list = mydb.getDates(txtSlot.getText().toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnChooseTimeStamp.setAdapter(dataAdapter);
    }
    // </editor-fold>
}
