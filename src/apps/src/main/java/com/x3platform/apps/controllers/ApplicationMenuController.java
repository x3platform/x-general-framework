package com.x3platform.apps.controllers;

import static com.x3platform.apps.Constants.APPLICATION_MENU_ROOT_ID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import com.x3platform.apps.services.ApplicationMenuService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.Role;
import com.x3platform.messages.MessageObject;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用菜单 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.apps.controllers.ApplicationMenuController")
@RequestMapping("/api/apps/applicationMenu")
public class ApplicationMenuController {

  /**
   * 业务逻辑服务接口
   */
  private ApplicationMenuService service = AppsContext.getInstance().getApplicationMenuService();

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
  public String save(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    ApplicationMenu entity = JSON.parseObject(data, ApplicationMenu.class);

    String authorizationReadScopeText = req.getString("authorizationReadScopeText");

    service.save(entity);

    service.bindAuthorizationScopeByEntityId(entity.getEntityId(), "应用_通用_查看权限", authorizationReadScopeText);

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
   * @return 返回一个相关的实例详细信息.
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    ApplicationMenu entity = service.findOne(id);

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
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    List<ApplicationMenu> list = service.findAll(query);

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

    List<ApplicationMenuLite> list = service.findAllLites(query);

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
  public String isExist(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    boolean result = service.isExist(id);

    return "{\"data\":" + JSON.toJSONString(result) + ","
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

    // TreeView treeView = service.getTreeView(treeViewName, treeViewRootTreeNodeId, commandFormat);
    TreeView treeView = null;

    return "{\"data\":" + JSON.toJSONString(treeView) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据内容
   * @return 返回树形结构数据
   */
  @RequestMapping("/getDynamicTreeView")
  public String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    String treeViewName = req.getString("treeViewName");
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    String parentId = req.getString("parentId");
    // 附加属性
    String treeViewId = req.getString("treeViewId");
    // string applicationId#fff#menuId#000000000000000;
    String commandFormat = req.getString("commandFormat");
    // 树形控件默认根节点标识为0, 需要特殊处理.
    parentId = (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId
      : parentId;

    // commandFormat 空值处理
    if (StringUtil.isNullOrEmpty(commandFormat)) {
      commandFormat = "";
    }
    StringBuilder outString = new StringBuilder();
    outString.append("{\"data\":");
    outString.append("{\"tree\":\"" + treeViewName + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":[");

    // 特殊类型
    if ("menu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000"
      .equals(parentId)) {

      if (!AppsConfigurationView.getInstance().getHiddenStartMenu()) {
        // 获取
        outString.append("{");
        outString.append(
          "\"id\":\"startMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000\",");
        outString.append("\"parentId\":\"0\",");
        outString.append("\"name\":\"开始菜单\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(commandFormat.replace("{treeNodeId}",
          "startMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000")
          .replace("{treeNodeName}", "开始菜单")) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }

      if (!AppsConfigurationView.getInstance().getHiddenTopMenu()) {
        // 获取顶部菜单
        outString.append("{");
        outString.append(
          "\"id\":\"topMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000\",");
        outString.append("\"parentId\":\"0\",");
        outString.append("\"name\":\"顶部菜单\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(commandFormat.replace("{treeNodeId}",
          "topMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000")
          .replace("{treeNodeName}", "顶部菜单")) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }

      if (!AppsConfigurationView.getInstance().getHiddenShortcutMenu()) {
        // 获取快捷菜单
        outString.append("{");
        outString.append(
          "\"id\":\"shortcutMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000\",");
        outString.append("\"parentId\":\"0\",");
        outString.append("\"name\":\"快捷菜单\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(commandFormat.replace("{treeNodeId}",
          "shortcutMenu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000")
          .replace("{treeNodeName}", "快捷菜单")) + "\",");
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
      // 查询顶层应用系统
      query.getWhere().put("parent_id", keys[2]);
      query.getWhere().put("status", 1);
      query.getOrders().add("order_id");
      query.getOrders().add("code");

      List<Application> list = AppsContext.getInstance().getApplicationService().findAll(query);

      for (Application item : list) {
        outString.append("{");
        outString.append("\"id\":\"" + String
          .format("applicationMenu#applicationId#%1$s#menuId#00000000-0000-0000-0000-000000000000",
            item.getId()) + "\",");
        outString.append("\"parentId\":\"" + StringUtil.toSafeJson(
          item.getParentId().equals("00000000-0000-0000-0000-000000000001") ? "0" : String.format(
            "applicationMenu#applicationId#%1$s#menuId#00000000-0000-0000-0000-000000000000",
            item.getParentId())) + "\",");
        outString
          .append("\"name\":\"" + StringUtil.toSafeJson(item.getApplicationDisplayName()) + "\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(commandFormat.replace("{treeNodeId}", String
          .format("applicationMenu#applicationId#%1$s#menuId#00000000-0000-0000-0000-000000000000",
            item.getId())).replace("{treeNodeName}", item.getApplicationDisplayName())) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("},");
      }
    }
    if (!parentId.equals(
      "menu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000")) {
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

      // List<ApplicationMenu> list = AppsContext.getInstance().getApplicationMenuService().findAll(whereClause);
      List<ApplicationMenu> list = AppsContext.getInstance().getApplicationMenuService()
        .findAll(query);
      for (ApplicationMenu item : list) {
        // AppsContext.getInstance().getApplicationMenuService().findAll(whereClause).ToList().ForEach(item ->
        // {
        if (item.getDisplayType().equals("MenuSplitLine")) {
          // 分割线不显示。
        } else {
          outString.append("{");
          outString.append("\"id\":\"" + String.format("%1$s#applicationId#%2$s#menuId#%3$s",
            StringUtil.toFirstLower(item.getMenuType()), item.getApplicationId(), item.getId())
            + "\",");
          outString.append("\"parentId\":\"" + StringUtil.toSafeJson(
            (item.getMenuType().equals("ApplicationMenu") && item.getApplicationId()
              .equals("00000000-0000-0000-0000-000000000001") && item.getParentId()
              .equals("00000000-0000-0000-0000-000000000000")) ? "0" : String
              .format("%1$s#applicationId#%2$s#menuId#%3$s",
                StringUtil.toFirstLower(item.getMenuType()), item.getApplicationId(),
                item.getParentId())) + "\",");
          outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
          outString.append("\"commandFormat\":\"" + StringUtil.toSafeJson(commandFormat.replace("{treeNodeId}", String
            .format("%1$s#applicationId#%2$s#menuId#%3$s",
              StringUtil.toFirstLower(item.getMenuType()), item.getApplicationId(), item.getId()))
            .replace("{treeNodeName}", item.getName())) + "\",");
          outString.append("\"target\":\"_self\"");
          outString.append("},");
        }
      }
    }
    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append(
      "]}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true)
        + "}");

    return outString.toString();
  }

  /**
   * 获取菜单
   *
   * @param data 请求的数据内容
   * @return 返回树形结构数据
   */
  @RequestMapping("/getMenus")
  public String getMenus(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    String applicationName = req.getString("applicationName");
    String menuType = req.getString("menuType");
    String parentId = req.getString("parentId");
    String parentFullPath = req.getString("parentFullPath");
    String currentId = req.getString("currentId");
    String currentFullPath = req.getString("currentFullPath");

    Application application = AppsContext.getInstance().getApplicationService()
      .findOneByApplicationName(applicationName);

    StringBuilder outString = new StringBuilder();

    // 根节点的标识为 00000000-0000-0000-0000-000000000000
    if (StringUtil.isNullOrEmpty(parentId) || "0".equals(parentId)) {
      parentId = APPLICATION_MENU_ROOT_ID;
    }

    outString.append("{\"data\":{");
    outString.append("\"applicationId\":\"" + application.getId() + "\",");
    outString.append("\"applicationName\":\"" + applicationName + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":"
      + getMenuChildNodes(application.getId(), parentId, menuType));
    outString.append("},"
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 获取顶部菜单信息
   *
   * @param data 请求的数据内容
   * @return 返回树形结构数据
   */
  @RequestMapping("/getTopMenus")
  public String getTopMenus(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();

    JSONObject req = JSON.parseObject(data);
    // 应用名称
    String applicationName = req.getString("applicationName");
    // 菜单类型
    String menuType = req.getString("menuType");
    //
    String type = req.getString("type");
    // 父级菜单标识
    String parentId = req.getString("parentId");
    String accountId = KernelContext.getCurrent().getUser().getId();
    Application application = AppsContext.getInstance().getApplicationService()
      .findOneByApplicationName(applicationName);

    //  获取等级菜单 分为2中情况 ，
    // 一种是 菜单 TopMenu ，
    // 一种是应用 Application
    if ("TopMenu".equals(menuType)) {
      // 当为菜单的时候 , parentId="0"; 判断 读取 应用 还是 Top ， 获取读取 应用
      if ("Application".equals(type)) {
        List<Application> apps = AppsContext.getInstance().getApplicationService().findAllByAccountId(accountId);
        outString.append("{\"data\":{");
        outString.append("\"applicationId\":\"" + application.getId() + "\",");
        outString.append("\"applicationName\":\"" + applicationName + "\",");
        outString.append("\"parentId\":\"" + parentId + "\",");
        outString.append("\"childNodes\":[");
        if (apps != null && apps.size() > 0) {
          for (Application item : apps) {
            outString.append("{");
            outString.append("\"id\":\"" + item.getId() + "\",");
            outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getParentId()) + "\",");
            outString.append(
              "\"name\":\"" + StringUtil.toSafeJson(item.getApplicationDisplayName()) + "\",");
            outString.append("\"iconPath\":\"" + StringUtil.toSafeJson(item.getIconPath()) + "\",");
            outString.append("\"url\":\"" + StringUtil.toSafeJson(item.getUrl()) + "\",");
            outString.append("\"target\":\"_self\",");
            outString.append("\"childNodes\":[]");
            outString.append("},");
          }
          if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
            outString = outString.deleteCharAt(outString.length() - 1);
          }
        }

        outString.append("]");
        outString.append(
          "}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true)
            + "}");
        // 查询应用 ， 如果不是应用 则是菜单 ， 菜单则查询顶级菜单 ；
      } else if ("TopMenu".equals(type)) {

      }

    } else {
      // 获取对应平台应用
     /* outString.append("{\"data\":{");
      outString.append("\"applicationId\":\"" + application.getId() + "\",");
      outString.append("\"applicationName\":\"" + applicationName + "\",");
      outString.append("\"parentId\":\"" + parentId + "\",");
      outString.append("\"childNodes\":" + getMenuChildNodes(application.getId(), parentId, menuType));
      outString.append("}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");*/
    }

    return outString.toString();
  }


  /**
   * 获取Scope，使用角色和 EveryOne
   */
