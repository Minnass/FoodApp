package com.example.foodapp.Util;

import java.text.NumberFormat;
import java.util.Locale;

public class VietNameseCurrencyFormat {
    private static Locale locale = new Locale("vi", "VN");
    private static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

    public static String getVietNameseCurrency(Object number) {
        return numberFormat.format(number);
    }
}
