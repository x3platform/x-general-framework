package com.x3platform.apps.controllers;

import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.*;
import com.x3platform.apps.services.*;

import com.x3platform.data.*;
import com.x3platform.messages.MessageObject;
import com.x3platform.globalization.I18n;
import com.x3platform.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.apps.controllers.ApplicationEventController")
@RequestMapping("/api/apps/applicationEvent")
public class ApplicationEventController {
  /**
   * 应用服务服务接口
   */
  private ApplicationEventService service = AppsContext.getInstance().getApplicationEventService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    ApplicationEvent entity = JSON.parseObject(data, ApplicationEvent.class);

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

    this.service.delete(id);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 获取详细信息
   *
   * @param data 请求的数据内容
   *             <pre>
   *                                                                                                                         {
   *                                                                                                                           // 唯一标识
   *                                                                                                                           id:""
   *                                                                                                                         }
   *                                                                                                                         </pre>
   * @return 返回一个相关的实例详细信息
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    ApplicationEvent entity = this.service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 返回一个相关的实例列表信息
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    /// 根据实际需要设置当前用户权限
    // query.getVariables().put("accountId", KernelContext.getCurrent().getUser().getId())

    // if (req.getString("su") == "1" && AppsSecurity.IsAdministrator(KernelContext.getCurrent().getUser(), appsConfiguration.ApplicationName))
    // {
    //   query.getVariables().put("elevatedPrivileges", "1");
    // }

    /// 根据实际需要设置查询参数
    // query.getWhere().put("Name", searchText);
    // query.setLength(length);

    List<ApplicationEvent> list = this.service.findAll(query);

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 获取分页内容
   *
   * @param data 请求的数据内容
   * @return 返回一个相关的实例列表信息
   */
  @RequestMapping("/query")
  public String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ApplicationEvent> list = this.service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 写入应用事件信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/write")
  public String write(@RequestBody String data, HttpServletRequest request) {
    try {
      JSONObject req = JSON.parseObject(data);
      String accountId = req.getString("accountId");
      String accountName = req.getString("accountName");
      String applicationId = req.getString("applicationId");
      String applicationFeatureName = req.getString("applicationFeatureName");
      String level = req.getString("level");
      String description = req.getString("description");
      String startTime = req.getString("startTime");
      String finishTime = req.getString("finishTime");
      String ip = req.getString("ip");
      LocalDateTime startTimeValue = null;
      LocalDateTime finishTimeValue = null;
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
      DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

      if (StringUtil.isNullOrEmpty(ip)) {
        ip = IPQueryUtil.getClientIP();
      }
      if (StringUtil.isNullOrEmpty(startTime)) {
        startTime =  LocalDateTime.now().toString();
      }
      startTimeValue = LocalDateTime.parse(startTime, startTime.length() == 19 ? df : df2);
      if (StringUtil.isNullOrEmpty(finishTime)) {
        finishTime =  LocalDateTime.now().toString();
      }
      finishTimeValue = LocalDateTime.parse(finishTime, finishTime.length() == 19 ? df : df2);
      this.service.write(accountId, accountName, applicationId, applicationFeatureName,
        level, description, startTimeValue, finishTimeValue, ip);
      return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
    }catch (Exception e){
      e.printStackTrace();
      return MessageObject.stringify("0", I18n.getStrings().text("msg_save_failed"));
    }
  }
}
