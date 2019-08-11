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
import com.x3platform.membership.models.OrganizationInfo;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织单元 API 接口
 *
 * @author ruanyu
 */
@Lazy(true)
@RestController
@RequestMapping("/api/membership/organizationUnit")
public class OrganizationUnitController {

  // @Autowired(required = false)
  // OrganizationService organizationService;

  OrganizationUnitService organizationUnitService = MembershipManagement.getInstance().getOrganizationUnitService();

  // @Autowired(required = false)
  // RoleService roleService ;

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

    OrganizationUnitInfo param = JSON.parseObject(data, OrganizationUnitInfo.class);
    organizationUnitService.save(param);
    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   *
   * 逻辑删除部门
   * @param  data
   * @return 返回操作结果
   */
  @RequestMapping("/delete")
  public final String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    // 组织结构删除 ， 1、判断是否有子组织 ， 2， 判断是否有角色 （如果有角色）
    List<OrganizationUnitInfo> isExistChildList = organizationUnitService.getChildOrganizationByOrganizationUnitId(id);
    if(isExistChildList!=null && isExistChildList.size() > 0){
      return MessageObject.stringify("1", I18n.getStrings().text("membership_organization_unit_is_exist_children_organization_failed"));
    }
    List<Role> isExist = MembershipManagement.getInstance().getRoleService().findAllByOrganizationUnitId(id, -1);
    if(isExist!=null && isExist.size() > 0){
      return MessageObject.stringify("1", I18n.getStrings().text("membership_organization_unit_is_exist_children_role_failed"));
    }
    // 判断 是否存在
    try{
      organizationUnitService.delete(id);
    }catch (Exception e){
      return MessageObject.stringify("1", I18n.getStrings().text(e.getLocalizedMessage()));
    }
    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 获取分页内容 / get paging.
   *
   * @param data JSON 对象
   * @return 返回一个相关的实例列表.
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();
    JSONObject req = JSON.parseObject(data);
    // 组织单位唯一标识
    String id = req.getString("id");
    OrganizationUnitInfo param = organizationUnitService.findOne(id);
    outString.append("{\"data\":" + JSON.toJSONString(param) + ",");
    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    return outString.toString();
  }

  /*
   * 动态加载 判断当前登录人员 ；  判断是否是超级管理员  ； 如果是内置的超级管理员 则查看所有 ； 不是超级管理员则查询当前账户绑定的顶级 组织 ； （组织机构必填）
   * 获取动态加载的树节点数据
   * @param data 请求的数据
   * @return 返回树型结构结果
   */
  @RequestMapping("/getDynamicTreeView")
  public String getDynamicTreeView(@RequestBody String data) {
    // 查找 所属组织顶级 ; 判断当前用户是否是管理员，

    /**
     *  is 默认管理员，则查询所有数据 ；
     */
    JSONObject reqTree = JSON.parseObject(data);
    /* 必填字段 */
    String tree = reqTree.getString("tree");
    String global = reqTree.getString("global");  // 初始化的时候必填 ，必填 ， 是否为默认管理员，如果不是默认管理员，则查询当前登录人员所在组织 ；
    String parentId = reqTree.getString("parentId");
    // 附加属性
    String treeViewId = reqTree.getString("treeViewId");
    String treeViewName = reqTree.getString("treeViewName");
    String treeViewRootTreeNodeId = reqTree.getString("treeViewRootTreeNodeId");
    String url = reqTree.getString("url");

    StringBuilder outString = new StringBuilder();


    // 树形控件默认根节点标识为0, 需要特殊处理.
    if(parentId.equals("0")){

      if (StringUtil.isNullOrEmpty(global)) {
        // 缺少参数
        return MessageObject.stringify("1", I18n.getStrings().text("interface_not_params"));
      }
      /**
       * 查询前所有， 不然查询，当前父节点的子节点即可
       */
      if(global.equals("true")){
        outString.append("{\"data\":");
        outString.append("{\"tree\":\"" + tree + "\",");
        outString.append("\"parentId\":\"" + parentId + "\",");
        outString.append("\"childNodes\":[");
        List<OrganizationInfo> infos = MembershipManagement.getInstance().getOrganizationService().findAll();
        if(infos !=null && infos.size() > 0){
          for (OrganizationInfo item : infos) {
            outString.append("{");
            outString.append("\"id\":\"" + item.getId() + "\",");
            outString.append("\"parentId\":\"" + StringUtil.toSafeJson("0") + "\",");
            outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
            outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName())) + "\",");
            outString.append("\"target\":\"_self\"");
            outString.append("},");
          }
          if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
            outString = outString.deleteCharAt(outString.length() - 1);
          }
        }
      }else{
        // 初始化，初始化则查询当前用户的顶层节点
        Account Account = KernelContext.getCurrent().getUser();
        if(Account==null){
          return  MessageObject.stringify("-1", I18n.getStrings().text("connect_session_passed"));
        }
        /**
         * 强制转换，转换后注意查询为空 ；
         */
        List<OrganizationUnitInfo> info =  organizationUnitService.findAllByAccountId(Account.getId());
        OrganizationUnitInfo rootOrganization =  organizationUnitService.findCorporationByOrganizationUnitId(info.get(0).getId());
        // 根据设置 ，查询对应所有数据问题,如果一个人有多个组织， 则设置他的顶级组织是一样的。 ；
        parentId = rootOrganization.getId();
        outString.append("{\"data\":");
        outString.append("{\"tree\":\"" + tree + "\",");
        outString.append("\"parentId\":\"" + parentId + "\",");
        outString.append("\"childNodes\":[");

        outString.append("{");
        outString.append("\"id\":\"" + rootOrganization.getId() + "\",");
        outString.append("\"parentId\":\"" + StringUtil.toSafeJson(rootOrganization.getParentId().equals(treeViewRootTreeNodeId) ? "0" : rootOrganization.getParentId()) + "\",");
        outString.append("\"name\":\"" + StringUtil.toSafeJson(rootOrganization.getName()) + "\",");
        outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", rootOrganization.getId()).replace("{treeNodeName}", rootOrganization.getName())) + "\",");
        outString.append("\"target\":\"_self\"");
        outString.append("}");

