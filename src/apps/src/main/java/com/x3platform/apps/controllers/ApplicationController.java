package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.models.*;
import com.x3platform.apps.services.*;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.services.AccountService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用管理
 *
 * @modify by xueqian.huang
 * 关于系统所有操作均在此进行操作
 * 主要功能介绍
 */
@Lazy(true)
@RestController
@RequestMapping("/api/apps/application")
public class ApplicationController {
  // @Autowired
  ApplicationOptionService optionService ;
  /**
   * 业务服务接口
   */
  private ApplicationService service = AppsContext.getInstance().getApplicationService();
  private AccountService accountService = MembershipManagement.getInstance().getAccountService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    Application entity = JSON.parseObject(data, Application.class);
    this.service.save(entity);
    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
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
   * @return 返回一个相关的实例详细信息.
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    Application entity = this.service.findOne(id);
    return "{\"data\":" + JSON.toJSONString(entity) + ","
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
    List<Application> list = this.service.findAll(query);
    paging.setTotal(DataPagingUtil.getTotal(list));
    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * @param data 查询当前 是否是 审查员 和  管理员 和 可访问成员
   */
  @RequestMapping("/queryScopeMembers")
  public String queryScopeMembers(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String authType = req.getString("authType");
    String applicationName = req.getString("applicationName");
    String selectKey = req.getString("selectKey");
    // 根据不同的类型 ， 来查询当前所有的数据 ；
    List<Account> accountInfos = accountService.findAllAccountByAccountName(authType, applicationName, selectKey);
    return "{\"data\":" + JSON.toJSONString(accountInfos) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 是否是管理员
   *
   * @param data 请求的内容
   */
  @RequestMapping("/isAdministrator")
  public String isAdministrator(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 应用名称
    String applicationName = req.getString("applicationName");
    // 当前用户信息
    Account account = KernelContext.getCurrent().getUser();

    Boolean result = AppsSecurity.isAdministrator(account,applicationName);

    return "{\"data\":" + JSON.toJSONString(result) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据
   * @return 返回树型结构结果
   */
  @RequestMapping("/getDynamicTreeView")
  public String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    String tree = req.getString("tree");
    String parentId = req.getString("parentId");
    // 附加属性
    String treeViewId = req.getString("treeViewId");
    String treeViewName = req.getString("treeViewName");
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    String commadFormat = req.getString("commadFormat");
    // 树形控件默认根节点标识为0, 需要特殊处理.
    parentId = (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId : parentId;
    StringBuilder outString = new StringBuilder();
    outString.append("{\"data\":");
    outString.append("{\"tree\":\"" + tree + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":[");
    // 查找树的子节点
    DataQuery query = new DataQuery();
    // String whereClause = String.format(" ParentId = ##%1$s## AND Status = 1 ORDER BY OrderId, Code ", parentId);
    query.getWhere().put("parent_id", parentId);
    query.getWhere().put("status", 1);
    query.getOrders().add("order_id");
    query.getOrders().add("code");
    List<Application> list = this.service.findAll(query);
    for (Application item : list) {
      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getParentId().equals(treeViewRootTreeNodeId) ? "0" : item.getParentId()) + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getApplicationDisplayName()) + "\",");
      outString.append("\"commad\":\"" + StringUtil.toSafeJson(commadFormat.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getApplicationDisplayName())) + "\",");
      outString.append("\"target\":\"_self\"");
      outString.append("},");
    }
    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }
    outString.append("]}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    return outString.toString();
  }

  /**
   * {accountId:'',applicationKey:'选填'}
   *
   * @param data
   * @return
   */
  @RequestMapping("/resetPassword")
  public final String resetPassword(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String applicationId = req.getString("applicationKey");
    // 判断是否传入必填， 如果传入必填，则进行根据数据库当前应用设置默认密码， 否则 根据membership.DefaultPassword 设置密码
    String DefaultPassword = "";
    if (StringUtil.isNullOrEmpty(applicationId)) {
      ApplicationOption info = optionService.getApplicationOption(applicationId, "Membership.DefaultPassword");
      if (info == null) {
        Application application = service.findOneByApplicationName("membership");
        info = optionService.getApplicationOption(application.getId(), "Membership.DefaultPassword");
      }
      DefaultPassword = info.getValue();
    } else {
      Application application = service.findOneByApplicationName("membership");
      ApplicationOption info = optionService.getApplicationOption(application.getId(), "Membership.DefaultPassword");
      DefaultPassword = info.getValue();
    }
    try {
      this.accountService.setPassword(accountId, DigestUtils.sha1Hex(DefaultPassword));
    } catch (Exception e) {
      return MessageObject.stringify("1", I18n.getStrings().text("membership_account_password_reset_failed"));
    }
    return MessageObject.stringify("0", I18n.getStrings().text("membership_account_password_reset_success"));
  }
}
