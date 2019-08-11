package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.services.ApplicationFeatureService;
import com.x3platform.apps.services.ApplicationMenuService;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.RoleInfo;
import com.x3platform.membership.services.AccountService;
import com.x3platform.membership.services.RoleService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用功能管理
 * 应用功能是否存在。。。。
 */
@Lazy(true)
@RestController
@RequestMapping("/api/apps/applicationFeature")
public class ApplicationFeatureController {

  // 业务逻辑服务接口
  private ApplicationFeatureService iFeatureService = AppsContext.getInstance().getApplicationFeatureService();
  // 保存按钮也在保存菜单
  private ApplicationMenuService ApplicationMenuService = AppsContext.getInstance().getApplicationMenuService();
  /**
   * 新写法 采用注解方式
   */

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
  public String Save(@RequestBody String data) {
    ApplicationFeature record = JSON.parseObject(data, ApplicationFeature.class);
    this.iFeatureService.save(record);
    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }


  /**
   *  角色绑定菜单及应用功能和应用功能
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  @RequestMapping("bindFeatureRelation")
  public String bindFeatureRelation(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String roleId = req.getString("roleId");
    JSONObject dataObj = req.getJSONObject("data");
    // 应用菜单绑定及解绑工作
    String bindMenuListStr = dataObj.getString("allbindMenuCheckList");
    String unBindMenuListStr = dataObj.getString("allunBindMenuCheckList");
    List<String> bindMenuList = JSON.parseArray(bindMenuListStr,String.class);
    List<String> unbindMenuList = JSON.parseArray(unBindMenuListStr,String.class);
    this.ApplicationMenuService.bindAuthorizationScopeByRole(roleId,bindMenuList,unbindMenuList);

    // 应用功能绑定及解绑工作
    String bindStr=dataObj.getString("allbindFeatureCheckList");
    String unBindStr = dataObj.getString("allunBindFeatrueCheckList") ;
    List bindFeatureList = JSON.parseArray(bindStr,String.class);
    List unBindFeatureList = JSON.parseArray(unBindStr,String.class);
    this.iFeatureService.bindFeatureRelation(roleId,bindFeatureList,unBindFeatureList); // 操作步骤，1删除解绑数据，2 新增 选中数据
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
    this.iFeatureService.delete(id);
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
    ApplicationFeature entity = this.iFeatureService.findOne(id);
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
    List<ApplicationFeature> list = this.iFeatureService.findAll(query);
    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 根据菜单id、角色id 、应用Id 查询对应显示情况  ,查询菜单下所有的按钮权限及其他公共基础权限
   * @param data
   */
  @RequestMapping("/findAllByMenu")
  public String findAllByMenu(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String menuId = req.getString("menuId");
    String roleId = req.getString("roleId");
    String applicationId = req.getString("applicationId");
    /**
     * 根据菜单 和 应用问题， 查找当前
     */
    Map<String,List<ApplicationFeature>> result = iFeatureService.findAllByMenu( menuId,roleId,applicationId);
    return "{\"data\":" + JSON.toJSONString(result) + ","
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

    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ApplicationFeature> list = this.iFeatureService.findAll(query);

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

    boolean result = this.iFeatureService.isExist(id);

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
  public String getDynamicTreeView(@RequestBody String data) {
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

      // List<Application> list = AppsContext.getInstance().getApplicationService().findAll(whereClause);
      List<Application> list = AppsContext.getInstance().getApplicationService().findAll(query);

      for (Application item : list) {
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


      // List<ApplicationMenu> list = AppsContext.getInstance().getApplicationMenuService().findAll(whereClause);
      List<ApplicationMenu> list = AppsContext.getInstance().getApplicationMenuService().findAll(query);

      for (ApplicationMenu item : list) {
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

  /**
   * 获取菜单
   *
   * @param data 请求的数据内容
   * @return 返回树型结构结果
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
    if (StringUtil.isNullOrEmpty(parentId)) {
      parentId = UUIDUtil.emptyString("D");
    }
    Application application = AppsContext.getInstance().getApplicationService().findOneByApplicationName(applicationName);
    StringBuilder outString = new StringBuilder();
    outString.append("{\"data\":{");
    outString.append("\"applicationId\":\"" + application.getId() + "\",");
    outString.append("\"applicationName\":\"" + applicationName + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":" + getMenuChildNodes(application.getId(), parentId, menuType));
    outString.append("}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    return outString.toString();
  }

  private String getMenuScopeChildNodes(String applicationId, String parentId, String menuType, String accountId) {
    /**
     * 判断是否是管理员角色权限 ， 如果是普通角色，则需要进行判断
     */
    Account accountInfo = KernelContext.getCurrent().getUser();  // 查询接管的当前用户
    List<ApplicationMenu> list = new ArrayList<>();
    // 内置超级管理员帐号 , 配置管理员账号 ， 配置审查员账号 ， 配置可访问成员账号
    boolean isAdministrator = AppsContext.getInstance().
      getApplicationService().isAdministrator(accountInfo, applicationId); // 内置超级管理员账号
    // 判断当前是否是应用管理员 配置，角色

    if (isAdministrator) {
      //  判断是否是应用管理员   ，   审查员
      //   判断是否是应用管理员中的 管理员角色
      list = AppsContext.getInstance().getApplicationMenuService().getMenusByParentId(applicationId, parentId, menuType);

    } else {
      /**
       *  判断是否是审查员  然后判断是否有可访问权限
       */
      // 审查员
      boolean isReviewer = AppsContext.getInstance().
        getApplicationService().isReviewer(accountInfo, applicationId);
      // 可访问成员
      boolean isMember = AppsContext.getInstance().
        getApplicationService().isMember(accountInfo, applicationId);
      // 审查员
      if (isReviewer) {
        list = AppsContext.getInstance().getApplicationMenuService().getMenusByParentId(applicationId, parentId, menuType);
      }
      // 可访问成员
      if (isMember) {
        list = AppsContext.getInstance().getApplicationMenuService().getMenusScopeByParentId(applicationId, parentId, menuType, accountId);
      }

    }
    StringBuilder outString = new StringBuilder();
    outString.append("[");
    if (list != null && list.size() > 0) {
      for (ApplicationMenu item : list) {
        outString.append("{");
        outString.append("\"id\":\"" + item.getId() + "\",");
        outString.append("\"parentId\":\"" + StringUtil.toSafeJson((item.getMenuType().equals("ApplicationMenu") && item.getApplicationId().equals("00000000-0000-0000-0000-000000000001") && item.getParentId().equals("00000000-0000-0000-0000-000000000000")) ? "0" : item.getParentId()) + "\",");
        outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
        outString.append("\"iconPath\":\"" + StringUtil.toSafeJson(item.getIconPath()) + "\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(item.getUrl()) + "\",");
        outString.append("\"target\":\"" + item.getTarget() + "\",");
        outString.append("\"childNodes\":" + getMenuScopeChildNodes(item.getApplicationId(), item.getId(), item.getMenuType(), accountInfo.getId()));
        outString.append("},");
      }
      if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
        outString = outString.deleteCharAt(outString.length() - 1);
      }
    }
    outString.append("]");
    return outString.toString();
  }


  private String getMenuChildNodes(String applicationId, String parentId, String menuType) {
    StringBuilder outString = new StringBuilder();

    List<ApplicationMenu> list = AppsContext.getInstance().getApplicationMenuService().getMenusByParentId(applicationId, parentId, menuType);

    outString.append("[");

    for (ApplicationMenu item : list) {
      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"parentId\":\"" + StringUtil.toSafeJson((item.getMenuType().equals("ApplicationMenu") && item.getApplicationId().equals("00000000-0000-0000-0000-000000000001") && item.getParentId().equals("00000000-0000-0000-0000-000000000000")) ? "0" : item.getParentId()) + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"iconPath\":\"" + StringUtil.toSafeJson(item.getIconPath()) + "\",");
      outString.append("\"url\":\"" + StringUtil.toSafeJson(item.getUrl()) + "\",");
      outString.append("\"target\":\"" + item.getTarget() + "\",");
      outString.append("\"childNodes\":" + getMenuChildNodes(item.getApplicationId(), item.getId(), item.getMenuType()));
      outString.append("},");
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]");

    return outString.toString();
  }


}
