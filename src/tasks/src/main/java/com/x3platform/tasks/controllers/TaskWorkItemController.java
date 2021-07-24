package com.x3platform.tasks.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.models.TaskWorkItem;
import com.x3platform.tasks.services.TaskWorkItemService;
import com.x3platform.tests.TestConstants;
import com.x3platform.util.StringUtil;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/tasks/workItem")
public class TaskWorkItemController {

  /**
   * 业务服务接口
   */
  private TaskWorkItemService service = TasksContext.getInstance().getTaskWorkItemService();

  /**
   * 发送记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/send")
  public String send(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    TaskWorkItem entity = JSON.parseObject(data, TaskWorkItem.class);

    // 通知选项
    String notificationOptions = req.getString("notificationOptions");

    service.send(entity.getApplicationId(), entity.getTaskCode(), entity.getType(),
      entity.getTitle(), entity.getContent(), entity.getTags(), entity.getSenderId(), entity.getReceiverId(),
      notificationOptions);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 发送记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/sendRange")
  public String sendRange(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    TaskWorkItem entity = JSON.parseObject(data, TaskWorkItem.class);

    // 接收者标识
    String receiverIds = req.getString("receiverIds");
    // 通知选项
    String notificationOptions = req.getString("notificationOptions");

    service.sendRange(entity.getApplicationId(), entity.getTaskCode(), entity.getType(),
      entity.getTitle(), entity.getContent(), entity.getTags(), entity.getSenderId(), receiverIds,
      notificationOptions);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 保存记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    TaskWorkItem entity = JSON.parseObject(data, TaskWorkItem.class);

    service.save(entity);

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

    int result = service.delete(id);

    if (result == 0) {
      return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
    } else {
      String resultCode = String.valueOf(result);
      return MessageObject.stringify(resultCode, I18n.getExceptionDescription(resultCode));
    }
  }

  /**
   * 根据任务标识和接收人标识查询出待办信息的详情
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

    List<TaskWorkItem> list = service.findAllByReceiverId(receiverId);

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

    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
  }

  /**
   * 设置任务工作项已读状态
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setFinished")
  public String setFinished(@RequestBody String data) {

    JSONObject req = JSON.parseObject(data);

    // 接收人唯一标识
    String receiverId = req.getString("receiverId");
    // 任务类型
    String type = req.getString("type");
    // 任务类型
    String taskIds = req.getString("taskIds");

    if (!StringUtil.isNullOrEmpty(receiverId) && !StringUtil.isNullOrEmpty(type)) {
      service.setFinishedByType(receiverId, type);
    } else if (!StringUtil.isNullOrEmpty(receiverId) && !StringUtil.isNullOrEmpty(taskIds)) {
      service.setFinished(receiverId, taskIds);
    } else {
      service.setFinished(receiverId);
    }

    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
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

    service.setStatus(taskId, receiverId, status);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
  }

  /**
   * 测试发送任务
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/testSendWebSocket")
  public String testSendWebSocket(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String taskCode = DigitalNumberContext.generate("Key_Guid");
    String type = req.getString("type");
    String url = req.getString("url");
    String receiverId = req.getString("receiverId");
    String notificationOptions = req.getString("options");

    if (StringUtil.isNullOrEmpty(type)) {
      type = "4";
    }

    if (StringUtil.isNullOrEmpty(url)) {
      url = "http://x3platform.com/app/" + taskCode;
    }

    if (StringUtil.isNullOrEmpty(receiverId)) {
      receiverId = TestConstants.GENERAL_ACCOUNT_ID;
    }

    if (StringUtil.isNullOrEmpty(notificationOptions)) {
      type = "4";
    }

    service.send(TestConstants.APPLICATION_ID, taskCode, type, StringUtil.format("测试待办信息【{}】", taskCode), url,
      "测试", TestConstants.SUPER_ACCOUNT_ID, receiverId, notificationOptions);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_commit_success"));
  }
}
