package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationLite;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.messages.MessageObject;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/apps/application")
public class ApplicationController {

  /**
   * 业务服务接口
   */
  private ApplicationService service = AppsContext.getInstance().getApplicationService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 操作结果
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String administratorScopeText = req.getString("administratorScopeText");

    String reviewerScopeText = req.getString("reviewerScopeText");

    String memberScopeText = req.getString("memberScopeText");

    String originalApplicationName = req.getString("originalApplicationName");

    Application entity = JSON.parseObject(data, Application.class);

    entity.setAccountId(StringUtil.isNullOrEmpty(entity.getAccountId()) ?
      KernelContext.getCurrent().getUser().getId() : entity.getAccountId());

    if (!entity.getApplicationName().equals(originalApplicationName)
      && service.isExistApplicationName(entity.getApplicationName())) {
      return MessageObject.stringify(I18n.getExceptions().text("code_same_field_content_already_exists"),
        I18n.getExceptions().format("text_same_field_content_already_exists", "ApplicationName"));
    }

    service.save(entity);

    service.bindAuthorizationScopeByEntityId(entity.getEntityId(), "应用_默认_管理员", administratorScopeText);

    service.bindAuthorizationScopeByEntityId(entity.getEntityId(), "应用_默认_审查员", reviewerScopeText);

    service.bindAuthorizationScopeByEntityId(entity.getEntityId(), "应用_默认_可访问成员", memberScopeText);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
   * @return 操作结果
   */
  @RequestMapping("/delete")
  public String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    service.delete(id);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 获取详细信息
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   * }
   * </pre>
   * @return 一个相关的实例详细信息.
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    Application entity = service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取详细信息
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 应用名称
   *   applicationName:""
   * }
   * </pre>
   * @return 一个相关的实例详细信息.
   */
  @RequestMapping("/findOneByApplicationName")
  public String findOneByApplicationName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String applicationName = req.getString("applicationName");

    Application entity = service.findOneByApplicationName(applicationName);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
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

    List<ApplicationLite> list = service.findAllLites(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取树节点数据
   *
   * @param data 请求的数据
   * @return 树形结构数据
   */
  @RequestMapping("/getTreeView")
  public String getTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 树的显示名称
    String treeViewName = req.getString("treeViewName");
    // 树的根节点标识
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    // 命令输出格式化
    String commandFormat = req.getString("commandFormat");
    // commandFormat 空值处理
    if (StringUtil.isNullOrEmpty(commandFormat)) {
      commandFormat = "";
    }

    TreeView treeView = service.getTreeView(treeViewName, treeViewRootTreeNodeId, commandFormat);

    return "{\"data\":" + JSON.toJSONString(treeView) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据
   * @return 树形结构数据
   */
  @RequestMapping("/getDynamicTreeView")
  public String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 树的显示名称
    String treeViewName = req.getString("treeViewName");
    // 树的根节点标识
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    // 父级对象标识
    String parentId = req.getString("parentId");
    // 命令格式化
    String commandFormat = req.getString("commandFormat");
    // commandFormat 空值处理
    if (StringUtil.isNullOrEmpty(commandFormat)) {
      commandFormat = "";
    }

    DynamicTreeView treeView = service.getDynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId,
      commandFormat);

    return "{\"data\":" + JSON.toJSONString(treeView) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
