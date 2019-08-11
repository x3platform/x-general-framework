package com.x3platform.sessions.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.messages.MessageObject;
import com.x3platform.sessions.SessionsContext;
import com.x3platform.sessions.services.TicketService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanyu
 */
@Lazy(true)
@RestController
@RequestMapping("/api/sessions/accountCache")
public class TicketController {

  private TicketService service = SessionsContext.getInstance().getTicketService();

  /**
   * 根据id修改待办信息的阅读状态
   *
   * @param data 请求的数据内容
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

    // service.setRead(taskId, receiverId, isRead);

    return MessageObject.stringify("0", "msg_commit_success");
  }
}
