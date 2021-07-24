package com.x3platform.quartz.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.InternalLogger;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务调度器
 *
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/sys/quartz/scheduler")
public class SchedulerController {

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  @Autowired
  private Scheduler scheduler;

  /**
   * 添加任务
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   jobName:"",
   *   // 分组名称
   *   jobGroup:""
   * }
   * </pre>
   * @return 响应的数据内容
   */
  @RequestMapping("/addJob")
  public String addJob(@RequestBody String data) throws SchedulerException, ClassNotFoundException {
    JSONObject req = JSON.parseObject(data);

    // JobName = "cj"
    // JobGroup = "123";
    // JobClassName = "com.x3platform.customs.sync.master.jobs.UploadTask";
    // CronExpression = "0/8 * * * * ?";
    String jobName = req.getString("jobName");
    String jobGroup = req.getString("jobGroup");
    String jobClassName = req.getString("jobClassName");
    String cronExpression = req.getString("cronExpression");

    if (!scheduler.isStarted()) {
      scheduler.start();
    }

    // 根据 name 和 group 获取当前 trigger 的身份
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

    // 获取触发器的信息
    Trigger trigger = scheduler.getTrigger(triggerKey);

    if (trigger != null) {
      return MessageObject.stringify("1",
        StringUtil.format("job name:{}, job group:{} is exist.", jobName, jobGroup));
    }

    Class jobClass = Class.forName(jobClassName);

    // 将 job 加入到 JobDetail 中
    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
    // 设置 cron 表达式
    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
    // 设置触发器
    trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
      .withSchedule(cronScheduleBuilder)
      .build();

    // 执行任务
    scheduler.scheduleJob(jobDetail, trigger);

    InternalLogger.getLogger().info("add job name:{}, job group:{}, job class name:{}, cron expression:{}.",
      jobName, jobGroup, jobClassName, cronExpression);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 添加记录记录
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   * }
   * </pre>
   * @return 响应的数据内容
   */
  @RequestMapping("/removeJob")
  public String removeJob(@RequestBody String data) throws SchedulerException, ClassNotFoundException {
    JSONObject req = JSON.parseObject(data);

    String jobName = req.getString("jobName");
    String jobGroup = req.getString("jobGroup");

    // 根据 name 和 group 获取当前 trigger 的身份
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

    // 获取触发器的信息
    Trigger trigger = scheduler.getTrigger(triggerKey);

    if (trigger == null) {
      return MessageObject.stringify("1",
        StringUtil.format("job name:{}, job group:{} is null.", jobName, jobGroup));
    }

    // 停止触发器
    scheduler.pauseTrigger(triggerKey);
    // 移除触发器
    scheduler.unscheduleJob(triggerKey);
    // 删除任务
    scheduler.deleteJob(trigger.getJobKey());

    InternalLogger.getLogger().info("remove job name:{}, job group:{}.", jobName, jobGroup);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  /**
   * 暂停任务
   */
  @RequestMapping("/pauseJob")
  public String pauseJob(@RequestBody String data) throws SchedulerException, ClassNotFoundException {
    JSONObject req = JSON.parseObject(data);

    String jobName = req.getString("jobName");
    String jobGroup = req.getString("jobGroup");

    // 根据 name 和 group 获取当前 trigger 的身份
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

    // 获取触发器的信息
    Trigger trigger = scheduler.getTrigger(triggerKey);

    if (trigger == null) {
      return MessageObject.stringify("1",
        StringUtil.format("job name:{}, job group:{} is null.", jobName, jobGroup));
    }

    // 暂停触发器
    scheduler.pauseTrigger(triggerKey);
    // 暂停任务
    scheduler.pauseJob(trigger.getJobKey());

    return MessageObject.stringify("0", I18n.getStrings().text("msg_commit_success"));
  }

  /**
   * 继续执行任务
   */
  @RequestMapping("/resumeJob")
  public String resumeJob(@RequestBody String data) throws SchedulerException {
    JSONObject req = JSON.parseObject(data);

    String jobName = req.getString("jobName");
    String jobGroup = req.getString("jobGroup");

    // 根据 name 和 group 获取当前 trigger 的身份
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

    // 获取触发器的信息
    Trigger trigger = scheduler.getTrigger(triggerKey);

    if (trigger == null) {
      return MessageObject.stringify("1",
        StringUtil.format("job name:{}, job group:{} is null.", jobName, jobGroup));
    }

    // 继续触发器
    scheduler.resumeTrigger(triggerKey);
    // 继续任务
    scheduler.resumeJob(trigger.getJobKey());

    return MessageObject.stringify("0", I18n.getStrings().text("msg_commit_success"));
  }

  /**
   * 获取所有任务信息
   */
  @RequestMapping("/getJobs")
  public String getJobs() throws Exception {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    // 获取有所的组
    List<String> jobGroupNameList = scheduler.getJobGroupNames();

    for (String jobGroupName : jobGroupNameList) {
      GroupMatcher<JobKey> jobKeyGroupMatcher = GroupMatcher.jobGroupEquals(jobGroupName);
      Set<JobKey> jobKeySet = scheduler.getJobKeys(jobKeyGroupMatcher);

      for (JobKey jobKey : jobKeySet) {
        // 获取 job 信息
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        Map<String, String> map = new HashMap<String, String>();

        map.put("jobGroup", jobKey.getGroup());
        map.put("jobName", jobKey.getName());
        map.put("jobDescription", jobDetail.getDescription());
        map.put("jobClassName", jobDetail.getJobClass().getName());

        List<Trigger> triggerList = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

        for (Trigger trigger : triggerList) {
          // 获取 trigger 信息
          map.put("triggerState", scheduler.getTriggerState(trigger.getKey()).toString());
          map.put("triggerDescription", trigger.getDescription());
          map.put("triggerStartTime", formatDateText(trigger.getStartTime()));
          map.put("triggerEndTime", formatDateText(trigger.getEndTime()));
          map.put("triggerNextFireTime", formatDateText(trigger.getNextFireTime()));
          map.put("triggerPreviousFireTime", formatDateText(trigger.getPreviousFireTime()));
          map.put("triggerFinalFireTime", formatDateText(trigger.getFinalFireTime()));

          if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            // String cronExpression = cronTrigger.getCronExpression();
            map.put("cronTriggerCronExpression", cronTrigger.getCronExpression());
          } else if (trigger instanceof SimpleTrigger) {
            SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
            map.put("simpleTriggerRepeatCount", String.valueOf(simpleTrigger.getRepeatCount()));
            map.put("simpleTriggerRepeatInterval", String.valueOf(simpleTrigger.getRepeatInterval()));
            map.put("simpleTriggerTriggeredTimes", String.valueOf(simpleTrigger.getTimesTriggered()));
          }

          list.add(map);
        }
      }
    }

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取任务信息
   */
  @RequestMapping("/getJob")
  public String getJob(@RequestBody String data) throws Exception {
    JSONObject req = JSON.parseObject(data);

    String jobName = req.getString("jobName");
    String jobGroup = req.getString("jobGroup");

    // 根据 name 和 group 获取当前 trigger 的身份
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

    // 获取触发器的信息
    Trigger trigger = scheduler.getTrigger(triggerKey);

    if (trigger == null) {
      return MessageObject.stringify("1",
        StringUtil.format("job name:{}, job group:{} is null.", jobName, jobGroup));
    }

    Map<String, String> map = new HashMap<String, String>();

    JobKey jobKey = trigger.getJobKey();

    // 获取 job 信息
    JobDetail jobDetail = scheduler.getJobDetail(trigger.getJobKey());

    map.put("jobGroup", jobKey.getGroup());
    map.put("jobName", jobKey.getName());
    map.put("jobDescription", jobDetail.getDescription());
    map.put("jobClassName", jobDetail.getJobClass().getName());

    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

    for (Trigger triggerItem : triggers) {
      // 获取 trigger 信息
      map.put("triggerState", scheduler.getTriggerState(trigger.getKey()).toString());
      map.put("triggerDescription", trigger.getDescription());
      map.put("triggerStartTime", formatDateText(triggerItem.getStartTime()));
      map.put("triggerEndTime", formatDateText(triggerItem.getEndTime()));
      map.put("triggerNextFireTime", formatDateText(triggerItem.getNextFireTime()));
      map.put("triggerPreviousFireTime", formatDateText(triggerItem.getPreviousFireTime()));
      map.put("triggerFinalFireTime", formatDateText(triggerItem.getFinalFireTime()));

      if (trigger instanceof CronTrigger) {
        CronTrigger cronTrigger = (CronTrigger) trigger;
        // String cronExpression = cronTrigger.getCronExpression();
        map.put("cronTriggerCronExpression", cronTrigger.getCronExpression());
      } else if (trigger instanceof SimpleTrigger) {
        SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
        map.put("simpleTriggerRepeatCount", String.valueOf(simpleTrigger.getRepeatCount()));
        map.put("simpleTriggerRepeatInterval", String.valueOf(simpleTrigger.getRepeatInterval()));
        map.put("simpleTriggerTriggeredTimes", String.valueOf(simpleTrigger.getTimesTriggered()));
      }
    }

    return "{\"data\":" + JSON.toJSONString(map) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  @RequestMapping("/start")
  public String start() throws SchedulerException {
    if (scheduler.isStarted()) {
      InternalLogger.getLogger().info("Scheduler:{} 当前状态为已启动。", scheduler.getSchedulerName());
    } else {
      scheduler.start();
      InternalLogger.getLogger().info("Scheduler:{} 已启动。", scheduler.getSchedulerName());
    }

    return MessageObject.stringify("0", "scheduler is started.");
  }

  /**
   * 暂停任务调度器
   */
  @RequestMapping("/standby")
  public String standby() throws SchedulerException {
    if (scheduler.isInStandbyMode()) {
      InternalLogger.getLogger().info("Scheduler:{} 当前状态为已暂停。", scheduler.getSchedulerName());
    } else if (scheduler.isStarted()) {
      scheduler.standby();
      InternalLogger.getLogger().info("Scheduler:{} 已暂停。", scheduler.getSchedulerName());
    }

    return MessageObject.stringify("0", "scheduler is standby.");
  }

  /**
   * 关闭任务调度器
   */
  @RequestMapping("/shutdown")
  public String shutdown() throws SchedulerException {
    scheduler.shutdown(true);
    InternalLogger.getLogger().info("Scheduler:{} 已关闭。", scheduler.getSchedulerName());
    return MessageObject.stringify("0", "scheduler is shutdown.");
  }

  /**
   * 查询任务调度器状态
   */
  @RequestMapping("/status")
  public String status() throws SchedulerException {
    String statusText = "default";

    if (scheduler.isStarted()) {
      statusText = "started";
    } else if (scheduler.isInStandbyMode()) {
      statusText = "standby";
    } else if (scheduler.isShutdown()) {
      statusText = "shutdown";
    }

    return MessageObject.stringify("0", "scheduler is " + statusText + ".");
  }

  private String formatDateText(Date date) {
    return date == null ? "none" : StringUtil.toDateFormat(date, "yyyy-MM-dd HH:mm:ss");
  }
}

