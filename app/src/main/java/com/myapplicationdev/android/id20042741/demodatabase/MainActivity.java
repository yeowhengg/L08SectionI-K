package com.myapplicationdev.android.id20042741.demodatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import java.time.LocalDate;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetTasks, btnDate;
    TextView tvChosenDate, tvResults;
    DatePicker datePicker;
    EditText etUserTask;
    int btnDateCounter = 0;
    ArrayAdapter adapter;
    ListView lvResults;
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        btnDate = findViewById(R.id.btnDate);
        datePicker = findViewById(R.id.dateId);
        tvChosenDate = findViewById(R.id.tvChosenDate);
        etUserTask = findViewById(R.id.userTaskID);
        lvResults = findViewById(R.id.lvResultsID);
        tvResults = findViewById(R.id.tvResultsID);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, String.format("%d",btnDateCounter), Toast.LENGTH_LONG).show();
                if(btnDate.isPressed() && btnDateCounter == 0){
                    datePicker.setVisibility(View.VISIBLE);
                    btnDate.setText("Confirm");
                    btnDateCounter = 1;
                }
                else{
                    date = dateValidation(datePicker) ? String.format("%d/%d/%d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()) : "Invalid Date";                    btnDate.setText("Choose Date");
                    datePicker.setVisibility(View.GONE);
                    btnDateCounter = 0;
                    tvChosenDate.setText(date);
                }
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // creating an object of DBHelper and passing in this activity's context
                DBHelper db = new DBHelper(MainActivity.this);

                String userTask = etUserTask.getText().toString();
                String userDate = tvChosenDate.getText().toString();

                if(!userTask.isEmpty()){
                    if(!userDate.equalsIgnoreCase("invalid date") && !userDate.equalsIgnoreCase("date chosen:")){
                        db.insertTask(userTask, userDate);
                        db.close();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Ensure date is in the right format", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Ensure Task is filled up", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<Task> data = db.getTaskContext();
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, data);
                lvResults.setAdapter(adapter);

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+data.get(i));
                    txt += data.get(i).getId() + ". " + data.get(i).getDescription() + "\n";
                }
                tvResults.setText(txt);

            }
        });

    }

    // date validation return true or false
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean dateValidation(DatePicker date){
        LocalDate.now().getYear();
        if(date.getYear() == LocalDate.now().getYear()){
            if(date.getMonth() + 1 == LocalDate.now().getMonth().getValue()){
                if(date.getDayOfMonth() <= LocalDate.now().getDayOfMonth()){
                    Toast.makeText(MainActivity.this, "Please select a date that includes today or later",Toast.LENGTH_LONG);
                    return false;
                }
            }
            else if(date.getMonth() + 1 <= LocalDate.now().getMonthValue()){
                return false;
            }
        }
        return true;

    }
}