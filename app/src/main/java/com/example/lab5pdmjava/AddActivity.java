package com.example.lab5pdmjava;


import static com.example.lab5pdmjava.MainActivity.showMessage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {
    SQLiteDatabase my_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        my_db = openOrCreateDatabase("Students", Context.MODE_PRIVATE, null);
        EditText editFName = findViewById(R.id.editTextFirstName);
        EditText editLName = findViewById(R.id.editTextLastName);
        EditText editDoB = findViewById(R.id.editTextDoB);
        EditText editHeight = findViewById(R.id.editTextHeight);

        Button btnAdd = findViewById(R.id.btnAddAdd);
        Button btnBack = findViewById(R.id.btnAddBack);

        btnBack.setOnClickListener(l -> {
            my_db.close();
            finish();

        });
        btnAdd.setOnClickListener(l->{
            if(editFName.getText().length() != 0 && editLName.getText().length() != 0 && editDoB.getText().length() != 0 && editHeight.getText().length() != 0) {

                my_db.execSQL("INSERT INTO student(name, pren, dob, height) VALUES ('" +
                        editFName.getText() + "','" + editLName.getText() + "','"
                        + editDoB.getText() + "','" + editHeight.getText()
                        + "')");

                Toast.makeText(this, "New student added", Toast.LENGTH_SHORT).show();
                finish();

            }
            else {
                showMessage("Error!", "Invalid data input!", this);
            }
        });
    }
}