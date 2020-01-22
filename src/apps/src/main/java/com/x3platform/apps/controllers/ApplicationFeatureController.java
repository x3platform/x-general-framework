package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.services.ApplicationFeatureService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
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
 * 应用功能 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/apps/applicationFeature")
public class ApplicationFeatureController {

  /**
   * 应用功能服务接口
   */
  private ApplicationFeatureService service = AppsContext.getInstance().getApplicationFeatureService();

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
    JSONObject req = JSON.parseObject(data);

    ApplicationFeature entity = JSON.parseObject(data, ApplicationFeature.class);

    String authorizationReadScopeText = req.getString("authorizationReadScopeText");

    service.save(entity);

    service.bindAuthorizationScopeByEntityId(entity.getId(), "应用_通用_查看权限", authorizationReadScopeText);

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
   * @return 一个相关的实例详细信息.
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    ApplicationFeature entity = service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息.
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    List<ApplicationFeature> list = service.findAll(query);

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取当前用户列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息.
   */
  @RequestMapping("/findAllByApplicationName")
  public String findAllByApplicationName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    List<ApplicationFeature> list = null;

    // List<ApplicationFeature> list = service.findAllByApplicationId(query);

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

    List<ApplicationFeature> list = service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param data 请求的数据内容
   * @return 操作结果
   */
  @RequestMapping("/isExist")
  public String isExist(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    boolean result = service.isExist(id);

    return "{\"data\":" + JSON.toJSONString(result) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 根据授权对象绑定菜单
   */
  @RequestMapping("/bindAuthorizationScopeByAuthorizationObjectIds")
  public String bindAuthorizationScopeByAuthorizationObjectIds(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String authorizationObjectType = req.getString("authorizationObjectType");
    String authorizationObjectId = req.getString("authorizationObjectId");
    String authorityName = req.getString("authorityName");
    String entityIds = req.getString("entityIds");

    service.bindAuthorizationScopeByAuthorizationObjectIds(authorizationObjectType, authorizationObjectId,
      authorityName, entityIds);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_commit_success"));
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
    // 父级节点标识
    String parentId = req.getString("parentId");
    // 命令输出格式化
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

  /**
   * 获取当前用户允许的功能列表
   *
   * @param data 请求的数据内容
   * @return 树形结构数据
   */
  @RequestMapping("/getAllowedFeatures")
  public String getAllowedFeatures(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    Account account = KernelContext.getCurrent().getUser();

    List<String> list = service.getAllowedFeatures();

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  // -------------------------------------------------------
  // 应用功能授权
  // -------------------------------------------------------

}
