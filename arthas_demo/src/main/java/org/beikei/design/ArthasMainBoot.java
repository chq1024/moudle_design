package org.beikei.design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class ArthasMainBoot {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 600, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("Bk-Thread-" + thread.getId());
                return thread;
            },new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        SpringApplication.run(ArthasMainBoot.class,args);
    }

    @RequestMapping("/exec")
    public void exec() {
//        System.out.println("执行成功！");
        AtomicInteger atomicInteger = new AtomicInteger(1000);
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        while (atomicInteger.get() > 0) {
            atomicInteger.decrementAndGet();
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "：执行成功！");
            },threadPoolExecutor);
            completableFutures.add(completableFuture);
        }
        Thread thread = new Thread(() -> {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
            voidCompletableFuture.join();
            System.out.println("执行结束！");
        });
        thread.start();
        System.out.println("请求结束！");

    }

    @EventListener(ContextClosedEvent.class)
    public void destroy() {
        threadPoolExecutor.shutdown();
    }
}