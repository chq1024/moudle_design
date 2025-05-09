package org.beikei.design;

import org.dromara.dynamictp.core.DtpRegistry;
import org.dromara.dynamictp.core.support.adapter.ThreadPoolExecutorAdapter;
import org.dromara.dynamictp.core.support.proxy.ThreadPoolExecutorProxy;
import org.dromara.dynamictp.spring.annotation.EnableDynamicTp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableDynamicTp
@RestController
public class DynamicTpMain {
    public static void main(String[] args) {
        SpringApplication.run(DynamicTpMain.class,args);
    }


    @Resource
    @Qualifier("bkThreadPool")
    private ThreadPoolExecutor bkThreadPool;

    @GetMapping("/exec")
    public void dynamicExec() {

        ThreadPoolExecutorAdapter myThreadPool = (ThreadPoolExecutorAdapter) DtpRegistry.getExecutor("myThreadPool");
        for (int i = 1; i < 10 ; i++) {
            int param = i;
            try {
                myThreadPool.getOriginal().execute(()->{
                    System.out.println(Thread.currentThread().getName() + "ME执行：" + (param));
                });
            }catch (RejectedExecutionException e) {
                System.out.println("丢弃：" + param);
            }

        }
        for (int i = 1; i < 10 ; i++) {
            int param = i;
            bkThreadPool.execute(()->{
                System.out.println(Thread.currentThread().getName() + "执行：" + (param));
            });
        }
    }
}