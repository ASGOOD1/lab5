package com.example.lab5pdmjava;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog.Builder;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase my_db;
    Button btnAdd;
    Button btnModify;
    Button btnSearch;
    Button btnDelete;
    Button btnViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAdd = findViewById(R.id.btnMainAdd);
        btnModify = findViewById(R.id.btnMainModify);
        btnSearch = findViewById(R.id.btnMainSearch);
        btnDelete = findViewById(R.id.btnMainDelete);
        btnViewAll = findViewById(R.id.btnMainViewAll);

        btnAdd.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        my_db = openOrCreateDatabase("Students", Context.MODE_PRIVATE, null);
        my_db.execSQL("CREATE TABLE IF NOT EXISTS student(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,pren TEXT,dob TEXT,height TEXT);");
        my_db.close();
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
        else if(v == btnDelete) {
            Intent intent = new Intent(this, DeleteActivity.class);
            startActivity(intent);
        }
        else if(v == btnViewAll) {
            Cursor c = my_db.rawQuery("SELECT * FROM student", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No students found in database", this);
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (c.moveToNext()) {
                buffer.append("ID: ").append(c.getInt(0)).append("\n");
                buffer.append("First Name: ").append(c.getString(1)).append("\n");
                buffer.append("Last Name: ").append(c.getString(2)).append("\n");
                buffer.append("Date of birth: ").append(c.getString(3)).append("\n");
                buffer.append("Height: ").append(c.getString(4)).append("cm\n\n");
            }
            showMessage("Students Data", buffer.toString(), this);
            c.close();
            my_db.close();
        }
        else if(v == btnModify) {
            Intent intent = new Intent(this, ModifyActivity.class);
            startActivity(intent);
        }
        else if(v == btnSearch) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }
    static public void showMessage(String title, String message, Context context) {
        Builder builder = new Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}