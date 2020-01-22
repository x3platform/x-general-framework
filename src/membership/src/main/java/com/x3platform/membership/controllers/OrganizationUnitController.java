package com.x3platform.membership.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.messages.MessageObject;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import java.util.Date;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 组织单元 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/membership/organizationUnit")
public class OrganizationUnitController {

  OrganizationUnitService service = MembershipManagement.getInstance().getOrganizationUnitService();

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
    JSONObject req = JSON.parseObject(data);
    OrganizationUnit param = JSON.parseObject(data, OrganizationUnitInfo.class);

    String originalName = req.getString("originalName");
    String originalGlobalName = req.getString("originalGlobalName");

    if (StringUtil.isNullOrEmpty(param.getName())) {
      return MessageObject.stringify(
        I18n.getExceptions().text("code_membership_name_is_required"),
        I18n.getExceptions().text("text_membership_name_is_required"));
    }

    if (StringUtil.isNullOrEmpty(param.getGlobalName())) {
      return MessageObject.stringify(
        I18n.getExceptions().text("code_membership_global_name_is_required"),
        I18n.getExceptions().text("text_membership_global_name_is_required"));
    }

    if (StringUtil.isNullOrEmpty(param.getId())) {
      // 新增
      if (this.service.isExistGlobalName(param.getGlobalName())) {
        // code_membership_global_name_already_exists
        return MessageObject.stringify(
          I18n.getExceptions().text("code_membership_global_name_already_exists"),
          I18n.getExceptions().text("text_membership_global_name_already_exists"));
      }

      param.setId(DigitalNumberContext.generate("Key_Guid"));
    } else {
      // 修改
      if (!originalGlobalName.equals(param.getGlobalName())) {
        if (this.service.isExistGlobalName(param.getGlobalName())) {
          return MessageObject.stringify(
            I18n.getExceptions().text("code_membership_global_name_already_exists"),
            I18n.getExceptions().text("text_membership_global_name_already_exists"));
        }
      }

      if (!originalName.equals(param.getName())) {
        List<OrganizationUnit> list = MembershipManagement.getInstance().getOrganizationUnitService()
          .findAllByParentId(param.getParentId());

        for (OrganizationUnit item : list) {
          if (item.getName().equals(param.getName())) {
            return MessageObject.stringify(
              I18n.getExceptions().text("code_membership_organization_unit_child_node_name_already_exists"),
              I18n.getExceptions().text("text_membership_organization_unit_child_node_name_already_exists"));
          }
        }
      }
    }

    service.save(param);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 逻辑删除部门
   *
   * @return 返回操作结果
   */
  @RequestMapping("/delete")
  public final String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    // 删除组织单元时需要判断限制条件
    // 1.判断是否有子组织
    List<OrganizationUnit> organizationUnits = service.getChildOrganizationByOrganizationUnitId(id);

    if (organizationUnits != null && !organizationUnits.isEmpty()) {
      return MessageObject.stringify("1",
        I18n.getStrings().text("membership_organization_unit_is_exist_children_organization_failed"));
    }

    // 2.判断是否有角色
    List<Role> roles = MembershipManagement.getInstance().getRoleService().findAllByOrganizationUnitId(id, -1);

    if (roles != null && !roles.isEmpty()) {
      return MessageObject.stringify("1",
        I18n.getStrings().text("membership_organization_unit_is_exist_children_role_failed"));
    }

    service.delete(id);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 根据组织单元标识查询对象详细信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例详细信息
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 组织单元唯一标识
    String id = req.getString("id");

    OrganizationUnit entity = service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   *
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 查询公司，查询等级
    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    Map params = (Map) JSONObject.parse(data);

    List<OrganizationUnit> list = service.findAll(params);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取带分页信息的列表内容
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

    List<OrganizationUnit> list = service.findAll(query);

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
    OrganizationUnitInfo entity = JSON.parseObject(data, OrganizationUnitInfo.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));

    // 根据实际需要设置默认值
    entity.setLocking(0);
    entity.setStatus(1);
    entity.setModifiedDate(new Date());
    entity.setCreatedDate(new Date());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取树节点数据
   *
   * @param data 请求的数据
   * @return 返回树形结构数据
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
   * @return 返回树形结构数据
   */
  @RequestMapping("/getDynamicTreeView")
  public String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 必填字段
    // 树的显示名称
    String treeViewName = req.getString("treeViewName");
    // 树的根节点标识
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    // 父级节点标识
    String parentId = req.getString("parentId");
    // 命令输出格式化
    String commandFormat = req.getString("commandFormat");

    DynamicTreeView treeView = service
      .getDynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId, commandFormat);

    return "{\"data\":" + JSON.toJSONString(treeView) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
