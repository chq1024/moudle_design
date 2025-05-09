package org.beikei.design.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.beikei.design.config.JobHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JobHandler(name = "myD2JobHandler")
@Slf4j
public class Demo2JobHandler extends IJobHandler {

    @Override
    public void execute() throws Exception {
        Thread.sleep(20000);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("Demo2JobHandler:执行任务：{}" , now);
        XxlJobHelper.log("Demo2JobHandler:执行任务：{}" , now);
    }
}
