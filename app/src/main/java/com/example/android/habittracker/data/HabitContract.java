package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * API Contract for the HabitTracker app.
 */

public class HabitContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private
    private HabitContract() {}

    /**
     * Inner class that defines constant values for the habits database table.
     * Each entry in the table represents a single habit.
     */
    public static class HabitEntry implements BaseColumns {
        /** Name of database table for habits */
        public static final String TABLE_NAME = "habits";

        /**
         * Unique ID number for the habit (To be used in the database table)
         *
         * Type: INTEGER
         */
        public static final String STRING_ID = BaseColumns._ID;

        /**
         * Name of the habit
         *
         * Type: TEXT
         */
        public static final String COLUMN_NAME = "name";

        /**
         * Location where the habit takes place
         *
         * Type: TEXT
         */
        public static final String COLUMN_LOCATION = "location";

        /**
         * Date of the number (To be converted to date)
         *
         * Type: TEXT
         */
        public static final String COLUMN_DATE = "date";

        /**
         * Duration of the habit in hours
         *
         * Type: INTEGER
         */
        public static final String COLUMN_DURATION = "duration";

    }
}
