package org.beikei.design.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.beikei.design.config.JobHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JobHandler(name = "myD1JobHandler")
@Slf4j
public class Demo1JobHandler extends IJobHandler {

    @Override
    public void execute() throws Exception {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("Demo1JobHandler:执行任务：{}" , now);
        XxlJobHelper.log("Demo1JobHandler:执行任务：{}" , now);
    }
}
