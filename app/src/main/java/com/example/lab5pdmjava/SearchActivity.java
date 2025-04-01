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
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicReference;

public class SearchActivity extends AppCompatActivity {
    SQLiteDatabase my_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        my_db = openOrCreateDatabase("Students", Context.MODE_PRIVATE, null);
        EditText editTextSearchID = findViewById(R.id.editTextSearch);
        Button btnSearch = findViewById(R.id.btnSearchSearch);
        Button btnBack = findViewById(R.id.btnSearchBack);

        btnBack.setOnClickListener(l->{
            my_db.close();
            finish();
        });

        TextView textViewID = findViewById(R.id.textViewID);
        TextView textViewFirstName = findViewById(R.id.textViewFirstName);
        TextView textViewLastName = findViewById(R.id.textViewLastName);
        TextView textViewDoB = findViewById(R.id.textViewDoB);
        TextView textViewHeight = findViewById(R.id.textViewHeight);

        textViewID.setVisibility(INVISIBLE);
        textViewFirstName.setVisibility(INVISIBLE);
        textViewLastName.setVisibility(INVISIBLE);
        textViewDoB.setVisibility(INVISIBLE);
        textViewHeight.setVisibility(INVISIBLE);

        AtomicReference<String> search_by = new AtomicReference<>("name");

        RadioButton searchFirst = findViewById(R.id.selectByFirst);
        RadioButton searchLast = findViewById(R.id.searchByLast);

        searchFirst.setOnClickListener(v->{
            searchFirst.setChecked(true);
            searchLast.setChecked(false);
            search_by.set("name");
        });
        searchLast.setOnClickListener(v->{
            searchFirst.setChecked(false);
            searchLast.setChecked(true);
            search_by.set("pren");
        });

        btnSearch.setOnClickListener(l->{
            if(editTextSearchID.getText().length() == 0) showMessage("Error! ", "Invalid data.", this);
            else {

                String string = "SELECT * FROM student WHERE "+search_by+" LIKE '%"+ editTextSearchID.getText() + "%'";

                Cursor c = my_db.rawQuery(string, null);
                if (c.getCount() == 0) {
                    showMessage("Error", "Student not found", this);
                    textViewID.setVisibility(INVISIBLE);
                    textViewFirstName.setVisibility(INVISIBLE);
                    textViewLastName.setVisibility(INVISIBLE);
                    textViewDoB.setVisibility(INVISIBLE);
                    textViewHeight.setVisibility(INVISIBLE);
                }
                else {
                    c.moveToNext();
                    String str = "ID: "+ c.getInt(0);
                    textViewID.setText(str);
                    str = "First name: "+ c.getString(1);
                    textViewFirstName.setText(str);
                    str = "Last name: "+ c.getString(2);
                    textViewLastName.setText(str);
                    str = "Date of Birth: "+ c.getString(3);
                    textViewDoB.setText(str);
                    str = "Height: " + c.getString(4) + "cm";
                    textViewHeight.setText(str);
                    textViewID.setVisibility(VISIBLE);
                    textViewFirstName.setVisibility(VISIBLE);
                    textViewLastName.setVisibility(VISIBLE);
                    textViewDoB.setVisibility(VISIBLE);
                    textViewHeight.setVisibility(VISIBLE);
                }
                c.close();
            }
        });
    }
}