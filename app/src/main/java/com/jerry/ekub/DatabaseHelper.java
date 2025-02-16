package com.jerry.ekub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_PHONE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Check if a username already exists
    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Add a new user to the database
    public boolean addUser(String firstName, String lastName, String username, String password, String phone) {
        if (isUsernameTaken(username)) {
            return false; // Username already exists
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1; // Returns true if the data was inserted successfully
    }

    // Check if a user exists with the given username and password
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Get user details by username
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME,
                new String[]{COLUMN_USERNAME, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PHONE},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
    }
}
