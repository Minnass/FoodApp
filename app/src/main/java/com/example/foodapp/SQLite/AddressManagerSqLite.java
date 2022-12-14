package com.example.foodapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodapp.Model.SQLiteModel.AddressModel;


import java.util.ArrayList;
import java.util.List;

public class AddressManagerSqLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cartManager";
    private static final String TABLE_ADDRESS = "address";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PHONENUMBER = "phoneNumber";
    private static final String KEY_ADDRESS = "addresss";

    public AddressManagerSqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ADDRESS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PHONENUMBER + " TEXT,"
                + KEY_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);

        // Create tables again
        onCreate(db);
    }

    public void upDateAddress(AddressModel oldItem, AddressModel newItem) {
        deleteItem(oldItem);
        addAddress(newItem);
    }

    public void addAddress(AddressModel address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, address.getName());
        values.put(KEY_PHONENUMBER, address.getPhone());
        values.put(KEY_ADDRESS, address.getAddress());
        db.insert(TABLE_ADDRESS, null, values);
        db.close();
    }

    public void deleteItem(AddressModel address) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ADDRESS + " WHERE "
                + KEY_ADDRESS + " = " + "\'" + address.getAddress() + "\'"
                + " AND " + KEY_PHONENUMBER + " = " + "\'" + address.getPhone() + "\'"
                + " AND " + KEY_USERNAME + " = " + "\'" + address.getName() + "\'";
        db.execSQL(query);
        db.close();
    }

    public List<AddressModel> getAllContacts() {
        List<AddressModel> addressList = new ArrayList<AddressModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADDRESS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddressModel item = new AddressModel("", "", "");
                item.setName(cursor.getString(1));
                item.setPhone(cursor.getString(2));
                item.setAddress(cursor.getString(3));
                // Adding item to list
                addressList.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        // return item list
        return addressList;
    }


}
