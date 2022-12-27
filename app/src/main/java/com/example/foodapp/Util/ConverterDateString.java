package com.example.foodapp.Util;

import android.util.Log;

public class ConverterDateString {


    public static String getConvertedDate(String date)
    {
        String[] subString= date.split("/");
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(subString[2]);
        stringBuffer.append("/");
        stringBuffer.append(subString[1]);
        stringBuffer.append("/");
        stringBuffer.append(subString[0]);
        return stringBuffer.toString();
    }
}
