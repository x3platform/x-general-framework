package com.x3platform.apps.controllers;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.models.ApplicationInfo;
import com.x3platform.apps.models.ApplicationMenuInfo;
import com.x3platform.apps.services.IApplicationMenuService;
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
import javax.xml.crypto.Data;

/**
 *
 */
@Lazy(true)
@RestController
@RequestMapping("/api/apps/applicationMenu")
public class ApplicationMenuController {
  // 业务逻辑服务接口
  private IApplicationMenuService service = AppsContext.getInstance().getApplicationMenuService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public final String Save(@RequestBody String data) {
    ApplicationMenuInfo entity = JSON.parseObject(data, ApplicationMenuInfo.class);

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
  public final String delete(@RequestBody String data) {
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
  public final String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    ApplicationMenuInfo entity = this.service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 返回一个相关的实例列表信息.
   */
  @RequestMapping("/findAll")
  public final String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    List<ApplicationMenuInfo> list = this.service.findAll(query);

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
  public final String Query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.Create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ApplicationMenuInfo> list = this.service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  @RequestMapping("/isExist")
  public final String isExist(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    boolean result = this.service.isExist(id);

    return "{\"data\":" + JSON.toJSONString(result) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据内容
   * @return 返回树型结构结果
   */
  @RequestMapping("/getDynamicTreeView")
  public final String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    String tree = req.getString("tree");
    String parentId = req.getString("parentId");

    // 附加属性
    String treeViewId = req.getString("treeViewId");
    String treeViewName = req.getString("treeViewName");
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    // string applicationId#fff#menuId#000000000000000;

    String url = req.getString("url");

    // 树形控件默认根节点标识为0, 需要特殊处理.
    parentId = (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId : parentId;

    StringBuilder outString = new StringBuilder();

    outString.append("{\"data\":");
    outString.append("{\"tree\":\"" + tree + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":[");

    // 特殊类型
    if ("menu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000".equals(parentId)) {
      if (AppsConfigurationView.getInstance().getHiddenStartMenu()) {
        outString.append("{");
        outString.append("\"id\":\"startMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000\",");
        outString.append("\"parentId\":\"0\",");
        outString.append("\"name\":\"开始菜单\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", "startMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000").replace("{treeNodeName}", "开始菜单")) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }

      if (AppsConfigurationView.getInstance().getHiddenTopMenu()) {
        outString.append("{");
        outString.append("\"id\":\"topMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000\",");
        outString.append("\"parentId\":\"0\",");
        outString.append("\"name\":\"顶部菜单\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", "topMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000").replace("{treeNodeName}", "顶部菜单")) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }

      if (AppsConfigurationView.getInstance().getHiddenShortcutMenu()) {
        outString.append("{");
        outString.append("\"id\":\"shortcutMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000\",");
        outString.append("\"parentId\":\"0\",");
        outString.append("\"name\":\"快捷菜单\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", "shortcutMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000").replace("{treeNodeName}", "快捷菜单")) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }
    }

    DataQuery query = new DataQuery();

    String[] keys = parentId.split("#");

    keys[0] = (keys[0].equals("menu")) ? "ApplicationMenu" : StringUtil.toFirstUpper(keys[0]);

    //
    // ApplicationMenu
    //
    if (keys[0].equals("ApplicationMenu") && keys[4].equals("00000000-0000-0000-0000-000000000000")) {
      // 应用系统
      // whereClause = String.format(" ParentId = ##%1$s## AND Status = 1 ORDER BY OrderId, Code ", keys[2]);
      query.getWhere().put("parent_id", keys[2]);
      query.getWhere().put("status", 1);
      query.getOrders().add("order_id");
      query.getOrders().add("code");

      // List<ApplicationInfo> list = AppsContext.getInstance().getApplicationService().findAll(whereClause);
      List<ApplicationInfo> list = AppsContext.getInstance().getApplicationService().findAll(query);

      for (ApplicationInfo item : list) {
        outString.append("{");
        outString.append("\"id\":\"" + String.format("applicationMenu#applicationId#%1$s#menuId#00000000-0000-0000-0000-000000000000", item.getId()) + "\",");
        outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getParentId().equals("00000000-0000-0000-0000-000000000001") ? "0" : String.format("applicationMenu#applicationId#%1$s#menuId#00000000-0000-0000-0000-000000000000", item.getParentId())) + "\",");
        outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getApplicationDisplayName()) + "\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", String.format("applicationMenu#applicationId#%1$s#menuId#00000000-0000-0000-0000-000000000000", item.getId())).replace("{treeNodeName}", item.getApplicationDisplayName())) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }
    }

    if (!parentId.equals("menu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000")) {
      // 菜单项
      if (keys[4].equals("00000000-0000-0000-0000-000000000000")) {
        // whereClause = String.format(" MenuType = ##%1$s## AND ApplicationId = ##%2$s## AND ParentId = ##%3$s## AND Status = 1 ORDER BY OrderId ", keys[0], keys[2], keys[4]);
        query.getWhere().put("menu_type", keys[0]);
        query.getWhere().put("application_id", keys[2]);
        query.getWhere().put("parent_id", keys[4]);
        query.getWhere().put("status", 1);
        query.getOrders().add("order_id");
      } else {
        // whereClause = String.format(" MenuType = ##%1$s## AND ParentId = ##%2$s## AND Status = 1 ORDER BY OrderId ", keys[0], keys[4]);
        query.getWhere().put("menu_type", keys[0]);
        query.getWhere().put("parent_id", keys[4]);
        query.getWhere().put("status", 1);
        query.getOrders().add("order_id");
      }


      // List<ApplicationMenuInfo> list = AppsContext.getInstance().getApplicationMenuService().findAll(whereClause);
      List<ApplicationMenuInfo> list = AppsContext.getInstance().getApplicationMenuService().findAll(query);

      for (ApplicationMenuInfo item : list) {
        // AppsContext.getInstance().getApplicationMenuService().findAll(whereClause).ToList().ForEach(item ->
        // {
        if (item.getDisplayType().equals("MenuSplitLine")) {
          // 分割线不显示。
        } else {
          outString.append("{");
          outString.append("\"id\":\"" + String.format("%1$s#applicationId#%2$s#menuId#%3$s", StringUtil.toFirstLower(item.getMenuType()), item.getApplicationId(), item.getId()) + "\",");
          outString.append("\"parentId\":\"" + StringUtil.toSafeJson((item.getMenuType().equals("ApplicationMenu") && item.getApplicationId().equals("00000000-0000-0000-0000-000000000001") && item.getParentId().equals("00000000-0000-0000-0000-000000000000")) ? "0" : String.format("%1$s#applicationId#%2$s#menuId#%3$s", StringUtil.toFirstLower(item.getMenuType()), item.getApplicationId(), item.getParentId())) + "\",");
          outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
          outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", String.format("%1$s#applicationId#%2$s#menuId#%3$s", StringUtil.toFirstLower(item.getMenuType()), item.getApplicationId(), item.getId())).replace("{treeNodeName}", item.getName())) + "\",");
          outString.append("\"target\":\"_self\"");
          outString.append("},");
        }
      }
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }
}
