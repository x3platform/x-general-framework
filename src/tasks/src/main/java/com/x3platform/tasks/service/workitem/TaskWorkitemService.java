package com.x3platform.tasks.service.workitem;

import com.x3platform.tasks.mapper.workitem.TaskWorkitemMapper;
import com.x3platform.tasks.model.workitem.TaskWorkitemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindTaskWorkitemService {

  @Autowired
  private TaskWorkitemMapper taskWorkitemMapper;

  public TaskWorkitemModel selectTask(String id) {

    return taskWorkitemMapper.selectTask(id);
  }
}

