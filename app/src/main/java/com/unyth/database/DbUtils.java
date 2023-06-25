package com.unyth.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unyth.eventor.DataItem;

import java.util.ArrayList;
import java.util.List;

public class DbUtils {
    private final DatabaseHelper dbHelper;

    public DbUtils(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define the columns to retrieve from the database
        String[] projection = {"id"};

        // Define the WHERE clause
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        // Execute the query
        Cursor cursor = db.query("User", projection, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        if (userExists) {
            // Username and password exist in the database
            return true;
        } else {
            // Username and password don't exist, create the entry
            SQLiteDatabase insertDb = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);

            long newRowId = insertDb.insert("User", null, values);

            insertDb.close();

            return false;
        }
    }

    public long createEvent(String title, String subtitle, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("subtitle", subtitle);
        values.put("date", date);

        long newRowId = db.insert("Event", null, values);

        db.close();

        return newRowId;
    }

    // Method to get all events from the database
    public List<DataItem> getAllEvents() {
        List<DataItem> eventList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"id", "title", "subtitle", "date"};

        Cursor cursor = db.query("Event", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow("subtitle"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

            DataItem event = new DataItem(id, title, subtitle, date);
            eventList.add(event);
        }

        cursor.close();
        db.close();

        return eventList;
    }


    public boolean updateEvent(DataItem event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", event.getTitle());
        values.put("subtitle", event.getSubtitle());
        values.put("date", event.getDate());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(event.getId())};

        int rowsAffected = db.update("Event", values, whereClause, whereArgs);

        db.close();

        return rowsAffected > 0;
    }

    public void deleteEvent(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        db.delete("Event", whereClause, whereArgs);

        db.close();
    }

    public DataItem getEventById(int eventId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"id", "title", "subtitle", "date"};

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(eventId)};

        Cursor cursor = db.query("Event", projection, selection, selectionArgs, null, null, null);

        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String subtitle = cursor.getString(cursor.getColumnIndexOrThrow("subtitle"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

        DataItem event = new DataItem(id, title, subtitle, date);

        cursor.close();
        db.close();

        return event;
    }
}