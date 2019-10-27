package com.example.attvit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attvit.DatabaseHelper.DatabaseHelper;

public class AddClassActivity extends AppCompatActivity {

    public DatabaseHelper myDb;

    EditText tableName , reg_no;
    String[] editCol = new String[4];
    Button btnAddData,btnAdddb;
    public int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        myDb = new DatabaseHelper(this);

        tableName = (EditText) findViewById(R.id.editText_Slot);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnAdddb = (Button) findViewById(R.id.button_adddb);
        reg_no = findViewById(R.id.editText_regno);


        AddData();
        addAll();

    }

    public void AddData (){
        btnAddData.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editCol[++i] = reg_no.getText().toString();

                    if(editCol[i].equalsIgnoreCase(reg_no.getText().toString() ) )
                        Toast.makeText(AddClassActivity.this,"Column Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(AddClassActivity.this,"Column Not Inserted",Toast.LENGTH_LONG).show();
                }

            }
        );
    }

    public void addAll(){
        btnAdddb.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    myDb.COL[0] = "Date";
                    for(int i=1 ; i < editCol.length ; i++)
                        myDb.COL[i] = editCol[i];
                    String s = tableName.getText().toString();
                    myDb.TABLE_NAME = s;

                    boolean isCreated = myDb.insertData( s, editCol );

                    if(isCreated) {
                        Toast.makeText(AddClassActivity.this,"DataBase Created",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),TeacherLandingPage.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(AddClassActivity.this,"DataBase Not Created",Toast.LENGTH_LONG).show();

                }
            }
        );

    }
}
