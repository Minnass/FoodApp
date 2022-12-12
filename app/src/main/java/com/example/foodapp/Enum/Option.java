package com.example.foodapp.Enum;

import androidx.annotation.NonNull;

public enum Option {
    Newness{
        @NonNull
        @Override
        public String toString() {
            return "Mới nhất";
        }
    },
    ATTRACTION
            {
                @NonNull
                @Override
                public String toString() {
                    return "Bán chạy";
                }
            }
}
