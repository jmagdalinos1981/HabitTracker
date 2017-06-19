package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;


public class MainActivity extends AppCompatActivity {
    /** Database helper that will provide us access to the database */
    private static HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDummyHabits();
                displayDatabaseInfo(readHabits());

                Toast.makeText(MainActivity.this, "Dummy habit data added", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo(readHabits());
    }

    private void displayDatabaseInfo(Cursor cursor) {


        // Find the TextView where the table information will be shown
        TextView headerTextView = (TextView) findViewById(R.id.text_view_header);
        TextView idTextView = (TextView) findViewById(R.id.text_view_id);
        TextView nameTextView = (TextView) findViewById(R.id.text_view_name);
        TextView dateTextView = (TextView) findViewById(R.id.text_view_date);
        TextView durationTextView = (TextView) findViewById(R.id.text_view_duration);
        TextView locationTextView = (TextView) findViewById(R.id.text_view_location);

        try {
            // Create a text header showing the number of entries and below it, show the contents
            // of the table
            headerTextView.setText("Number of habits in the table: " + cursor.getCount());
            idTextView.setText(HabitEntry._ID + "\n");
            nameTextView.setText(HabitEntry.COLUMN_NAME + "\n");
            dateTextView.setText(HabitEntry.COLUMN_DATE + "\n");
            durationTextView.setText(HabitEntry.COLUMN_DURATION + "\n");
            locationTextView.setText(HabitEntry.COLUMN_LOCATION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DURATION);
            int locationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_LOCATION);

            while (cursor.moveToNext()) {
                // Use the index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                String currentLocation = cursor.getString(locationColumnIndex);

                // Display the values from each column of the current row in the cursor in the
                // TextViews
                idTextView.append(currentID + "\n");
                nameTextView.append(currentName + "\n");
                dateTextView.append(currentDate + "\n");
                durationTextView.append(currentDuration + "\n");
                locationTextView.append(currentLocation + "\n");

            }
        } finally {
            // Close the cursor to release all its resources and make it invalid.
            cursor.close();
        }
    }

    private void insertDummyHabits() {
        // Create and/or open a database to write to it
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and the habit's attributes are the values.
        ContentValues values = new ContentValues();

        // Get values from string-array
        String[] habitNames = getResources().getStringArray(R.array.habitNames);
        String[] habitDates = getResources().getStringArray(R.array.habitDates);
        int[] habitDurations = getResources().getIntArray(R.array.habitDurations);
        String[] habitLocations = getResources().getStringArray(R.array.habitLocations);

        for (int i = 0; i < habitNames.length; i++) {
            // Entry i
            values.put(HabitEntry.COLUMN_NAME, habitNames[i]);
            values.put(HabitEntry.COLUMN_DATE, habitDates[i]);
            values.put(HabitEntry.COLUMN_DURATION, habitDurations[i]);
            values.put(HabitEntry.COLUMN_LOCATION, habitLocations[i]);

            // Insert a new row for the habit in the database, returning the ID of that new row.
            long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        }
    }

    private Cursor readHabits() {
        // Create and/or open a database to write to it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // will be used after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_DURATION,
                HabitEntry.COLUMN_LOCATION};

        // Perform a query on the habits table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,      // The name of the table
                projection,                 // The Columns to return
                null,                       // The columns for the WHERE clause
                null,                       // The values for the WHERE clause
                null,                       // Don't group the rows
                null,                       // Don't filter by row groups
                null);                      // The sort order

        return cursor;
    }
}
