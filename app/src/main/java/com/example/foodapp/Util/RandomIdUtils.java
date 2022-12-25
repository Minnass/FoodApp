package com.example.foodapp.Util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RandomIdUtils {

    private static AtomicLong atomicCounter = new AtomicLong();

    public static String createId() {

        String currentCounter = String.valueOf(atomicCounter.getAndIncrement());
        String uniqueId = UUID.randomUUID().toString();

        return uniqueId + "-" + currentCounter;
    }
}