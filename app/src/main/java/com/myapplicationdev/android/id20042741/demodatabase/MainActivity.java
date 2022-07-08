package com.myapplicationdev.android.id20042741.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetTasks;
    TextView tvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);

        tvTasks = findViewById(R.id.tvViewTasks);


        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // creating an object of DBHelper and passing in this activity's context
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertTask("Submit RJ", "8 July 2022");
                db.close();
            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DBHelper db = new DBHelper(MainActivity.this);
                db.getTaskContext();

                ArrayList<String> data = db.getTaskContext();
                String txt = "";

                for(int i = 0; i < data.size(); i++){
                    Log.d("Database content", i +". "+data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }

                tvTasks.setText(txt);
            }
        });
    }
}