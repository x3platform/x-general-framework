package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationSettingGroup;
import com.x3platform.apps.services.ApplicationSettingGroupService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用参数分组 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/apps/applicationSettingGroup")
public class ApplicationSettingGroupController {

  private static String DIGITAL_NUMBER_KEY_CODE = "Table_Application_Setting_Group_Key_Code";

  /**
   * 业务服务接口
   */
  private ApplicationSettingGroupService service = AppsContext.getInstance().getApplicationSettingGroupService();

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
    ApplicationSettingGroup entity = JSON.parseObject(data, ApplicationSettingGroup.class);

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !service.isExist(entity.getId());

    if (isNewObject && StringUtil.isNullOrEmpty(entity.getCode())) {
      entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
    }

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

    ApplicationSettingGroup entity = service.findOne(id);

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

    // if (req.getString("su") == "1" && AppsSecurity.IsAdministrator(KernelContext.getCurrent().getUser(), appsConfiguration.ApplicationName))
    // {
    //   query.getVariables().put("elevatedPrivileges", "1");
    // }

    /// 根据实际需要设置查询参数
    // query.getWhere().put("Name", searchText);
    // query.setLength(length);

    List<ApplicationSettingGroup> list = service.findAll(query);

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
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/query")
  public String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ApplicationSettingGroup> list = service.findAll(query);

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
   * 创建新的对象信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/create")
  public String create(@RequestBody String data) {
    ApplicationSettingGroup entity = JSON.parseObject(data, ApplicationSettingGroup.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));

    // 根据实际需要设置默认值
    entity.setStatus(1);
    // entity.setModifiedDate(new Date());
    // entity.setCreatedDate(new Date());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
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
    // commandFormat 空值处理
    if (StringUtil.isNullOrEmpty(commandFormat)) {
      commandFormat = "";
    }

    TreeView treeView = service.getTreeView(treeViewName, treeViewRootTreeNodeId, commandFormat);

    return "{\"data\":" + JSON.toJSONString(treeView) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取动态加载的树节点数据
   *
   * @param data 请求的数据
   * @return 树形结构数据
   */
  @RequestMapping("/getDynamicTreeView")
  public String getDynamicTreeView(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 树的显示名称
    String treeViewName = req.getString("treeViewName");
    // 树的根节点标识
    String treeViewRootTreeNodeId = req.getString("treeViewRootTreeNodeId");
    // 父级对象标识
    String parentId = req.getString("parentId");
    // 命令格式化
    String commandFormat = req.getString("commandFormat");
    // commandFormat 空值处理
    if (StringUtil.isNullOrEmpty(commandFormat)) {
      commandFormat = "";
    }

    DynamicTreeView treeView = service.getDynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId,
      commandFormat);

    return "{\"data\":" + JSON.toJSONString(treeView) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
