package com.x3platform.tasks;

import com.x3platform.tasks.models.*;

/**
 * 通知接口
 */
public interface INotificationProvider {

  /**
   * 发送通知
   *
   * @param task        任务信息
   * @param receiverIds 接收者
   * @param options     选项
   */
  int send(TaskWorkInfo task, String receiverIds, String options);
}
