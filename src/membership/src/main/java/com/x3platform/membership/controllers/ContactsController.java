package com.x3platform.membership.controllers;

import static com.x3platform.Constants.TEXT_ALL;
import static com.x3platform.Constants.TEXT_DEFAULT;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.AuthorizationObjectType;
import com.x3platform.KernelContext;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.Group;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.membership.StandardOrganizationUnit;
import com.x3platform.membership.StandardRole;
import com.x3platform.membership.User;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 通讯录 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.membership.controllers.ContactsController")
@RequestMapping("/api/membership/contacts")
public class ContactsController {

  private boolean containsAuthorizationObjectType(String contactType, AuthorizationObjectType authorizationObjectType) {
    if (TEXT_DEFAULT.equals(contactType)) {
      contactType = "[account],[role],[organizationUnit],[group]";
    } else if (TEXT_ALL.equals(contactType)) {
      contactType = "[account],[role],[organizationUnit],[standardOrganizationUnit],[standardRole],[workflowRole]";
    } else if (!contactType.startsWith("[") && !contactType.endsWith("]")) {
      contactType = StringUtil.format("[{}]", contactType.replace(",", "],["));
    }

    String value = StringUtil.format("[{}]", StringUtil.toFirstLower(authorizationObjectType.getValue()));

    return contactType.contains(value);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询所有数据
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    StringBuilder outString = new StringBuilder();
    // 联系人类型
    String contactType = req.getString("contactType");
    // 包含被禁止的对象
    String  includeProhibitedValue = req.getString("includeProhibited");
    int includeProhibited = 0;
    if (!StringUtil.isNullOrEmpty( includeProhibitedValue)) {
      includeProhibited = Integer.valueOf( includeProhibitedValue);
    }

    // 授权对象名称关键字匹配
    String keyword = req.getString("keyword");
    DataQuery query = new DataQuery();
    // 设置查询场景
    query.getVariables().put("scene", "Contacts");
    query.getWhere().put("name", "%" + keyword + "%");
    query.getWhere().put("status", (includeProhibited == 1 ? "" : "1"));

    outString.append("{\"data\":[");

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.ACCOUNT)) {
      outString.append(formatAccount(MembershipManagement.getInstance()
        .getAccountService().findAll(query), includeProhibited));
    }

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.ORGANIZATION_UNIT)) {
      /// whereClause = String
      //  .format("( T.Name LIKE ##%%%1$s%%## %2$s )", key, (includeProhibited == 1 ? "" : "AND Status = 1"));

      outString.append(formatOrganizationUnit(MembershipManagement.getInstance().
        getOrganizationUnitService().findAll(query), includeProhibited));
    }

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.ROLE)) {
      /// whereClause = String
      //  .format("( T.Name LIKE ##%%%1$s%%## %2$s )", key, (includeProhibited == 1 ? "" : "AND Status = 1"));

      outString.append(formatRole(MembershipManagement.getInstance()
        .getRoleService().findAll(query), includeProhibited));
    }

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.GROUP)) {
      // whereClause = String
      //  .format("( T.Name LIKE ##%%%1$s%%## %2$s )", key, (includeProhibited == 1 ? "" : "AND Status = 1"));

      outString.append(formatGroup(MembershipManagement.getInstance()
        .getGroupService().findAll(query), includeProhibited));
    }

    // 标准组织
    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.STANDARD_ORGANIZATION_UNIT)) {
      // whereClause = String
      //  .format("( T.getName LIKE ##%%%1$s%%## %2$s )", key, (includeProhibited == 1 ? "" : "AND Status = 1"));

      outString.append(formatStandardOrganizationUnit(MembershipManagement.getInstance()
        .getStandardOrganizationUnitService().findAll(query)));
    }

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.STANDARD_ROLE)) {
      // whereClause = String.format("( T.getName LIKE ##%%%1$s%%## %2$s )", key,
      //  (includeProhibited == 1 ? "" : "AND ( Status = 1 AND Locking = 1 )"));

      outString.append(formatStandardRole(MembershipManagement.getInstance()
        .getStandardRoleService().findAll(query)));
    }

