package com.beikei.design.util;

public class ManualTransaction {

    public static void doInTransaction(Runnable runnable) {
        runnable.run();
    }
}