//  private String getMenuScopeChildNodes(String applicationId, String parentId, String menuType,
//    String accountId) {
//    /**
//     * 判断是否是管理员角色权限 ， 如果是普通角色，则需要进行判断
//     */
//    Account accountInfo = KernelContext.getCurrent().getUser();  // 查询接管的当前用户
//    List<ApplicationMenu> list = new ArrayList<>();
//    // 内置超级管理员帐号 , 配置管理员账号 ， 配置审查员账号 ， 配置可访问成员账号
//    boolean isAdministrator = AppsContext.getInstance().getApplicationService()
//      .isAdministrator(accountInfo, applicationId); // 内置超级管理员账号
//    // 判断当前是否是应用管理员 配置，角色
//
//    if (isAdministrator) {
//      //   判断是否是应用管理员   ，   审查员
//      //   判断是否是应用管理员中的 管理员角色
//      list = AppsContext.getInstance().getApplicationMenuService()
//        .getMenusByParentId(applicationId, parentId, menuType);
//    } else {
//      /**
//       *  判断是否是审查员  然后判断是否有可访问权限
//       */
//      // 可访问成员
//      boolean isMember = AppsContext.getInstance().getApplicationService().isMember(accountInfo, applicationId);
//      // 可访问成员
//      if (isMember) {
//        list = service.getMenusScopeByParentId(applicationId, parentId, menuType, accountId);
//      }
//    }
//    StringBuilder outString = new StringBuilder();
//    outString.append("[");
//    if (list != null && list.size() > 0) {
//      for (ApplicationMenu item : list) {
//        outString.append("{");
//        outString.append("\"id\":\"" + item.getId() + "\",");
//        outString.append("\"parentId\":\"" + StringUtil.toSafeJson(
//          (item.getMenuType().equals("ApplicationMenu") && item.getApplicationId()
//            .equals("00000000-0000-0000-0000-000000000001") && item.getParentId()
//            .equals("00000000-0000-0000-0000-000000000000")) ? "0" : item.getParentId()) + "\",");
//        outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
//        outString.append("\"iconPath\":\"" + StringUtil.toSafeJson(item.getIconPath()) + "\",");
//        outString.append("\"url\":\"" + StringUtil.toSafeJson(item.getUrl()) + "\",");
//        outString.append("\"target\":\"" + item.getTarget() + "\",");
//        outString.append(
//          "\"childNodes\":" + getMenuScopeChildNodes(item.getApplicationId(), item.getId(),
//            item.getMenuType(), accountInfo.getId()));
//        outString.append("},");
//      }
//      if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
//        outString = outString.deleteCharAt(outString.length() - 1);
//      }
//    }
//    outString.append("]");
//    return outString.toString();
//  }
  private String getMenuChildNodes(String applicationId, String parentId, String menuType) {
    StringBuilder outString = new StringBuilder();

    List<ApplicationMenu> list = AppsContext.getInstance().getApplicationMenuService()
      .getMenusByParentId(applicationId, parentId, menuType);

    outString.append("[");

    for (ApplicationMenu item : list) {
      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"parentId\":\"" + StringUtil.toSafeJson(
        (item.getMenuType().equals("ApplicationMenu") && item.getApplicationId()
          .equals("00000000-0000-0000-0000-000000000001") && item.getParentId()
          .equals("00000000-0000-0000-0000-000000000000")) ? "0" : item.getParentId()) + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"iconPath\":\"" + StringUtil.toSafeJson(item.getIconPath()) + "\",");
      outString.append("\"url\":\"" + StringUtil.toSafeJson(item.getUrl()) + "\",");
      outString.append("\"target\":\"" + item.getTarget() + "\",");
      outString.append("\"childNodes\":" + getMenuChildNodes(item.getApplicationId(), item.getId(),
        item.getMenuType()));
      outString.append("},");
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]");

    return outString.toString();
  }

  // "{"path":"/search/subdivide","name":"search_subdivide","icon":"database","locale":"menu.search_subdivide","children":
  //  [{"path":"/search/customs_policy","name":"search_customs_policy2","icon":"database","
  //  exact":true,"locale":"menu.search_subdivide.search_customs_policy2"},
  //  {"locale":"menu.search_subdivide"}]}

