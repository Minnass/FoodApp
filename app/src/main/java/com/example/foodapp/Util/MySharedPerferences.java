package com.example.foodapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MySharedPerferences {
    private static final String MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE";
    private static final String FIRST_RUNNING = "FIRST_RUNNING";


    public static Boolean isSavedBefore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(FIRST_RUNNING, false);
    }

    public static void setSavedBefore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_RUNNING, true);
        editor.apply();
    }

    public static String getValue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE, context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


    public static void setValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void deleteBefore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCE, context.MODE_PRIVATE);

        if (!isSavedBefore(context)) {
            //Chua co data
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("imagePath");
        editor.commit();
    }

    public static void getAvatar(ImageView view, Context context) {
        if (!MySharedPerferences.isSavedBefore(context)) {
            return;
        }
        String imgPath = MySharedPerferences.getValue(context, "imagePath");
        File savedAvatar = new File(imgPath, "avatar.jpg");
        try {
            Bitmap savedBitmap = BitmapFactory.decodeStream(new FileInputStream(savedAvatar));
            view.setImageBitmap(savedBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
