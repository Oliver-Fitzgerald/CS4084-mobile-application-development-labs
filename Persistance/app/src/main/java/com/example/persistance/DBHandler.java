package com.example.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHandler extends  SQLiteOpenHelper{

    private final static String DB_NAME = "namesdb";
    private final static int DB_VERSION = 1;
    private final static String TABLE_NAME = "names";
    private final static String ID_COL = "id";
    private final static String FIRST_COL = "first";
    private final static String LAST_COL = "last";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FIRST_COL + " TEXT,"
                + LAST_COL + "TEXT)";
        db.execSQL(query);
    }

    /**
     * add
     * Adds a row to the db with a col for first and last name
     * @param name
     */
    public void add(Name name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIRST_COL, name.getFirst());
        values.put(LAST_COL, name.getLast());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Name> getNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Name> names = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(new Name(cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No Changes To Schema Yet
    }
}