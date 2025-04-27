package org.beikei.design.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.beikei.design.config.JobHandler;
import org.beikei.design.util.SpringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JobHandler(name = "myDm1JobChildHandler")
@Slf4j
public class Demo1JobChildHandler extends IJobHandler {
    @Override
    public void execute() throws Exception {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("Demo1JobChildHandler:执行任务：{}" , now);
        XxlJobHelper.log("Demo1JobChildHandler:执行任务：{}" , now);
    }
}
