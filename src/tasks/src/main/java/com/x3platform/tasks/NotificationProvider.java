package com.x3platform.tasks;

import com.x3platform.tasks.models.*;

import java.util.List;

/**
 * 通知接口
 */
public interface NotificationProvider {

  /**
   * 发送通知
   *
   * @param workItem    任务信息
   * @param options     选项
   * @return 返回消息代码 0=表示成功
   */
  int send(TaskWorkItem workItem, String options);

  /**
   * 发送通知
   *
   * @param list        多个任务信息
   * @param options     选项
   * @return 返回消息代码 0=表示成功
   */
  int send(List<TaskWorkItem> list, String options);
}
