package com.x3platform.sessions.controllers;

import static com.x3platform.util.StringUtil.toDateFormat;

import com.x3platform.KernelContext;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.sessions.SessionsContext;
import com.x3platform.sessions.Ticket;
import com.x3platform.sessions.services.TicketService;
import com.x3platform.util.StringUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/sessions/ticket")
public class TicketController {

  private TicketService service = SessionsContext.getInstance().getTicketService();

  /**
   * 保持心跳
   */
  @RequestMapping("/heartbeat")
  public int heartbeat(HttpServletRequest req, HttpServletResponse res) {
    return 1;
  }

  /**
   * 获取会话标识
   */
  @RequestMapping("/getSessionId")
  public String getSessionId(HttpServletRequest req, HttpServletResponse res) {
    if (KernelContext.getCurrent().getUser() == null) {
      // return "{\"message\":{\"returnCode\":1,\"value\":\"请登录系统。\"}}";
      return MessageObject.stringify("0", "请登录系统。");
    } else {
      return "{\"data\":{\"sessionId\":\"" + req.getSession().getId()
        + "\"},\"message\":{\"returnCode\":0,\"value\":\"操作成功。\"}}";
    }
  }

  /**
   * 查找数据
   */
  @RequestMapping("/findOne")
  public String findOne(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    String ticketValue = req.getParameter("ticketValue");

    Ticket param = service.findByTicketValue(ticketValue);

    outString.append("{\"data\":");

    if (param != null) {
      outString.append("{");
      outString.append("\"ticketId\":\"" + param.getTicketId() + "\",");
      outString.append("\"ticketValue\":\"" + param.getTicketValue() + "\",");
      outString.append("\"accountObjectType\":\"" + param.getAccountObjectType() + "\",");
      outString
        .append("\"date\":\"" + toDateFormat(param.getCreatedDate(), "yyyy-MM-dd HH:mm:ss.fff") + "\" ");
      outString.append("}");
    }

    outString.append("," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 转储数据
   */
  @RequestMapping("/dump")
  public String dump(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    List<Ticket> list = service.dump();

    outString.append("{\"data\":[");

    for (Ticket item : list) {
      outString.append("{");
      outString.append("\"ticketId\":\"" + item.getTicketId() + "\",");
      outString.append("\"ticketValue\":\"" + item.getTicketValue() + "\",");
      outString.append("\"date\":\"" + toDateFormat(item.getCreatedDate(), "yyyy-MM-dd HH:mm:ss") + "\" ");
      outString.append("},");
    }

    outString = StringUtil.trimEnd(outString, ",");

    outString.append("]," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }
}
