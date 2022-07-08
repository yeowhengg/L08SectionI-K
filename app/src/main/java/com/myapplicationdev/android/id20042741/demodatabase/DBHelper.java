package com.myapplicationdev.android.id20042741.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //starts the version with 1
    //increments the version by 1 whenever db schema changes
    private static final int DATABASE_VER = 1;

    //the filename of the database
    private static final String DATABASE_NAME = "tasks.db";

    //these are all the fields we want to put into the SQLite
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL =
                "CREATE TABLE "
                + TABLE_TASK + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";

        db.execSQL(createTableSQL);
        Log.i("info", "created table and fields");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if older db exist, we delete
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // recreate a new db
        onCreate(db);
    }

    public void insertTask(String description, String date){


        // getting an instance of the database to allow write operations
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues is used to store the value for the db
        ContentValues values = new ContentValues();

        // use the column name "description" as key, and the description parameter as value
        values.put(COLUMN_DESCRIPTION, description);

        // use the column name "date" as key, and the date parameter as value
        values.put(COLUMN_DATE, date);

        // inserting the row into TABLE_TASK
        db.insert(TABLE_TASK, null, values);

        Log.d("DEBUG", String.format("Successfully inserted %s %s into task", description, date));

        //closing the db
        db.close();
    }

    public ArrayList<String> getTaskContext(){
        // creating an array list that stores everything that is inside of the db
        ArrayList<String> tasks = new ArrayList<String>();

        // selecting the db's description field and its value
        String selectQuery = String.format("SELECT %s FROM %s", COLUMN_DESCRIPTION, TABLE_TASK);

        // getting an instance of the db to allow us to read the data
        SQLiteDatabase db = this.getReadableDatabase();

        // a cursor that allow the db to point to the field and its rows
        Cursor cursor = db.rawQuery(selectQuery, null);

        // this method checks if the first row of the db has any value. If it does not have a value,
        // the subsequent methods inside this if will not run. Because if first row in the database
        // has no value, it means that there is currently no data in the db.

        if(cursor.moveToFirst()){ // essentially means if cursor.movetofirst == true
            do{ // do the method
                tasks.add(cursor.getString(0)); // the "method"
            }while(cursor.moveToNext()); // while this is true
        }

        cursor.close();
        db.close();

        return tasks;
    }


}
