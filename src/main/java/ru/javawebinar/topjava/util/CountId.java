package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CountId {
    private static final AtomicInteger count = new AtomicInteger(0);
    private static int id;

    public static int getId() {
        id = count.incrementAndGet();
        return id;
    }

}
