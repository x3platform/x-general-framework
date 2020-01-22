package com.x3platform.digitalnumber.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.digitalnumber.models.DigitalNumber;
import com.x3platform.digitalnumber.services.DigitalNumberService;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/sys/digitalNumber")
public class DigitalNumberController {

  /**
   * 数据提供器
   */
  private DigitalNumberService service = DigitalNumberContext.getInstance().getDigitalNumberService();

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
    DigitalNumber entity = JSON.parseObject(data, DigitalNumber.class);

    service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
   * @return 响应的内容
   */
  @RequestMapping("/delete")
  public String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String name = req.getString("name");

    service.delete(name);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 根据名称获取相关流水号信息
   *
   * @param data 请求的数据内容
   * @return 一个 {@link DigitalNumber} 实例
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String name = req.getString("name");

    DigitalNumber param = service.findOne(name);

    return "{\"data\":" + JSON.toJSONString(param) + ","
      + MessageObject.stringify("0", "msg_query_success", true) + "}";
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

    List<DigitalNumber> list = service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 创建新的对象
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/create")
  public String create(@RequestBody String data) {
    DigitalNumber entity = new DigitalNumber();

    entity.setName("");
    entity.setCreatedDate(LocalDateTime.now());
    entity.setModifiedDate(LocalDateTime.now());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", "msg_create_success", true) + "}";
  }

  /**
   * 生成流水号
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/generate")
  public String generate(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String name = req.getString("name");

    String result = service.generate(name);

    return "{\"data\":\"" + result + "\","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