/*        DataQuery query = new DataQuery();
          query.getWhere().put("parent_id", parentId);
          query.getWhere().put("status", 1);
          query.getOrders().add("order_id");
          query.getOrders().add("code");
        List<OrganizationUnit> list = this.service.findAll(query);
        if(list!=null && list.size() > 0){
          for (OrganizationUnit item : list) {
            outString.append("{");
            outString.append("\"id\":\"" + item.getId() + "\",");
            outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getParentId().equals(treeViewRootTreeNodeId) ? "0" : item.getParentId()) + "\",");
            outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
            outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName())) + "\",");
            outString.append("\"target\":\"_self\"");
            outString.append("},");
          }
          if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
            outString = outString.deleteCharAt(outString.length() - 1);
          }
        }*/
      }
    }else{
      //  parentId = (StringUtil.isNullOrEmpty(parentId) || parentId.equals("0")) ? treeViewRootTreeNodeId : parentId;
      //  当为 00000-000000-00000-00000
      // 查找树的子节点
      outString.append("{\"data\":");
      outString.append("{\"tree\":\"" + tree + "\",");
      outString.append("\"parentId\":\"" + parentId + "\",");
      outString.append("\"childNodes\":[");

      DataQuery query = new DataQuery();
      query.getWhere().put("parent_id", parentId);
      query.getWhere().put("status", 1);
      query.getOrders().add("order_id");
      query.getOrders().add("code");
      List<OrganizationUnitInfo> list = organizationUnitService.findAll(query);
      if(list!=null && list.size() > 0){
        for (OrganizationUnitInfo item : list) {
          outString.append("{");
          outString.append("\"id\":\"" + item.getId() + "\",");
          outString.append("\"parentId\":\"" + StringUtil.toSafeJson(item.getParentId().equals(treeViewRootTreeNodeId) ? "0" : item.getParentId()) + "\",");
          outString.append("\"name\":\"" + StringUtil.toSafeJson(item.getName()) + "\",");
          outString.append("\"url\":\"" + StringUtil.toSafeJson(url.replace("{treeNodeId}", item.getId()).replace("{treeNodeName}", item.getName())) + "\",");
          outString.append("\"target\":\"_self\"");
          outString.append("},");
        }
        if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
          outString = outString.deleteCharAt(outString.length() - 1);
        }
      }

    }


    outString.append("]}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    return outString.toString();
  }

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
    List<OrganizationUnitInfo> list = organizationUnitService.findAll(query);
    paging.setTotal(DataPagingUtil.getTotal(list));
    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
