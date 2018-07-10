package com.x3platform.tasks.controller.workitem;

import com.x3platform.tasks.model.workitem.TaskWorkitemModel;
import com.x3platform.tasks.service.workitem.TaskWorkitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskWorkitemController {

  @Autowired
  private TaskWorkitemService taskWorkitemService;

  @RequestMapping("/task/selecttask")
  public TaskWorkitemModel selectTaskWorkitem(String id) {
    return taskWorkitemService.selectTask(id);
  }

  @RequestMapping("/task/addtask")
  public void addTask(TaskWorkitemModel taskWorkitemModel) {
    UUID id = UUID.randomUUID();
    taskWorkitemModel.setId(id.toString());
    UUID application_id = UUID.randomUUID();
    taskWorkitemModel.setApplication_id(application_id.toString());
    UUID task_code = UUID.randomUUID();
    taskWorkitemModel.setTask_code(task_code.toString());
    taskWorkitemService.addTask(taskWorkitemModel);
  }

  @RequestMapping("/task/queryalltask")
  public List<TaskWorkitemModel> queryAllTasK(String receiver_id) {
    return taskWorkitemService.queryAllTasK(receiver_id);
  }

  @RequestMapping("/task/counttask")
  public int countTask(String receiver_id) {
    return taskWorkitemService.countTask(receiver_id);
  }

  @RequestMapping("/task/updateread")
  public void UpdateRead(String id) {
    taskWorkitemService.updateRead(id);
  }

  @RequestMapping("/task/updatestatus")
  public void updateStatus(String id) {
    taskWorkitemService.updateStatus(id);
  }

}
