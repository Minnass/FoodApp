package com.example.foodapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodapp.Model.SQLiteModel.ItemCartModel;

import java.util.ArrayList;
import java.util.List;

public class CartManagerSqLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cartManager";
    private static final String TABLE_CART = "cart";
    private static final String KEY_ID = "id";
    private static final String KEY_FOODNAME = "foodName";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_DISCOUNT = "discount";
    private static final String KEY_IMAGE = "image";


    public CartManagerSqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FOODNAME + " TEXT,"
                + KEY_PRICE + " INTEGER,"
                + KEY_DISCOUNT + " INTEGER,"
                + KEY_IMAGE + " TEXT,"
                + KEY_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        // Create tables again
        onCreate(db);
    }

    public void addCart(ItemCartModel item) {
        int currentQuantity = getItemQuantity(item);
        if (currentQuantity != 0) {
            updateQuantity(item, currentQuantity + 1);
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_FOODNAME, item.getFoodName());
            values.put(KEY_PRICE, item.getPrice());
            values.put(KEY_DISCOUNT, item.getDiscount());
            values.put(KEY_IMAGE, item.getImage());
            values.put(KEY_QUANTITY, item.getQuantity());
            db.insert(TABLE_CART, null, values);
            db.close();
        }
    }

    public List<ItemCartModel> getAllContacts() {
        List<ItemCartModel> itemList = new ArrayList<ItemCartModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemCartModel item = new ItemCartModel("", 0, 0, 0, "");
                item.setFoodName(cursor.getString(1));
                item.setPrice(Integer.parseInt(cursor.getString(2)));
                item.setDiscount(Integer.parseInt(cursor.getString(3)));
                item.setImage(cursor.getString(4));
                item.setQuantity(Integer.parseInt(cursor.getString(5)));
                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        // return item list
        return itemList;
    }

    public void deleteItem(ItemCartModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_FOODNAME + " = ?",
                new String[]{String.valueOf(item.getFoodName())});
        db.close();
    }

    public void deleteALl() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_CART;
        db.execSQL(selectQuery);
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getItemQuantity(ItemCartModel item) {
        if (checkExistingItem(item)) {
            String query = "SELECT " + KEY_QUANTITY + " FROM " + TABLE_CART + " where " + KEY_FOODNAME + " = " + "\'" + item.getFoodName() + "\'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                return Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
            db.close();
        }
        return 0;
    }

    public Boolean checkExistingItem(ItemCartModel item) {

        String Query = "SELECT *FROM " + TABLE_CART + " where " + KEY_FOODNAME + " = " + "\'" + item.getFoodName() + "\'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void updateQuantity(ItemCartModel item, int newQuantity) {
        String query = "UPDATE " + TABLE_CART + " SET " + KEY_QUANTITY + " = " + newQuantity
                + " WHERE " + KEY_FOODNAME + " = " + "\'" + item.getFoodName() + "\'";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void deleteSomeItems(List<ItemCartModel> list) {
        for (int i = 0; i < list.size(); i++) {
            deleteItem(list.get(i));
        }
    }
}
