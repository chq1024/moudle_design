package org.beikei.design.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadHelper {

    private static ThreadPoolExecutor logThreadPool;
    private static final AtomicInteger threadIdx = new AtomicInteger();

    public static ThreadPoolExecutor logThreadGroup() {
        if (logThreadPool == null) {
            LockHelper.multiLock(() -> {
                if (logThreadPool == null) {
                    logThreadPool = new ThreadPoolExecutor(5, 10, 600, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                            r -> {
                                Thread thread = new Thread(r);
                                thread.setName("LOG_THREAD_" + threadIdx.getAndIncrement());
                                return thread;
                            }, new ThreadPoolExecutor.CallerRunsPolicy());
                }
            });
        }
        return logThreadPool;
    }
}
