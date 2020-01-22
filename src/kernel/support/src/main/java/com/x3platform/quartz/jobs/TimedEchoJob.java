package com.x3platform.quartz.jobs;

import com.x3platform.InternalLogger;
import java.time.LocalDateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 定时输出任务
 *
 * @author ruanyu
 */
@Component
@DisallowConcurrentExecution
public class TimedEchoJob extends QuartzJobBean {

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) {
    JobDetail jobDetail = jobExecutionContext.getJobDetail();
    InternalLogger.getLogger().info("job:{} {}.", jobDetail.getKey(), LocalDateTime.now());
    try {
      Thread.sleep(10000);
    } catch (InterruptedException ex) {
      InternalLogger.getLogger().error("", ex);
    }
  }
}
