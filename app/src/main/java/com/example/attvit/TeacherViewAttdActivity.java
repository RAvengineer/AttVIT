package com.example.attvit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attvit.DatabaseHelper.DatabaseHelper;

public class TeacherViewAttdActivity extends AppCompatActivity {
    EditText etSlot;
    Spinner spnSee;
    Button btnGS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_attd);

        initializeComponents();
        executeListeners();
    }

    // <editor-fold default="collapsed" desc="initializeComponents">
    public void initializeComponents() {
        etSlot = findViewById(R.id.etSlot);
        spnSee = findViewById(R.id.spnSee);
        btnGS = findViewById(R.id.btnGetSlots);

    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="Add Item on Spinner">
    public void addItemOnSpinner() {
        DatabaseHelper mydb = new DatabaseHelper(this);
        String[] list = mydb.getDates(etSlot.getText().toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSee.setAdapter(dataAdapter);
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="executeListeners">
    public void executeListeners() {

        btnGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSlot.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(getApplicationContext(), "Go fu*k yourself!", Toast.LENGTH_SHORT).show();
                else
                    addItemOnSpinner();
            }
        });

        spnSee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnSee.setSelection(position);
                if (parent.getItemAtPosition(position).equals("Choose Slot")) {
                    // do nothing
                } else {
                    String date = parent.getItemAtPosition(position).toString();
                    Intent intent = new Intent(getApplicationContext(), TeacherViewAttdBySlotActivity.class);
                    intent.putExtra("date", date);
                    intent.putExtra("slot", etSlot.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    // </editor-fold>
}
