package com.example.foodapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodapp.Model.FoodModel;
import com.example.foodapp.Model.SQLiteModel.ItemCartModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFoodManagerSqLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favoriteFoodManager";
    private static final String TABLE_FOOD = "favoriteFood";
    private static final String KEY_ID = "id";
    private static final String KEY_FOODNAME = "foodName";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DISCRIPTION = "discription";
    private static final String KEY_DISCOUNT = "discount";
    private static final String KEY_QUANTITYSOLD = "quantity_sold";
    private static final String KEY_EATERNUMBER = "eater_number";


    public FavoriteFoodManagerSqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FOODNAME + " TEXT,"
                + KEY_PRICE + " FLOAT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DISCRIPTION + " TEXT,"
                + KEY_DISCOUNT + " INTEGER,"
                + KEY_QUANTITYSOLD + " INTEGER,"
                + KEY_EATERNUMBER + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);

        // Create tables again
        onCreate(db);
    }

    public void addFavoriteFood(FoodModel food) {

        if (checkExistingItem(food)) {
            return;
    } else
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOODNAME, food.getName());
        values.put(KEY_PRICE, food.getPrice());
        values.put(KEY_IMAGE,food.getImage());
        values.put(KEY_DISCRIPTION,food.getDescription());
        values.put(KEY_DISCOUNT,food.getDiscount());
        values.put(KEY_QUANTITYSOLD,food.getQuantity());
        values.put(KEY_EATERNUMBER,food.getEaterNumber());
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

}

    public List<FoodModel> getAllContacts() {
        List<FoodModel> itemList = new ArrayList<FoodModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoodModel food = new FoodModel("",0,"","",0,0,0);
               food.setName(cursor.getString(1));
                food.setPrice(Float.parseFloat(cursor.getString(2)));
                food.setImage(cursor.getString(3));
                food.setDescription(cursor.getString(4));
                food.setDiscount(Integer.parseInt(cursor.getString(5)));
                food.setQuantity(Integer.parseInt(cursor.getString(6)));
                food.setEaterNumber(Integer.parseInt(cursor.getString(7)));
                // Adding item to list
                itemList.add(food);
            } while (cursor.moveToNext());
        }
        db.close();
        // return item list
        return itemList;
    }

    public void deleteItem(FoodModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD, KEY_FOODNAME + " = ?",
                new String[]{String.valueOf(item.getName())});
        db.close();
    }

    public void deleteALl() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_FOOD;
        db.execSQL(selectQuery);
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOOD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }



    public Boolean checkExistingItem(FoodModel item) {

        String Query = "SELECT *FROM " + TABLE_FOOD + " where " + KEY_FOODNAME + " = " + "\'" + item.getName() + "\'";
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
