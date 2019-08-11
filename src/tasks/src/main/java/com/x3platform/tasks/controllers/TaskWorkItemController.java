package com.x3platform.tasks.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.configuration.TasksConfiguration;
import com.x3platform.tasks.models.TaskWorkItem;
import com.x3platform.tasks.services.TaskWorkItemService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author ruanyu
 */
@Lazy(true)
@RestController
@RequestMapping("/api/tasks/workItem")
public class TaskWorkItemController {
  /**
   * 业务服务接口
   */
  private TaskWorkItemService service = TasksContext.getInstance().getTaskWorkItemService();

  /**
   * 保存记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    TaskWorkItem entity = JSON.parseObject(data, TaskWorkItem.class);

    this.service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/delete")
  public String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    int result = this.service.delete(id);

    if (result == 0) {
      return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
    } else {
      return MessageObject.stringify(String.valueOf(result), String.valueOf(result));
    }
  }

  /**
   * 根据ID查询出待办信息的详情
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 任务标识
    String id = req.getString("id");
    // 接收人唯一标识
    String receiverId = req.getString("receiverId");

    TaskWorkItem entity = service.findOne(id, receiverId);

    return "{\"data\":" + JSON.toJSONString(entity) + "," +
      MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 根据接收者标识获取相关待办信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("findAllByReceiverId")
  public String findAllByReceiverId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 接收人唯一标识
    String receiverId = req.getString("receiverId");

    List<TaskWorkItem> list = service.findAllByReceiverId(receiverId, new DataQuery());

    return "{\"data\":" + JSON.toJSONString(list) + "," +
      MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 根据接收者标识获取相关任务工作项统计数
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/getUnfinishedQuantities")
  public String getUnfinishedQuantities(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 接收人唯一标识
    String receiverId = req.getString("receiverId");

    List<HashMap<Integer, Integer>> result = service.getUnfinishedQuantities(receiverId);

    return "{\"data\":" + JSON.toJSONString(result) + "," +
      MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 设置任务工作项已读状态
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setRead")
  public String setRead(@RequestBody String data) {

    JSONObject req = JSON.parseObject(data);

    // 任务唯一标识
    String taskId = req.getString("id");
    // 接收人唯一标识
    String receiverId = req.getString("receiverId");
    // 是否已读
    int isRead = req.getInteger("isRead");

    service.setRead(taskId, receiverId, isRead);

    return MessageObject.stringify("0", "msg_commit_success");
  }

  /**
   * 设置任务工作项状态
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setStatus")
  public String setStatus(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 任务唯一标识
    String taskId = req.getString("id");
    // 接收人唯一标识
    String receiverId = req.getString("receiverId");
    // 状态
    int status = req.getInteger("status");

    this.service.setStatus(taskId, receiverId, status);

    return MessageObject.stringify("0", "msg_commit_success");
  }
}
