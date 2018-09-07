package com.x3platform.apps.controllers;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.models.ApplicationInfo;
import com.x3platform.apps.models.ApplicationMenuInfo;
import com.x3platform.apps.services.IApplicationService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.util.StringUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x3platform.messages.MessageObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Lazy(true)
@RestController
@RequestMapping("/api/apps/application")
public class ApplicationController {
  // 数据提供器
  private IApplicationService service = AppsContext.getInstance().getApplicationService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public String Save(@RequestBody String data) {
    ApplicationInfo entity = JSON.parseObject(data, ApplicationInfo.class);

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

    ApplicationInfo entity = this.service.findOne(id);

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
  public String Query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    DataPaging paging = DataPagingUtil.Create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ApplicationInfo> list = this.service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /*
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据内容
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

    String url = req.getString("url");

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

    List<ApplicationInfo> list = this.service.findAll(query);

    for (ApplicationInfo item : list) {
      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getParentId().equals(treeViewRootTreeNodeId) ? "0" : item.getParentId()) + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getApplicationDisplayName()) + "\",");
      outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getApplicationDisplayName())) + "\",");
      outString.append("\"target\":\"_self\"");
      outString.append("},");
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }
}
