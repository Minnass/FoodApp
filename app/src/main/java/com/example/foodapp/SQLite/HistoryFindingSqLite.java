package com.example.foodapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodapp.Model.SQLiteModel.AddressModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryFindingSqLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "historyFindingManager";
    private static final String TABLE_HISTORYFINDING = "historyFinding";
    private static final String KEY_ID = "id";
    private static final String KEY_KEYWORD = "keyWord";

    public HistoryFindingSqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HISTORYFINDING + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_KEYWORD + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORYFINDING);

        // Create tables again
        onCreate(db);
    }


    public void addKeyword(String keyword) {
        if(checkExistingItem(keyword))
        {
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KEYWORD, keyword);
        db.insert(TABLE_HISTORYFINDING, null, values);
        db.close();
    }

    public void deleteALl() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_HISTORYFINDING;
        db.execSQL(selectQuery);
        db.close();
    }

    public List<String> getAllKeywords() {
        List<String> allKeywords = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORYFINDING;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
  // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               String temp=cursor.getString(1);
                // Adding item to list
                allKeywords.add(temp);
            } while (cursor.moveToNext());
        }
        db.close();
        // return item list
        return allKeywords;
    }

    public Boolean checkExistingItem(String keyword) {

        String Query = "SELECT *FROM " + TABLE_HISTORYFINDING + " where " + KEY_KEYWORD + " = " + "\'" + keyword + "\'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


}
