package com.x3platform.data.commons.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.data.commons.DataCommonContext;
import com.x3platform.data.commons.models.DataStorageNode;
import com.x3platform.data.commons.services.DataStorageNodeService;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.data.commons.controllers.DataStorageNodeController")
@RequestMapping("/api/data/commons/dataStorageNode")
public class DataStorageNodeController
{
  /**
   * 业务服务接口
   */
  private DataStorageNodeService service = DataCommonContext.getInstance().getDataStorageNodeService();

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
    DataStorageNode entity = JSON.parseObject(data, DataStorageNode.class);

    service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
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
   * @return 一个相关的实例详细信息.
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    DataStorageNode entity = service.findOne(id);

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

    List<DataStorageNode> list = service.findAll(query);

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

    List<DataStorageNode> list = service.findAll(query);

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
  public String create(@RequestBody String data){
    DataStorageNode entity = JSON.parseObject(data, DataStorageNode.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));

    /// 根据实际需要设置默认值
    // entity.setStatus(1);
    // entity.setModifiedDate(new Date());
    // entity.setCreatedDate(new Date());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_create_success"), true) + "}";
  }

  /**
   * 获取下拉列表信息
   *
   * @param data 请求的数据内容
   * @return 返回参数列表
   */
  @RequestMapping("/getCombobox")
  public String getCombobox(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String applicationId = req.getString("applicationId");
    // 可选字段
    String selectedValue = req.getString("selectedValue");
    String emptyItemText = req.getString("emptyItemText");

    // 容错处理
    if (StringUtil.isNullOrEmpty(selectedValue)) {
      selectedValue = "-1";
    }

    StringBuilder outString = new StringBuilder();

    List<DataStorageNode> list = service.getCombobox(applicationId);

    outString.append("{\"data\":[");

    if (!StringUtil.isNullOrEmpty(emptyItemText)) {
      outString.append("{\"text\":\"" + emptyItemText + "\",\"value\":\"-\"}" + ",");
    }

    for (DataStorageNode item : list) {
      outString.append("{\"text\":\"" + item.getName() + "\",\"value\":\"" + item.getId() + "\",\"selected\":" + (
        (item.getId().equals(selectedValue)) ? 1 : 0) + "}" + ",");
    }

    if (",".equals(StringUtil.substring(outString.toString(), outString.length() - 1, 1))) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("],");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }
}
