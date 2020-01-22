package com.x3platform.sync.jobs;

import com.x3platform.InternalLogger;
import com.x3platform.sync.SyncContext;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 同步更新包任务
 */
@Component
@DisallowConcurrentExecution
public class CreateAndSendPkgJob extends QuartzJobBean {

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) {
    JobDetail jobDetail = jobExecutionContext.getJobDetail();

    InternalLogger.getLogger().info("Job:{} begin.", jobDetail.getKey());

    // 创建并发送任务
    SyncContext.getInstance().getSyncPkgService().createAndSend();

    InternalLogger.getLogger().info("Job:{} end.", jobDetail.getKey());
  }
}
