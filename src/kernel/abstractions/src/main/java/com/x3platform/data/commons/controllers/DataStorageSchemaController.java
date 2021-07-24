package com.x3platform.data.commons.controllers;

import com.x3platform.data.commons.DataCommonContext;
import com.x3platform.data.commons.models.*;
import com.x3platform.data.commons.services.*;

import com.x3platform.data.*;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.messages.MessageObject;
import com.x3platform.globalization.I18n;
import com.x3platform.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.data.commons.controllers.DataStorageSchemaController")
@RequestMapping("/api/data/commons/dataStorageSchema")
public class DataStorageSchemaController
{
  /**
   * 业务服务接口
   */
  private DataStorageSchemaService service = DataCommonContext.getInstance().getDataStorageSchemaService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @since 0.1.0
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   *   // 名称
   *   name:""
   * }
   * </pre>
   *
   * @return 响应的数据内容
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    DataStorageSchema entity = JSON.parseObject(data, DataStorageSchema.class);

    this.service.save(entity);

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
   * <pre>{@code
   * {
   *   // 唯一标识
   *   id:""
   * }
   * }</pre>
   * @return 一个相关的实例详细信息.
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    DataStorageSchema entity = this.service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息.
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataQuery query = DataQuery.create(req.getString("query"));

    // 根据实际需要设置当前用户权限
    // query.getVariables().put("accountId", KernelContext.getCurrent().getUser().getId())

    /// if (req.getString("su") == "1" && AppsSecurity.IsAdministrator(KernelContext.getCurrent().getUser(), data-storagesConfiguration.ApplicationName))
    // {
    //   query.getVariables().put("elevatedPrivileges", "1");
    // }

    /// 根据实际需要设置查询参数
    // query.getWhere().put("Name", searchText);
    // query.setLength(length);

    List<DataStorageSchema> list = this.service.findAll(query);

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

    List<DataStorageSchema> list = this.service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/isExist")
  public String isExist(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    boolean result = this.service.isExist(id);

    return "{\"data\":" + JSON.toJSONString(result) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 唯一标识
   *   node:""
   * }
   * </pre>
   * @return 响应的数据内容
   */
  @RequestMapping("/create")
  public String create(@RequestBody String data){
    DataStorageSchema entity = JSON.parseObject(data, DataStorageSchema.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));

    /// 根据实际需要设置默认值
    // entity.setStatus(1);
    // entity.setModifiedDate(new Date());
    // entity.setCreatedDate(new Date());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_create_success"), true) + "}";
  }

  /**
   * 获取应用参数列表
   *
   * @param data 请求的数据内容
   * @return 返回参数列表
   */
  @RequestMapping("/getCombobox")
  public String getCombobox(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 必填字段
    String applicationId = req.getString("applicationId");

    //
    String combobox = req.getString("combobox");
    String selectedValue = req.getString("selectedValue");
    String emptyItemText = req.getString("emptyItemText");

    // 容错处理
    if (StringUtil.isNullOrEmpty(selectedValue)) {
      selectedValue = "-1";
    }

    StringBuilder outString = new StringBuilder();

    List<DataStorageSchema> list = this.service.getCombobox();

    outString.append("{\"data\":[");

    if (!StringUtil.isNullOrEmpty(emptyItemText)) {
      outString.append("{\"text\":\"" + emptyItemText + "\",\"value\":\"\"}" + ",");
    }

    for (DataStorageSchema item : list) {
      outString.append("{\"text\":\"" + item.getName() + "\",\"value\":\"" + item.getId() + "\",\"selected\":" + (
        (item.getId().equals(selectedValue)) ? 1 : 0) + "}" + ",");
    }

    if (",".equals(StringUtil.substring(outString.toString(), outString.length() - 1, 1))) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("],");

    outString.append("\"combobox\":\"" + combobox + "\",");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }
}
