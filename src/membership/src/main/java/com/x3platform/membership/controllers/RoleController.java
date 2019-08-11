package com.x3platform.membership.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.KernelContext;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.Role;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.models.RoleInfo;
import com.x3platform.membership.services.RoleService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 API 接口
 *
 * @author ruanyu
 */
@Lazy(true)
@RestController
@RequestMapping("/api/membership/role")
public class RoleController {

  // AccountService accountService = MembershipManagement.getInstance().getAccountService();

  RoleService service = MembershipManagement.getInstance().getRoleService();

  // @Autowired
  // OrganizationUnitService organizationUnitService  ;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public final String save(@RequestBody String data) {
    RoleInfo param = JSON.parseObject(data, RoleInfo.class);
    service.save(param);
    if (StringUtil.isNullOrEmpty(param.getId())) {
      return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
    } else {
      return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
    }
  }

  /**
   * 删除记录,删除记录分为逻辑删除和物理删除
   *
   * @param data Xml 文档对象
   * @return 返回操作结果
   */
  @RequestMapping("/delete")
  public final String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    // 判断当前角色下，是否有子角色 ；
    List<Role> isExistRoleList = service.findAllByParentId(id);
    if (isExistRoleList != null && isExistRoleList.size() > 0) {
      return MessageObject.stringify("1", I18n.getStrings().text("membership_role_children_role_failed"));
    }
    // 判断当前角色下，是否存在是否存在人 ；
    List<Account> isExistAccountList = MembershipManagement.getInstance().getAccountService().findAllByRoleId(id);
    if (isExistAccountList != null && isExistAccountList.size() > 0) {
      return MessageObject.stringify("1", I18n.getStrings().text("membership_role_children_account_failed"));
    }
    try {
      service.delete(id);
    } catch (Exception e) {
      return MessageObject.stringify("1", I18n.getStrings().text("msg_delete_failed"));
      // e.printStackTrace();
    }
    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 返回对应消息 返回一个相关的实例列表.
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    Role param = service.findOne(id);
    outString.append("{\"data\":" + JSON.toJSONString(param) + ",");
    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    return outString.toString();
  }

  /**
   * @param data JSON 格式文本
   * @return 返回一个相关的实例列表.
   */
  @RequestMapping("/query")
  public final String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    DataPaging paging = DataPagingUtil.create(req.getString("paging"));
    DataQuery query = DataQuery.create(req.getString("query"));
    Object organizationUnitId = query.getWhere().get("organizationUnitId");
    if (organizationUnitId != null && "0".equals(organizationUnitId.toString())) {
      // 当设置为哦
      Object global = query.getWhere().get("global");
      if (global != null) {
        if ("true".equals(global.toString())) {
          query.getWhere().put("organizationUnitId", "");
        } else {
          query.getWhere().put("organizationUnitId", organizationUnitId); // 查询不出来，设置
        }
      } else {
        query.getWhere().put("organizationUnitId", '1'); // 查询不出来，设置
      }
    }

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<Role> list = service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取角色树 remark 1： 角色树是加载在组织机构上，进行展示的， 然后对角色进行操作
   */
  @RequestMapping("/getDynamicTreeView")
  public final String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    String tree = req.getString("tree");
    String parentId = req.getString("parentId");
    // 附加属性
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    String url = req.getString("url");
    String organizationUnitId = "";
    // 树形控件默认根节点标识为0, 需要特殊处理.
    if ("0".equals(parentId)) {
      String global = req.getString("global");
      if ("false".equals(global)) {
        // 判断获取， 然后进行支撑
        Account userInfo = KernelContext.getCurrent().getUser();
        List<OrganizationUnitInfo> info = MembershipManagement.getInstance()
          .getOrganizationUnitService().findAllByAccountId(userInfo.getId());

        OrganizationUnitInfo organizationUnit = MembershipManagement.getInstance()
          .getOrganizationUnitService().findCorporationByOrganizationUnitId(info.get(0).getId());

        // 需要进行组织结构控制
        organizationUnitId = organizationUnit.getId();
      }
    }

    parentId = (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId : parentId;

    StringBuilder outString = new StringBuilder();
    outString.append("{\"data\":");
    outString.append("{\"tree\":\"" + tree + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":[");
    // 查找树的子节点
    DataQuery query = new DataQuery();
    query.getVariables().put("scence", "QueryChild");
    query.getWhere().put("parent_id", parentId);
    query.getWhere().put("organizationUnitId", organizationUnitId);
    query.getWhere().put("status", 1);
    query.getOrders().add("order_id");
    query.getOrders().add("code");
    // 1、 根据当前登录人的组织机构 和 加载树形方式
    List<Role> roleInfoList = service.findAll(query);
    for (Role item : roleInfoList) {
      String mParentId = "0";
      if (!StringUtil.isNullOrEmpty(item.getParent().getId())) {
        mParentId = item.getParent().getId();
      }
      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"parentId\":\"" + StringUtil.toSafeJson(mParentId) + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"url\":\"" + StringUtil
        .toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName())) + "\",");
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
   * 获取角色树 remark 1： 角色树是加载在组织机构上，进行展示的， 然后对角色进行操作
   */
  @RequestMapping("/getMaxCode")
  public final String getMaxCode(@RequestBody String data) {
    Role role = null;
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    String search = req.getString("search");
    String value = req.getString("value");
    if (search.equals("organization")) {
      role = service.findMaxCodeByOrganizationUnitId(value);
    } else if (search.equals("role")) {
      role = service.findMaxCodeByParentId(value);
    }
    if (StringUtils.isEmpty(role)) {
      return "0001";
    } else {
      String code = role.getCode();
      return StringUtil.numberToStr(Integer.valueOf(code) + 1, "0000");
    }
  }

  /**
   * 组织机构获得所拥有的人员
   */
  @RequestMapping("/getOwnRoles")
  public final String getOwnRoles(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String orgainzationUnitId = req.getString("orgainzationUnitId");
    service.findAllRolesByOrganization(orgainzationUnitId);
    // Account accountInfo = this.service.findMaxCode(req);
    // String result = StringUtil.numberToStr(Integer.parseInt(accountInfo.getCode()) + 1, "00000");
    // return "{\"data\":" + result + ",\"paging\":" + JSON.toJSONString(accountInfo) + ","
    //   + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";

    return "";
  }

  /**
   * 根据角色。查询组织结构; 用途，使用在 默认角色带入组织结构;
   */
  @RequestMapping("/findOrganizationUnitInfo")
  public final String findOrganizationUnitInfo(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    Role role = service.findOne(id);

    if (role != null && role.getOrganizationUnitId() != null) {
      // 判断当前是否为 顶级节点
    }

    OrganizationUnitInfo organizationUnit = MembershipManagement.getInstance()
      .getOrganizationUnitService().findOne(role.getOrganizationUnitId());

    return "{\"data\":" + JSON.toJSONString(organizationUnit) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
