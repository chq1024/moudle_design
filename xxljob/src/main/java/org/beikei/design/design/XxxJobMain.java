package org.beikei.design.design;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.beikei.design.design.config.JobHandler;
import org.beikei.design.design.util.SpringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Set;


@SpringBootApplication
@Slf4j
public class XxxJobMain implements CommandLineRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(XxxJobMain.class, args);
        SpringUtils.regApplicationContext(applicationContext);
    }

    @Override
    public void run(String... args) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("org.beikei.design.handler",this.getClass().getClassLoader())
                .addScanners(Scanners.TypesAnnotated));

        Set<Class<?>> handleClasses = reflections.getTypesAnnotatedWith(JobHandler.class);
        for (Class<?> clazz : handleClasses) {
            JobHandler annotation = clazz.getAnnotation(JobHandler.class);
            String name = annotation.name();
            XxlJobExecutor.registJobHandler(name,(IJobHandler) clazz.getDeclaredConstructor().newInstance());
        }
    }
}