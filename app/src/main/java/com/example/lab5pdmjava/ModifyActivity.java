package com.example.lab5pdmjava;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import static com.example.lab5pdmjava.MainActivity.showMessage;

import android.content.Context;
import android.database.Cursor;
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

import java.util.concurrent.atomic.AtomicInteger;

public class ModifyActivity extends AppCompatActivity {
    SQLiteDatabase my_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AtomicInteger student_id = new AtomicInteger(-1);
        my_db = openOrCreateDatabase("Students", Context.MODE_PRIVATE, null);
        EditText editTextFindID = findViewById(R.id.editTextModifyID);
        Button btnBack = findViewById(R.id.btnModifyBack);
        Button btnFetchData = findViewById(R.id.btnModifyFetchData);

        EditText editTextFName = findViewById(R.id.editTextModifyFirst);
        EditText editTextLName = findViewById(R.id.editTextModifyLast);
        EditText editTextDoB = findViewById(R.id.editTextModifyDoB);
        EditText editTextHeight = findViewById(R.id.editTextModifyHeight);
        Button btnModify = findViewById(R.id.btnModifyUpdate);


        editTextFName.setVisibility(INVISIBLE);
        editTextLName.setVisibility(INVISIBLE);
        editTextDoB.setVisibility(INVISIBLE);
        editTextHeight.setVisibility(INVISIBLE);
        btnModify.setVisibility(INVISIBLE);

        btnBack.setOnClickListener(l->{
            my_db.close();
            finish();
        });
        btnFetchData.setOnClickListener(l->{
            if(editTextFindID.getText().length() == 0) showMessage("Error!", "Invalid id.", this);
            else {
                Cursor c = my_db.rawQuery("SELECT * FROM student WHERE id = " + editTextFindID.getText(), null);
                if (c.getCount() == 0) {
                    showMessage("Error", "Student id not found", this);
                    editTextFName.setVisibility(INVISIBLE);
                    editTextLName.setVisibility(INVISIBLE);
                    editTextDoB.setVisibility(INVISIBLE);
                    editTextHeight.setVisibility(INVISIBLE);
                    btnModify.setVisibility(INVISIBLE);
                }
                else {
                    c.moveToNext();
                    student_id.set(c.getInt(0));
                    editTextFName.setText(c.getString(1));
                    editTextLName.setText(c.getString(2));
                    editTextDoB.setText(c.getString(3));
                    editTextHeight.setText(c.getString(4));
                    editTextFName.setVisibility(VISIBLE);
                    editTextLName.setVisibility(VISIBLE);
                    editTextDoB.setVisibility(VISIBLE);
                    editTextHeight.setVisibility(VISIBLE);
                    btnModify.setVisibility(VISIBLE);
                }
                c.close();
            }
        });
        btnModify.setOnClickListener(l->{
            my_db.execSQL("UPDATE student SET name='"+editTextFName.getText()+"',pren='"+editTextLName.getText()+"',dob='"
            + editTextDoB.getText() +"',height='" + editTextHeight.getText() + "' WHERE id=" + student_id);
            Toast.makeText(this, "Student data modified.", Toast.LENGTH_SHORT).show();
        });

    }
}