//  @RequestMapping("/getMenuAllBytUser")
//  public String getMenuAllBytUser(@RequestBody String data) {
//    JSONObject req = JSON.parseObject(data);
//    StringBuilder os = new StringBuilder();
//    Account accountInfo = KernelContext.getCurrent().getUser(); // 查询接管的当前用户
//    String accountId = accountInfo.getId();
//    // 当前用户， 和传递的 applicationKey  获得对应的菜单
//    // 必填字段
//    // String token = req.getString("token");
//    String applicationName = req.getString("applicationName");
//    String menuType = req.getString("menuType");
//    if (StringUtil.isNullOrEmpty(menuType)) {
//      menuType = "ApplicationMenu";
//    }
//    String parentId = req.getString("parentId");
//    DataQuery query = new DataQuery();
//    // 当前应用
//    Application application = AppsContext.getInstance().getApplicationService()
//      .findOneByApplicationName(applicationName);
//
//    /**
//     * 查询当前应用 ， 查看是否存在应用，存在应用 则查询是否有相关权限
//     */
//    if (application != null) {
//      /**
//       * 当前用户有可访问权限，则进行查询对应菜单，角色权限 ；
//       */
//      if (AppsContext.getInstance().getApplicationService().isMember(accountInfo, application.getId())) {
//        if (StringUtil.isNullOrEmpty(parentId) || parentId.equals(application.getId())) {
//          parentId = "00000000-0000-0000-0000-000000000000";
//        }
//        os.append("{\"data\":{");
//        os.append("\"applicationId\":\"" + application.getId() + "\",");
//        os.append("\"applicationName\":\"" + applicationName + "\",");
//        os.append("\"parentId\":\"" + parentId + "\",");
//        os.append(
//          "\"childNodes\":" + getMenuScopeChildNodes(application.getId(), parentId, menuType,
//            accountId));
//        os.append(
//          "}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true)
//            + "}");
//      }
//    } else {
//      // 返回错误信息 ， 或者 返回相关信息 ；
//
//    }
//    return os.toString();
//  }

  @RequestMapping("/getMenuAllByApplicationKey")
  public String getMenuAllByApplicationKey(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String applicationKey = req.getString("AppKey");
    Application Application = AppsContext.getInstance().getApplicationService().findOneByApplicationKey(applicationKey);
    List<ApplicationMenu> ApplicationMenuList = service
      .getMenusByApplicationId(Application.getId());
    // 当前用户， 和传递的 applicationKey  获得对应的菜单
    return "{\"data\":" + JSON.toJSONString(ApplicationMenuList) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询当前角色，对应已经分配了的角色 ；
   */
//  @RequestMapping("/getExistApplicationMenuByRole")
//  public String getExistApplicationMenuByRole(@RequestBody String data) {
//    JSONObject req = JSON.parseObject(data);
//    String id = req.getString("id"); // 角色id
//    List<String> roles = service.getMenusScopeByRoleId(id);
//    return "{\"data\":" + JSON.toJSONString(roles) + ","
//      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
//  }


  /**
   * 根据 菜单 或者 组织 获得 该菜单所对应的所有人 或者 角色 ， 请注意 ，逻辑操作，注意判断只配角色 或者 只配人员情况 或者 人员、角色一起
   *
   * @param data 参数情况
   */
  @RequestMapping("/getUserOrRoleByMenuApplication")
  public String getUserOrRoleByMenuApplication(@RequestBody String data) {
    Map<String, Object> params = new HashMap<>();
    JSONObject req = JSON.parseObject(data);
    String menuId = req.getString("menuId");
    String organizationUnitId = req.getString("organizationUnitId");
    List<Account> accountInfoList = MembershipManagement.getInstance().getAccountService()
      .findAllAccountsByOrganization(organizationUnitId);
    List<Role> roleInfoList = MembershipManagement.getInstance().getRoleService()
      .findAllByOrganizationUnitId(organizationUnitId);
    params.put("accountInfoList", accountInfoList);
    params.put("roleInfoList", roleInfoList);
    // 当前用户， 和传递的 applicationKey  获得对应的菜单
    return "{\"data\":" + JSON.toJSONString(params) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  @RequestMapping("/getAllUserOrRoleByMenuApplication")
  public String getAllUserOrRoleByMenuApplication(@RequestBody String data) {
    Map params = new HashMap();
    JSONObject req = JSON.parseObject(data);
    String organizationUnitId = req.getString("organizationUnitId");
    String scope = req.getString("scope");
    List<String> scopes = JSON.parseArray(scope, String.class);
    List<Role> roleInfoList = new ArrayList<>();
    List<Account> accountInfoList = new ArrayList<>();
    if (scopes != null && scopes.size() > 0) {
      for (String scopeInfo : scopes) {
        if (scopeInfo.equals("role")) {
          roleInfoList = MembershipManagement.getInstance().getRoleService()
            .findAllByOrganizationUnitId(organizationUnitId);
        }
        if (scopeInfo.equals("account")) {
          accountInfoList = MembershipManagement.getInstance().getAccountService()
            .findAllAccountsByOrganization(organizationUnitId);
        }
      }
      params.put("roleInfoList", roleInfoList);
      params.put("accountInfoList", accountInfoList);
    }
    // 当前用户， 和传递的 applicationKey  获得对应的菜单
    return "{\"data\":" + JSON.toJSONString(params) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取角色对应的所有的菜单节点， 及已经拥有的菜单节点
   *
   * @param data 请求的数据内容
   * @return 返回树形结构数据 主要功能 超级管理员 、  系统管理员  、 管理员 、普通管理员 该功能只能给管理员进行权限赋值 拼装树形结构数据[{id:xxxx,childNodes:[]},{}]
   */
  @RequestMapping("/getAllDynamicTreeByRole")
  public String getAllDynamicTreeByRole(@RequestBody String data) {
    // 发送 applicationKey:则查询当前发送key ， 如果不发送key 则查询 当前 能访问的所有应用 ，  获取对应是否全局， 如果是全局 则查询所有的应用信息

    // Menus#Application#00000000-0000-0000-0000-000000000000#ApplicationMenu#00000000-0000-0000-0000-000000000000 ；默认顶级
    JSONObject req = JSON.parseObject(data);
    // 必须字段 ，注释 觉得id
    String roleId = req.getString("roleId");
    String applicationId = req.getString("applicationId");
    // 必填字段
    String tree = req.getString("tree");
    String parentId = req.getString("parentId");
    if (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) {
      parentId = "0";
    }
    List<Object> allApplication = getChildApplicationNodes(parentId, applicationId);
    StringBuffer resultBuffer = new StringBuffer();
    resultBuffer.append("{");
    resultBuffer.append("}");
    // 附加属性
    return "{\"data\":" + JSON.toJSONString(allApplication) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * @param parentId 上一级id
   * @param applicationId 所属应用id return [{},{},{}]    返回对应菜单问题
   */
  private List<Object> getChildMenuNodes(String parentId, String applicationId) {
    List<Object> result = new ArrayList<>();
    /**
     *  如果没有parentId 默认 0的时候 一定要设置 应用id
     */
    DataQuery query = new DataQuery();
    query.getWhere().put("application_id", applicationId);
    query.getWhere().put("parent_id", parentId);
    query.getWhere().put("status", 1);
    query.getOrders().add("order_id");
//    List<ApplicationMenuViewInfo> list = service.findApplicationMenuViewInfoAll(query);
//    if (list != null && list.size() > 0) {
//      for (ApplicationMenuViewInfo ApplicationMenu : list) {
//        ApplicationMenu.setChildNodes(getChildMenuNodes(ApplicationMenu.getId(), applicationId));
//        result.add(ApplicationMenu);
//      }
//    }
    return result;
  }

  /**
   * @param parentId 所属应用对应上一级
   * @return [{}，{}，{}] 0
   */
  private List<Object> getChildApplicationNodes(String parentId, String inApplicationId) {

    List<Object> result = new ArrayList<>();
    List<Object> apps = new ArrayList<>();

    /**
     * 1、如果传入applicationId 则查询 ，当前下所有的菜单
     */
    if (!StringUtil.isNullOrEmpty(inApplicationId)) {
      Application info = AppsContext.getInstance().getApplicationService().findOne(inApplicationId);
      if (info != null) {
        apps.add(info);
      }
    } else {
      //  查询 带有 登录人 所有的应用
      String accountId = KernelContext.getCurrent().getUser().getId();
      List<Application> applications = AppsContext.getInstance().getApplicationService().findAllByAccountId(accountId);
      if (applications != null && applications.size() > 0) {
        apps.addAll(applications);
      }
    }

    // 发送 applicationKey:则查询当前发送key ， 如果不发送key 则查询 当前 能访问的所有应用 ，  获取对应是否全局， 如果是全局 则查询所有的应用信息

    DataQuery query = new DataQuery();

    // 应用系统
    query.getWhere().put("parent_id", parentId);
    query.getWhere().put("status", 1);
    query.getOrders().add("order_id");
    query.getOrders().add("code");
    // List<ApplicationViewInfo> list = AppsContext.getInstance().getApplicationService().findApplicationViewInfoAll(query);
    // 查询当前所有的应用情况 。
    if (apps != null && apps.size() > 0) {
      for (Object Application : apps) {
        Application info = (Application) Application;
        info.setParentId("0");
        List<Object> child = getChildMenuNodes(info.getId(), info.getId());
        info.setChildNodes(child);
        result.add(info);
      }
    }
    return result;
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
}
