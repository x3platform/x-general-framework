package com.x3platform.tasks.controllers;

import com.x3platform.tasks.models.TaskWorkitemModel;
import com.x3platform.tasks.services.TaskWorkitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskWorkitemController {

  @Autowired
  private TaskWorkitemService taskWorkitemService;

  /**
   *根据ID查询出待办信息的详情
   */
  @RequestMapping("/task/selecttask")
  public TaskWorkitemModel selectTaskWorkitem(String id) {
    return taskWorkitemService.selectTask(id);
  }

  /**
   *添加待办信息
   * @param taskWorkitemModel
   */
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

  /**
   * 根据receiver_id(接收人id)查出所有待办信息
   * @param receiver_id
   * @return
   */
  @RequestMapping("/task/queryalltask")
  public List<TaskWorkitemModel> queryAllTasK(String receiver_id) {
    return taskWorkitemService.queryAllTasK(receiver_id);
  }

  /**
   * 根据receiver_id(接收人id)统计出该接收人待办信息条数
   * @param receiver_id
   * @return
   */
  @RequestMapping("/task/counttask")
  public int countTask(String receiver_id) {
    return taskWorkitemService.countTask(receiver_id);
  }

  /**
   * 根据id修改待办信息的阅读状态
   * @param id
   */
  @RequestMapping("/task/updateread")
  public void UpdateRead(String id) {
    taskWorkitemService.updateRead(id);
  }

  /**
   * 根据id将待办信息修改为完成
   * @param id
   */
  @RequestMapping("/task/updatestatus")
  public void updateStatus(String id) {
    taskWorkitemService.updateStatus(id);
  }

}
