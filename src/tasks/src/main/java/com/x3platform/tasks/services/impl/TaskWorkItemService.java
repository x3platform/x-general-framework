package com.x3platform.tasks.services.impl;

import com.x3platform.tasks.mappers.TaskWorkItemMapper;
import com.x3platform.tasks.models.TaskWorkItemInfo;
import com.x3platform.tasks.models.TaskWorkitemModel;
import com.x3platform.tasks.services.ITaskWorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskWorkItemService implements ITaskWorkItemService {

  @Autowired
  private TaskWorkItemMapper provider;

  @Override
  public TaskWorkItemInfo findOne(String taskId, String receiverId) {
    return null;
  }

  @Override
  public TaskWorkItemInfo findOneByTaskCode(String applicationId, String taskCode, String receiverId) {
    return null;
  }

  @Override
  public List<TaskWorkItemInfo> findAllByReceiverId(String receiverId, HashMap<String, Object> params) {
    params.put("receiverId", receiverId);
    return this.provider.findAll(params);
  }

  @Override
  public List<TaskWorkItemInfo> findAllByReceiverId(String receiverId, String whereClause, int length) {
    return null;
  }

  @Override
  public boolean isExist(String taskId, String receiverId) {
    return false;
  }

  @Override
  public int copy(String fromReceiverId, String toReceiverId, LocalDateTime beginDate, LocalDateTime endDate) {
    return 0;
  }

  @Override
  public int cut(String fromReceiverId, String toReceiverId, LocalDateTime beginDate, LocalDateTime endDate) {
    return 0;
  }

  @Override
  public void setRead(String taskId, String receiverId, boolean isRead) {

  }

  @Override
  public void setStatus(String taskId, String receiverId, int status) {

  }

  @Override
  public void setFinished(String receiverId) {

  }

  @Override
  public void setFinished(String receiverId, String taskIds) {

  }

  @Override
  public void setFinishedByTaskCode(String applicationId, String taskCodes, String receiverId) {

  }

  @Override
  public void setUnfinished(String receiverId, String taskIds) {

  }

  @Override
  public void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId) {

  }

  @Override
  public List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId) {
    List<HashMap<Integer, Integer>> result = this.provider.getUnfinishedQuantities(receiverId);
    return result;
  }

  @Override
  public List<TaskWorkItemInfo> getWidgetData(String receiverId, int length) {
    return null;
  }
}

