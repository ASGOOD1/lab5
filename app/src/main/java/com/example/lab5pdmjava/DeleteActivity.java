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

public class DeleteActivity extends AppCompatActivity {
    SQLiteDatabase my_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        my_db = openOrCreateDatabase("Students", Context.MODE_PRIVATE, null);
        EditText editTextDelete = findViewById(R.id.editTextDeleteID);
        Button btnDelete = findViewById(R.id.btnDeleteDelete);
        Button btnBack = findViewById(R.id.btnDeleteBack);

        btnBack.setOnClickListener(l -> {
            my_db.close();
            finish();
        });
        btnDelete.setOnClickListener(l-> {
            if(editTextDelete.getText().length() == 0) showMessage("Error!", "Invalid data input!", this);
            else {
                int val = my_db.delete("student", "id = "+editTextDelete.getText(), null);
                if(val != 0) {
                    Toast.makeText(this, "Student deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else showMessage("Error!", "This student doesn't exist!", this);
            }
        });
    }
}