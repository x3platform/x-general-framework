package com.x3platform.membership.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.membership.models.RoleInfo;
import com.x3platform.membership.services.RoleService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.Date;
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

  RoleService service = MembershipManagement.getInstance().getRoleService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 操作结果
   */
  @RequestMapping("/save")
  public final String save(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    Role entity = JSON.parseObject(data, RoleInfo.class);

    String originalName = req.getString("originalName");

    String originalGlobalName = req.getString("originalGlobalName");

    String memberText = req.getString("memberText");

    if (StringUtil.isNullOrEmpty(entity.getName())) {
      return MessageObject.stringify(
        I18n.getExceptions().text("code_membership_name_is_required"),
        I18n.getExceptions().text("text_membership_name_is_required"));
    }

    if (StringUtil.isNullOrEmpty(entity.getId())) {
      // 新增
      if (this.service.isExistGlobalName(entity.getGlobalName())) {
        return MessageObject.stringify(
          I18n.getExceptions().text("code_membership_global_name_already_exists"),
          I18n.getExceptions().text("text_membership_global_name_already_exists"));
      }
      entity.setId(StringUtil.toUuid());
    } else {
      // 修改
      if (!originalGlobalName.equals(entity.getGlobalName())) {
        if (this.service.isExistGlobalName(entity.getGlobalName())) {
          return MessageObject.stringify(
            I18n.getExceptions().text("code_membership_global_name_already_exists"),
            I18n.getExceptions().text("text_membership_global_name_already_exists"));
        }
      }
    }

    // 重置成员关系
    entity.resetMemberRelations(memberText);

    service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录,删除记录分为逻辑删除和物理删除
   *
   * @param data Xml 文档对象
   * @return 操作结果
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
    }
    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 对应消息 一个相关的实例列表
   *
   * @return 响应的数据内容
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    Role entity = service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * @param data JSON 格式文本
   * @return 响应的数据内容
   */
  @RequestMapping("/query")
  public final String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.create(req.getString("paging"));
    DataQuery query = DataQuery.create(req.getString("query"));

    query.getVariables().put("scene", "Query");

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<Role> list = service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 创建新的对象信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/create")
  public String create(@RequestBody String data) {
    RoleInfo entity = JSON.parseObject(data, RoleInfo.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));

    // 根据实际需要设置默认值
    entity.setStatus(1);
    entity.setModifiedDate(new Date());
    entity.setCreatedDate(new Date());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询组织下所有的角色
   */
  @RequestMapping("/findAllByOrganizationUnitId")
  public final String findAllByOrganizationUnitId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String organizationUnitId = req.getString("organizationUnitId");
    String depthValue = req.getString("depth");
    int depth = 0;
    if (!StringUtil.isNullOrEmpty(depthValue)) {
      depth = Integer.valueOf(depthValue);
    }
    List<Role> list = service.findAllByOrganizationUnitId(organizationUnitId, depth);

    return "{\"data\":" + JSON.toJSONString(list) + ","
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

    parentId = (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId : parentId;

    StringBuilder outString = new StringBuilder();
    outString.append("{\"data\":");
    outString.append("{\"tree\":\"" + tree + "\",");
    outString.append("\"parentId\":\"" + parentId + "\",");
    outString.append("\"childNodes\":[");
    // 查找树的子节点
    DataQuery query = new DataQuery();
    query.getVariables().put("scene", "QueryChild");
    query.getWhere().put("parent_id", parentId);
    query.getWhere().put("organizationUnitId", organizationUnitId);
    query.getWhere().put("status", 1);
    query.getOrders().add("order_id");
    query.getOrders().add("code");
    // 1、 根据当前登录人的组织机构 和 加载树形方式
    List<Role> roleInfoList = service.findAll(query);
    for (Role item : roleInfoList) {
      String mParentId = "00000000-0000-0000-0000-000000000000";
/*      if(null==item.getParent()){
        mParentId="00000000-0000-0000-0000-000000000001";
      }*/

      if (item.getParent() != null) {
        mParentId = item.getParent().getId();
      }

      outString.append("{");
      outString.append("\"id\":\"" + item.getId() + "\",");
      outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getId()) + "\",");
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
}