//    if ((contactType & AuthorizationObjectType.GeneralRole) == AuthorizationObjectType.GeneralRole) {
//      whereClause = String
//        .format("( T.getName LIKE ##%%%1$s%%## %2$s )", key, (includeProhibited == 1 ? "" : "AND Status = 1"));
//
//      outString.append(FormatGeneralRole(MembershipManagement.getInstance().GeneralRoleService.findAll(whereClause)));
//    }

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.CONTACT)) {
      //
      // 联系人[未开发]
      //

      //whereClause = string.Format(" T.getName LIKE ##%{0}%## ", key);

      //if (KernelConfigurationView.Instance.RuntimePattern == "Longfor")
      //{
      //    whereClause = string.Format(" T.PostName LIKE ##%{0}%## ", key);
      //}

      //outString.Append(FormatRole(MembershipManagement.getInstance().getRoleService().findAll(whereClause)));
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("],");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 预览角色或组织对应的实际人员
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  public String preview(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    StringBuilder outString = new StringBuilder();

    List<Account> list = null;

    String key = req.getString("key");

    String[] keyArray = key.split("[,]", -1);

    String[] temp = null;

    AuthorizationObjectType contactType;

    int viewCount = 0;

    int maxViewCount = 5;

    for (String keyItem : keyArray) {
      temp = keyItem.split("[#]", -1);

      if (temp.length != 3) {
        return "{\"message\":{\"returnCode\":1,\"value\":\"查询参数格式不正确。\"}}";
      }

      // 屏蔽保密角色成员信息
      if (MembershipConfigurationView.getInstance().getProhibitedPreviewObjects().contains(temp[2])) {
        return "{\"message\":{\"returnCode\":0,\"value\":\"\"}}";
      }

      switch (temp[0].toLowerCase()) {
        case "account":
          return "{\"message\":{\"returnCode\":0,\"value\":\"\"}}";

        case "organization":
          contactType = AuthorizationObjectType.ORGANIZATION_UNIT;
          break;

        case "role":
        default:
          contactType = AuthorizationObjectType.ROLE;
          break;

      }

      if (contactType == AuthorizationObjectType.ORGANIZATION_UNIT) {
        list = MembershipManagement.getInstance().getAccountService().findAllByOrganizationUnitId(temp[1]);

        for (Account item : list) {
          if (item.getStatus() == 0) {
            continue;
          }

          outString.append(String.format("%1$s;", item.getName()));

          viewCount++;

          if (viewCount == maxViewCount) {
            return "{\"message\":{\"returnCode\":0,\"value\":\"" + StringUtil.toSafeJson(outString.toString())
              + "...\"}}";
          }
        }
      }

      if (contactType == AuthorizationObjectType.ROLE) {
        list = MembershipManagement.getInstance().getAccountService().findAllByRoleId(temp[1]);

        for (Account item : list) {
          if (item.getStatus() == 0) {
            continue;
          }

          outString.append(String.format("%1$s;", item.getName()));

          viewCount++;

          if (viewCount == maxViewCount) {
            return "{\"message\":{\"returnCode\":0,\"value\":\"" + StringUtil.toSafeJson(outString.toString())
              + "...\"}}";
          }
        }
      }
    }

    return "{\"message\":{\"returnCode\":0,\"value\":\"" + StringUtil.toSafeJson(outString.toString()) + "\"}}";
  }

  /**
   * 查询所有数据
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  @RequestMapping("/findAllByOrganizationUnitId")
  public String findAllByOrganizationUnitId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    StringBuilder outString = new StringBuilder();

    // 联系人类型
    String contactType = req.getString("contactType");
    // 包含被禁止的对象
    String  includeProhibitedValue = req.getString("includeProhibited");
    int includeProhibited = 0;
    if (!StringUtil.isNullOrEmpty( includeProhibitedValue)) {
      includeProhibited = Integer.valueOf( includeProhibitedValue);
    }

    String organizationUnitId = req.getString("organizationUnitId");

    // 自动转换到我的公司
    if (organizationUnitId.equals("20000000-0000-0000-0000-000000000000")) {
      Account account = KernelContext.getCurrent().getUser();

      // 设置用户所属公司信息
      // 如果用户没有公司信息则为空值
      User user = MembershipManagement.getInstance().getUserService().findOne(account.getId());

      organizationUnitId = (user.getCorporationId() == null) ? "" : user.getCorporationId();
    }

    // 0 全部 1 2 4 8;

    outString.append("{\"data\":[");

    if (!StringUtil.isNullOrEmpty(organizationUnitId)) {
      // 组织信息
      if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.ORGANIZATION_UNIT)) {
        outString.append(formatOrganizationUnit(
          MembershipManagement.getInstance().getOrganizationUnitService().findOne(organizationUnitId)));

        outString.append(formatOrganizationUnit(
          MembershipManagement.getInstance().getOrganizationUnitService().findAllByParentId(organizationUnitId),
          includeProhibited));
      }

//      if ((contactType & AuthorizationObjectType.AssignedJob) == AuthorizationObjectType.AssignedJob) {
//        outString.append(FormatAssignedJob(
//          MembershipManagement.getInstance().AssignedJobService.findAllByOrganizationUnitId(organizationUnitId),
//          includeProhibited));
//      }

      if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.ROLE)) {
        outString.append(formatRole(MembershipManagement.getInstance().getRoleService().
          findAllByOrganizationUnitId(organizationUnitId), includeProhibited));
      }

      if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.ACCOUNT)) {
        outString.append(formatAccount(MembershipManagement.getInstance().getAccountService().
          findAllByOrganizationUnitId(organizationUnitId, true), includeProhibited));
      }

      if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
        outString = outString.deleteCharAt(outString.length() - 1);
      }
    }

    outString.append("],");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 查询所有数据
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  @RequestMapping("/findAllByStandardOrganizationUnitId")
  public String findAllByStandardOrganizationUnitId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    StringBuilder outString = new StringBuilder();

    // 联系人类型
    String contactType = req.getString("contactType");
    // 包含被禁止的对象
    String  includeProhibitedValue = req.getString("includeProhibited");
    int includeProhibited = 0;
    if (!StringUtil.isNullOrEmpty( includeProhibitedValue)) {
      includeProhibited = Integer.valueOf( includeProhibitedValue);
    }

    String standardOrganizationUnitId = req.getString("standardOrganizationUnitId");

    outString.append("{\"data\":[");

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.STANDARD_ORGANIZATION_UNIT)) {
      outString.append(formatStandardOrganizationUnit(
        MembershipManagement.getInstance().getStandardOrganizationUnitService().findOne(standardOrganizationUnitId)));

      outString.append(formatStandardOrganizationUnit(
        MembershipManagement.getInstance().getStandardOrganizationUnitService()
          .findAllByParentId(standardOrganizationUnitId)));
    }

    if (containsAuthorizationObjectType(contactType, AuthorizationObjectType.STANDARD_ROLE)) {
      outString.append(formatStandardRole(MembershipManagement.getInstance().getStandardRoleService()
        .findAllByStandardOrganizationUnitId(standardOrganizationUnitId)));
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("],");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 查询所有数据
   *
   * @param data 请求的数据内容
   * @return 返回操作结果
   */
  @RequestMapping("/findAllByGroupCatalogItemId")
  public String findAllByGroupNodeId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    StringBuilder outString = new StringBuilder();

    String groupType = req.getString("groupType");
    // 包含被禁止的对象
    String  includeProhibitedValue = req.getString("includeProhibited");
    int includeProhibited = 0;
    if (!StringUtil.isNullOrEmpty( includeProhibitedValue)) {
      includeProhibited = Integer.valueOf( includeProhibitedValue);
    }

    String catalogItemId = req.getString("CatalogItemId");

    // 0 全部 1 2 4 8;

    outString.append("{\"data\":[");

    switch (groupType) {
//      case "group":
//        outString
//          .append(
//            FormatGroup(MembershipManagement.getInstance().getGroupService().findAllByCatalogItemId(CatalogItemId),
//              includeProhibited));
//        break;
//      case "general-role":
//        outString.append(
//          FormatGeneralRole(
//            MembershipManagement.getInstance().GeneralRoleService.findAllByCatalogItemId(CatalogItemId)));
//        break;
//      case "standard-role":
//        List<StandardOrganizationUnit> standardOrganizationUnits = null;
//
//        if (CatalogItemId.indexOf("[CatalogItem]") == 0) {
//          String whereClause = String.format(
//            " CatalogItemId = ##%1$s## AND ( ParentId IS NULL OR ParentId = ##00000000-0000-0000-0000-000000000000## )  ",
//            CatalogItemId.replace("[CatalogItem]", ""));
//
//          standardOrganizationUnits = MembershipManagement.getInstance().getStandardOrganizationUnitService()
//            .findAll(whereClause);
//        } else {
//          standardOrganizationUnits = MembershipManagement.getInstance().getStandardOrganizationUnitService()
//            .findAllByParentId(CatalogItemId.replace("[StandardRole]", ""));
//        }
//
//        outString.append(FormatStandardOrganizationUnit(standardOrganizationUnits));
//
//        break;
//      case "standard-general-role":
//        outString.append(FormatStandardGeneralRole(
//          MembershipManagement.getInstance().StandardGeneralRoleService.findAllByCatalogItemId(CatalogItemId)));
//        break;
//      case "workflow-role":
//        outString.append(FormatWorkflowRole(CatalogItemId));
//        break;
      default:
        break;
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("],");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @return 返回操作结果
   */
  private String formatOrganizationUnit(List<OrganizationUnit> list, int includeProhibited) {
    StringBuilder outString = new StringBuilder();

    // 部门
    for (OrganizationUnit item : list) {
      // 过滤禁用的对象
      if (includeProhibited == 0 && item.getStatus() == 0) {
        continue;
      }

      // 过滤全局名称为空的对象
      if (StringUtil.isNullOrEmpty(item.getGlobalName())) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"[部门]" + StringUtil.toSafeJson(item.getGlobalName()) + "\",");
      outString.append("\"type\":\"organizationUnit\" ");
      outString.append("},");
    }

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @return 返回操作结果
   */
  private String formatOrganizationUnit(OrganizationUnit item) {
    StringBuilder outString = new StringBuilder();

    // 过滤禁用的对象
    if (item.getStatus() == 0) {
      return "";
    }

    // 过滤全局名称为空的对象
    if (StringUtil.isNullOrEmpty(item.getGlobalName())) {
      return "";
    }

    outString.append("{");
    outString.append("\"id\":\"" + item.getId() + "\",");
    outString.append("\"name\":\"[部门]" + StringUtil.toSafeJson(item.getGlobalName()) + "\",");
    outString.append("\"type\":\"organizationUnit\" ");
    outString.append("},");

    return outString.toString();
  }
  
  /**
   * 格式化数据
   *
   * @param list 列表
   * @param includeProhibited 是否包含被禁用对象
   * @return 返回操作结果
   */
//  private String FormatAssignedJob(List<AssignedJob> list, int includeProhibited) {
//    StringBuilder outString = new StringBuilder();
//
//    // 岗位
//    for (AssignedJob item : list) {
//      // 过滤禁用的对象
//      if (includeProhibited == 0 && item.getStatus() == 0) {
//        continue;
//      }
//
//      // 过滤全局名称为空的对象
//      if (StringUtil.isNullOrEmpty(item.getName())) {
//        continue;
//      }
//
//      outString.append("{");
//      outString.append("\"id\":\"" + item.getId() + "\",");
//      outString.append("\"name\":\"[岗位]" + StringUtil.toSafeJson(item.getName()) + "\",");
//      outString.append("\"type\":\"assignedJob\" ");
//      outString.append("},");
//    }
//
//    return outString.toString();
//  }

  /**
   * 格式化数据
   *
   * @param list 列表
   * @param includeProhibited 是否包含被禁用对象
   * @return 返回操作结果
   */
  private String formatRole(List<Role> list, int includeProhibited) {
    StringBuilder outString = new StringBuilder();

    // 部门
    for (Role item : list) {
      // 过滤禁用的对象
      if (includeProhibited == 0 && item.getStatus() == 0) {
        continue;
      }

      // 过滤全局名称为空的对象
      if (StringUtil.isNullOrEmpty(item.getGlobalName())) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"[角色]" + StringUtil.toSafeJson(item.getGlobalName()) + "\",");
      outString.append("\"type\":\"role\" ");
      outString.append("},");
    }

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @param list 列表
   * @param includeProhibited 是否包含被禁用对象
   * @return 返回操作结果
   */
  private String formatAccount(List<Account> list, int includeProhibited) {
    StringBuilder outString = new StringBuilder();

    // 人员数据
    for (Account item : list) {
      // 过滤禁用的对象
      if (includeProhibited == 0 && item.getStatus() == 0) {
        continue;
      }

      // 过滤全局名称为空的对象
      if (StringUtil.isNullOrEmpty(item.getGlobalName())) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"[人员]" + StringUtil.toSafeJson(item.getGlobalName()) + "\",");
      outString.append("\"type\":\"account\",");
      outString.append("\"status\":\"" + item.getStatus() + "\" ");
      outString.append("},");
    }

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @param list 列表
   * @param includeProhibited 是否包含被禁用对象
   * @return 返回操作结果
   */
  private String formatGroup(List<Group> list, int includeProhibited) {
    StringBuilder outString = new StringBuilder();

    // 部门
    for (Group item : list) {
      // 过滤禁用的对象
      if (includeProhibited == 0 && item.getStatus() == 0) {
        continue;
      }

      // 过滤全局名称为空的对象
      if (StringUtil.isNullOrEmpty(item.getGlobalName())) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"[群组]" + StringUtil.toSafeJson(item.getGlobalName()) + "\",");
      outString.append("\"type\":\"group\",");
      outString.append("\"status\":\"" + item.getStatus() + "\" ");
      outString.append("},");
    }

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @return 返回操作结果
   */
  private String formatStandardOrganizationUnit(List<StandardOrganizationUnit> list) {
    StringBuilder outString = new StringBuilder();

    // 部门
    for (StandardOrganizationUnit item : list) {
      // 过滤禁用的对象
      if (item.getStatus() == 0) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"[标准部门]" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"type\":\"standardOrganizationUnit\",");
      outString.append("\"status\":\"" + item.getStatus() + "\" ");
      outString.append("},");
    }

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @return 返回操作结果
   */
  private String formatStandardOrganizationUnit(StandardOrganizationUnit item) {
    StringBuilder outString = new StringBuilder();

    // 过滤禁用的对象
    if (item.getStatus() == 0) {
      return "";
    }

    outString.append("{");
    outString.append("\"id\":\"" + item.getId() + "\",");
    outString.append("\"name\":\"[标准部门]" + StringUtil.toSafeJson(item.getName()) + "\",");
    outString.append("\"type\":\"standardOrganizationUnit\" ");
    outString.append("},");

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @return 返回操作结果
   */
  private String formatStandardRole(List<StandardRole> list) {
    StringBuilder outString = new StringBuilder();

    // 部门
    for (StandardRole item : list) {
      // 过滤禁用的对象
      if (item.getStatus() == 0) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"[标准角色]" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"type\":\"standardRole\",");
      outString.append("\"status\":\"" + item.getStatus() + "\" ");
      outString.append("},");
    }

    return outString.toString();
  }

  /**
   * 格式化数据
   *
   * @return 返回操作结果
   */
//  private String FormatWorkflowRole(String CatalogItemId) {
//    StringBuilder outString = new StringBuilder();
//
//    switch (CatalogItemId) {
//      case "60000000-0000-0000-0001-000000000000":
//
//        outString.append("{");
//        outString.append("\"id\":\"00000000-0000-0000-0000-000000000000\",");
//        outString.append("\"name\":\"[流程角色]流程发起人\",");
//        outString.append("\"type\":\"initiator\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0001-000000000090\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["PriorityLeader90DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]集团部门负责人/地区公司负责人(九级负责人角色)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["PriorityLeader90DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"priorityLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0001-000000000080\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["PriorityLeader80DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]集团中心负责人/地区职能部门负责人(八级负责人角色)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["PriorityLeader80DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"priorityLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        break;
//
//      case "60000000-0000-0000-0002-000000000000":
//
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0002-000000000001\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["ForwardLeader01DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]公司负责人(正向一级领导)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["ForwardLeader01DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"forwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0002-000000000002\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["ForwardLeader02DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]部门负责人(正向二级领导)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["ForwardLeader02DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"forwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0002-000000000003\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["ForwardLeader03DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]中心负责人(正向三级领导)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["ForwardLeader03DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"forwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0002-000000000004\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["ForwardLeader04DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]小组负责人(正向四级领导)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["ForwardLeader04DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"forwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        break;
//
//      case "60000000-0000-0000-0003-000000000000":
//
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0003-000000000001\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["BackwardLeader01DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]直接领导(反向一级领导)\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["BackwardLeader01DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"backwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0003-000000000002\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["BackwardLeader02DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]反向二级领导\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["BackwardLeader02DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"backwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0003-000000000003\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["BackwardLeader03DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]反向三级领导\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["BackwardLeader03DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"backwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        outString.append("{");
//        outString.append("\"id\":\"60000000-0000-0000-0003-000000000004\",");
//        if (MembershipConfigurationView.Instance.Configuration.keySet()["BackwardLeader04DisplayName"] == null) {
//          outString.append("\"name\":\"[流程角色]反向四级领导\",");
//        } else {
//          outString.append("\"name\":\"[流程角色]" + MembershipConfigurationView.Instance.Configuration
//            .keySet()["BackwardLeader04DisplayName"].Value + "\",");
//        }
//        outString.append("\"type\":\"backwardLeader\",");
//        outString.append("\"status\":\"1\" ");
//        outString.append("},");
//        break;
//
//      default:
//        break;
//    }
//
//    return outString.toString();
//  }

  // -------------------------------------------------------
  // 树形菜单
  // -------------------------------------------------------

  /**
   * 获取树节点数据
   *
   * @param data 请求的数据
   * @return 返回树形结构数据
   */
  @RequestMapping("/getTreeView")
  public String getTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    StringBuilder outString = new StringBuilder();

    String organizationUnitId = req.getString("organizationUnitId");

    outString.append(this.getTreeView1(organizationUnitId));

    if (StringUtil.substring(outString.toString(), outString.length() - 2, 2).equals("]}")) {
      outString = outString.delete(outString.length() - 2, outString.length() - 2 + 2);
    }

    outString.append("],\"message\":{\"returnCode\":0,\"value\":\"查询成功。\"}}");

    return outString.toString();
  }

  /**
   */
  public String getTreeView1(String organizationUnitId) {
    StringBuilder outString = new StringBuilder();

    String childNodes = "";

    List<OrganizationUnit> list = MembershipManagement.getInstance().getOrganizationUnitService()
      .findAllByParentId(organizationUnitId);

    outString.append("{\"data\":[");

    for (OrganizationUnit item : list) {
      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");

      childNodes = getTreeView(item.getId());

      childNodes = childNodes.replace("{\"data\":[", "[").replace("]}", "]");

      outString.append("\"childNodes\":" + childNodes + ", ");

      outString.append("\"status\":\"" + item.getStatus() + "\" ");

      outString.append("},");
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]}");

    return outString.toString();
  }

  /**
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据
   * @return 返回树形结构数据
   */
  public String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 必填字段
    String tree = req.getString("tree");
    String parentId = req.getString("parentId");

    // 附加属性
    String treeViewType = req.getString("treeViewType");
    String treeViewId = req.getString("treeViewId");
    String treeViewName = req.getString("treeViewName");
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");

    String url = req.getString("url");

    // 树形控件默认根节点标识为0, 需要特殊处理.
    parentId =
      (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId
        : parentId;

    StringBuilder outString = new StringBuilder();

    outString.append("{\"data\":");
    outString.append("{\"tree\":\"" + tree + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("childNodes:[");

    Account account = KernelContext.getCurrent().getUser();

    switch (treeViewType) {
//      case "my-corporation":
//
//        IMemberInfo member = MembershipManagement.getInstance().MemberService[account.Id];
//
//        if (member.getCorporation() == null) {
//          parentId = "00000000-0000-0000-0000-000000000001";
//        } else {
//          treeViewRootTreeNodeId = MembershipManagement.getInstance().getMemberService().findOne(account.getId()).Corporation.Id;
//        }
//
//        if (parentId.equals("20000000-0000-0000-0000-000000000000")) {
//          parentId = treeViewRootTreeNodeId;
//        }
//
//        outString.append(getTreeViewWithOrganizationUnit(parentId, url, treeViewRootTreeNodeId));
//        break;

      case "group":
//      case "general-role":
//        outString.append(getTreeViewWithCatalogItem(parentId, url, treeViewRootTreeNodeId));
//        break;
      case "standard-organization-unit":
        outString.append(getTreeViewWithStandardOrganizationUnitTreeNode(parentId, url, treeViewRootTreeNodeId));
        break;
      case "standard-role":
        outString.append(getTreeViewWithStandardOrganizationUnitTreeNode(parentId, url, treeViewRootTreeNodeId));
        break;
//      case "standard-general-role":
//        outString.append(getTreeViewWithCatalogItem(parentId, url, treeViewRootTreeNodeId));
//        break;
      case "workflow-role":
        outString.append(getTreeViewWithWorkflowRole(parentId, url, treeViewRootTreeNodeId));
        break;
      case "organization-unit":
      default:
        outString.append(getTreeViewWithOrganizationUnit(parentId, url, treeViewRootTreeNodeId));
        break;
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]},\"message\":{\"returnCode\":0,\"value\":\"查询成功。\"}}");

    return outString.toString();
  }

//  private String getTreeViewWithCatalogItem(String parentId, String url, String treeViewRootTreeNodeId) {
//    List<CatalogItem> list = MembershipManagement.getInstance().getCatalogItemService().findAllByParentId(parentId);
//
//    StringBuilder outString = new StringBuilder();
//
//    for (CatalogItem item : list) {
//      if (item.getStatus() == 0) {
//        continue;
//      }
//
//      outString.append("{");
//      outString.append("\"id\":\"" + item.getId() + "\",");
//      outString.append(
//        "\"parentId\":\"" + StringUtil
//          .toSafeJson(treeViewRootTreeNodeId.equals(item.getParentId()) ? "0" : item.getParentId())
//          + "\",");
//      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
//      outString.append("\"title\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
//      outString.append(
//        "\"url\":\"" + StringUtil
//          .toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName()))
//          + "\",");
//      outString.append("\"target\":\"_self\"");
//      outString.append("},");
//    }
//
//    return outString.toString();
//  }

  private String getTreeViewWithWorkflowRole(String parentId, String url, String treeViewRootTreeNodeId) {
    StringBuilder outString = new StringBuilder();

    outString.append("{");
    outString.append("\"id\":\"60000000-0000-0000-0001-000000000000\",");
    outString.append("\"parentId\":\"0\",");
    outString.append("\"name\":\"流程角色\",");
    outString.append("\"title\":\"流程角色\",");
    outString.append(
      "\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", "60000000-0000-0000-0001-000000000000"))
        + "\",");
    outString.append("\"target\":\"_self\"");
    outString.append("},");

    outString.append("{");
    outString.append("\"id\":\"60000000-0000-0000-0002-000000000000\",");
    outString.append("\"parentId\":\"0\",");
    outString.append("\"name\":\"正向领导\",");
    outString.append("\"title\":\"正向领导\",");
    outString.append(
      "\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", "60000000-0000-0000-0002-000000000000"))
        + "\",");
    outString.append("\"target\":\"_self\"");
    outString.append("},");

    outString.append("{");
    outString.append("\"id\":\"60000000-0000-0000-0003-000000000000\",");
    outString.append("\"parentId\":\"0\",");
    outString.append("\"name\":\"反向领导\",");
    outString.append("\"title\":\"反向领导\",");
    outString.append(
      "\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", "60000000-0000-0000-0003-000000000000"))
        + "\",");
    outString.append("\"target\":\"_self\"");
    outString.append("}");

    return outString.toString();
  }

  private String getTreeViewWithOrganizationUnit(String parentId, String url, String treeViewRootTreeNodeId) {
    List<OrganizationUnit> list = MembershipManagement.getInstance().getOrganizationUnitService()
      .findAllByParentId(parentId);

    StringBuilder outString = new StringBuilder();

    for (OrganizationUnit item : list) {
      if (item.getStatus() == 0) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append(
        "\"parentId\":\"" + StringUtil
          .toSafeJson(treeViewRootTreeNodeId.equals(item.getParent().getId()) ? "0" : item.getParent().getId())
          + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"title\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append(
        "\"url\":\"" + StringUtil
          .toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName()))
          + "\",");
      outString.append("\"target\":\"_self\"");
      outString.append("},");
    }

    return outString.toString();
  }

  private String getTreeViewWithStandardOrganizationUnitTreeNode(String parentId, String url,
    String treeViewRootTreeNodeId) {
    StringBuilder outString = new StringBuilder();

    List<StandardOrganizationUnit> list = MembershipManagement.getInstance().getStandardOrganizationUnitService()
      .findAllByParentId(parentId);

    for (StandardOrganizationUnit item : list) {
      if (item.getStatus() == 0) {
        continue;
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append(
        "\"parentId\":\"" + StringUtil
          .toSafeJson(treeViewRootTreeNodeId.equals(item.getParentId()) ? "0" : item.getParentId())
          + "\",");
      outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append("\"title\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
      outString.append(
        "\"url\":\"" + StringUtil
          .toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName()))
          + "\",");
      outString.append("\"target\":\"_self\"");
      outString.append("},");
    }

    return outString.toString();
  }
}

