package org.beikei.design;

import org.beikei.design.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class ArthasMainBoot {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 0, TimeUnit.SECONDS,
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
//        AtomicInteger atomicInteger = new AtomicInteger(1000);
//        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
//        while (atomicInteger.get() > 0) {
//            atomicInteger.decrementAndGet();
//            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(Thread.currentThread().getName() + "：执行成功！");
//
//            },threadPoolExecutor);
//            completableFutures.add(completableFuture);
//        }
//        Thread thread = new Thread(() -> {
//            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
//            voidCompletableFuture.join();
//            System.out.println("执行结束！");
//        });
//        thread.start();
//        System.out.println("请求结束！");
        method1();
    }

    private void method1() {
        System.out.println("执行method1");
        String method2Return = method2();
        System.out.println(method2Return);
    }
    private String method2() {
        method3();
        method4();
        return "执行method2";
    }

    private void method3() {
//        throw new RuntimeException("抛出异常");
        System.out.println("执行method3");
    }
    private void method4() {
        System.out.println("执行method4");
    }

    @EventListener(ContextClosedEvent.class)
    public void destroy() {
        threadPoolExecutor.shutdown();
    }
}