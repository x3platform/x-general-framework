package com.x3platform.tasks.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.configuration.TasksConfiguration;
import com.x3platform.tasks.models.TaskWorkItemInfo;
import com.x3platform.tasks.models.TaskWorkitemModel;
import com.x3platform.tasks.services.ITaskWorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Lazy(true)
@RestController
@RequestMapping("/api/tasks/workItem")
public class TaskWorkItemController {

  private ITaskWorkItemService service = TasksContext.getInstance().getTaskWorkItemService();

  /**
   * 根据ID查询出待办信息的详情
   */
  @RequestMapping("/findOne")
  public TaskWorkItemInfo findOne(String taskId, String receiverId) {
    return service.findOne(taskId, receiverId);
  }

  /**
   * 添加待办信息
   *
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

    // service.(taskWorkitemModel);
  }

  /**
   * 根据receiver_id(接收人id)查出所有待办信息
   *
   * @param receiverId
   * @return
   */
  @RequestMapping("findAllByReceiverId")
  public String findAllByReceiverId(@RequestBody String data) {

    StringBuilder outString = new StringBuilder();

    JSONObject req = JSON.parseObject(data);

    // 接收人唯一标识
    String receiverId = req.getString("receiverId");

    List<TaskWorkItemInfo> list = service.findAllByReceiverId(receiverId, new HashMap<String, Object>());

    outString.append("{\"data\":" + JSON.toJSONString(list) + ",");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 根据receiver_id(接收人id)统计出该接收人待办信息条数
   *
   * @param receiver_id
   * @return
   */
  @RequestMapping("/getUnfinishedQuantities")
  public String getUnfinishedQuantities(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();

    JSONObject req = JSON.parseObject(data);

    // 接收人唯一标识
    String receiverId = req.getString("receiverId");

    List<HashMap<Integer, Integer>> result = service.getUnfinishedQuantities(receiverId);

    outString.append("{\"data\":" + JSON.toJSONString(result) + ",");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 根据id修改待办信息的阅读状态
   *
   * @param id
   */
  @RequestMapping("/setRead")
  public String setRead(@RequestBody String data) {

    JSONObject req = JSON.parseObject(data);

    // 任务唯一标识
    String taskId = req.getString("taskId");
    // 接收人唯一标识
    String receiverId = req.getString("receiverId");
    // 是否已读
    boolean isRead = req.getBoolean("isRead");

    service.setRead(taskId, receiverId, isRead);
    return MessageObject.stringify("0", "msg_commit_success");
  }

  /**
   * 根据id将待办信息修改为完成
   *
   * @param id
   */
  @RequestMapping("/setStatus")
  public String setStatus(String taskId, String receiverId, int status) {
    service.setStatus(taskId, receiverId, status);
    return MessageObject.stringify("0", "msg_commit_success");
  }
}
