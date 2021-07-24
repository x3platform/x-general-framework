package com.x3platform.membership.controllers;

import com.x3platform.membership.*;
import com.x3platform.membership.services.*;

import com.x3platform.data.*;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.messages.MessageObject;
import com.x3platform.globalization.I18n;
import com.x3platform.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.membership.controllers.UserController")
@RequestMapping("/api/membership/user")
public class UserController {
  
  /**
   * 业务服务接口
   */
  private UserService service = MembershipManagement.getInstance().getUserService();
  
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
    User entity = JSON.parseObject(data, User.class);
    
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
   * @return 一个相关的实例详细信息
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    
    String id = req.getString("id");
    
    User entity = this.service.findOne(id);
    
    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  /**
   * 根据帐号标识获取用户详细信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例详细信息
   */
  @RequestMapping("/findOneByAccountId")
  public String findOneByAccountId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    
    String accountId = req.getString("accountId");
    
    User entity = this.service.findOneByAccountId(accountId);
    
    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  /**
   * 根据帐号登录名获取用户详细信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例详细信息
   */
  @RequestMapping("/findOneByLoginName")
  public String findOneByLoginName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    
    String loginName = req.getString("loginName");
    
    User entity = this.service.findOneByLoginName(loginName);
    
    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    
    DataQuery query = DataQuery.create(req.getString("query"));
    
    /// 根据实际需要设置当前用户权限
    // query.getVariables().put("accountId", KernelContext.getCurrent().getUser().getId())
    
    // if (req.getString("su") == "1" && AppsSecurity.IsAdministrator(KernelContext.getCurrent().getUser(), membershipConfiguration.ApplicationName))
    // {
    //   query.getVariables().put("elevatedPrivileges", "1");
    // }
    
    /// 根据实际需要设置查询参数
    // query.getWhere().put("Name", searchText);
    // query.setLength(length);
    
    List<User> list = this.service.findAll(query);
    
    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------
  
  /**
   * 获取带分页的列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/query")
  public String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    
    DataPaging paging = DataPagingUtil.create(req.getString("paging"));
    
    DataQuery query = DataQuery.create(req.getString("query"));
    
    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());
    
    List<User> list = this.service.findAll(query);
    
    paging.setTotal(DataPagingUtil.getTotal(list));
    
    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  /**
   * 设置默认角色
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setDefaultRole")
  public String setDefaultRole(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    
    String accountId = req.getString("accountId");
    String roleId = req.getString("roleId");
    
    MembershipManagement.getInstance().getUserService().setDefaultRole(accountId, roleId);
    
    return MessageObject.stringify("0", I18n.getStrings().text("msg_commit_success"));
  }
}
