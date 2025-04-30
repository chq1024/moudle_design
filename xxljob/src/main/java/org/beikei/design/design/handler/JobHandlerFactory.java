package org.beikei.design.design.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JobHandlerFactory {

//    @XxlJob(value = "demoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("DemoJobHandler:执行任务：{}" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        XxlJobHelper.handleSuccess("DemoJobHandler:执行成功!");
        XxlJobHelper.handleFail("DemoJobHandler:执行失败!");
    }
}
