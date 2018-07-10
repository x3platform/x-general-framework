package com.x3platform.tasks.service.workitem;

import com.x3platform.tasks.mapper.workitem.TaskWorkitemMapper;
import com.x3platform.tasks.model.workitem.TaskWorkitemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskWorkitemService {

  @Autowired
  private TaskWorkitemMapper taskWorkitemMapper;

  public TaskWorkitemModel selectTask(String id) {

    return taskWorkitemMapper.selectTask(id);
  }

  public void addTask(TaskWorkitemModel taskWorkitemModel) {
    taskWorkitemMapper.addTask(taskWorkitemModel);
  }

  public List<TaskWorkitemModel> queryAllTasK(String receiver_id) {
    return taskWorkitemMapper.queryAllTasK(receiver_id);
  }

  public int countTask(String receiver_id) {
    return taskWorkitemMapper.countTask(receiver_id);
  }

  public void updateRead(String id) {
    taskWorkitemMapper.updateRead(id);
  }

  public void updateStatus(String id){
    taskWorkitemMapper.updateStatus(id);
  }


}

