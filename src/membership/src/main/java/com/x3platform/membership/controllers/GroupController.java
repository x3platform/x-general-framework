package com.x3platform.membership.controllers;

import static com.x3platform.Constants.TEXT_EMPTY;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Group;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.GroupInfo;
import com.x3platform.membership.services.GroupService;
import com.x3platform.messages.MessageObject;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.x3platform.util.StringUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.membership.controllers.GroupController")
@RequestMapping("/api/membership/group")
public class GroupController {

  /**
   * 业务服务接口
   */
  private GroupService service = MembershipManagement.getInstance().getGroupService();

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

    Group entity = JSON.parseObject(data, GroupInfo.class);
    // 原始名称
    String originalName = req.getString("originalName");
    // 原始全局名称
    String originalGlobalName = req.getString("originalGlobalName");

    String memberText = req.getString("memberText");

    if (StringUtil.isNullOrEmpty(entity.getName())) {
      return MessageObject.stringify(
        I18n.getExceptions().text("code_membership_name_is_required"),
        I18n.getExceptions().text("text_membership_name_is_required"));
    }

    if (StringUtil.isNullOrEmpty(entity.getName())) {
      return MessageObject.stringify(
        I18n.getExceptions().text("code_membership_global_name_is_required"),
        I18n.getExceptions().text("text_membership_global_name_is_required"));
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
   * 删除记录
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   * }
   * </pre>
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
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   * }
   * </pre>
   * @return 一个相关的实例详细信息
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    Group entity = service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    /// 根据实际需要设置当前用户权限
    // query.getVariables().put("accountId", KernelContext.getCurrent().getUser().getId())

    // if (req.getString("su") == "1" && AppsSecurity.IsAdministrator(KernelContext.getCurrent().getUser(), membershipConfiguration.ApplicationName))
    // {
    //   query.getVariables().put("elevatedPrivileges", "1");
    // }

    /// 根据实际需要设置查询参数
    // query.getWhere().put("Name", searchText);
    // query.setLength(length);

    List<Group> list = service.findAll(query);

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

    List<Group> list = service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   * }
   * </pre>
   * @return 响应的数据内容
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
   * 查询是否存在相关的记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/create")
  public String create(@RequestBody String data) {
    GroupInfo entity = JSON.parseObject(data, GroupInfo.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));
    entity.setName(TEXT_EMPTY);
    entity.setGlobalName(TEXT_EMPTY);
    entity.setStatus(1);
    entity.setModifiedDate(LocalDateTime.now());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
