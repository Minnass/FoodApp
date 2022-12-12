package com.example.foodapp.Enum;

import androidx.annotation.NonNull;

import java.io.Serializable;

public enum Categories implements Serializable {
    NOODLE {
        @NonNull
        @Override
        public String toString() {
            return "Phở bún cháo";
        }
    },
    FASTFOOD {
        @NonNull
        @Override
        public String toString() {
            return "FastFood";
        }
    },
    BEVERAGE {
        @NonNull
        @Override
        public String toString() {
            return "Đồ uống";
        }
    },
    RICE {
        @NonNull
        @Override
        public String toString() {
            return "Cơm";
        }
    },
    MEATANDFISH {
        @NonNull
        @Override
        public String toString() {
            return "Thịt, Cá";
        }
    },
    DESSERT {
        @NonNull
        @Override
        public String toString() {
            return "Tráng miệng";
        }
    };

    static public int getIndex(Categories value) {
        Categories[] categories = Categories.values();
        int index = 0;
        for (Categories item : categories) {
            if (item == value) {
                return index;
            }
            index++;
        }
        return 0;
    }

}