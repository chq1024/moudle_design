package org.beikei.design.utils;

public class LockHelper {

    public static void multiLock(Runnable runnable) {
        runnable.run();
    }
}
