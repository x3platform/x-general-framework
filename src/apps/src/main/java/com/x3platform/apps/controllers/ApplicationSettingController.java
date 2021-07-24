package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationSetting;
import com.x3platform.apps.services.ApplicationSettingService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.Date;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 应用参数 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.apps.controllers.ApplicationSettingController")
@RequestMapping("/api/apps/applicationSetting")
public class ApplicationSettingController {

  private static String DIGITAL_NUMBER_KEY_CODE = "Table_Application_Setting_Key_Code";

  /**
   * 数据服务
   */
  private ApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) {
    ApplicationSetting entity = JSON.parseObject(data, ApplicationSetting.class);

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !service.isExist(entity.getId());

    if (isNewObject && StringUtil.isNullOrEmpty(entity.getCode())) {
      entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
    }

    this.service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().

      text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @return 操作结果
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
   * <pre>
   * {
   *   // 唯一标识
   *   id:""
   * }
   * </pre>
   * @return 返回一个相关的实例详细信息
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    ApplicationSetting entity = this.service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取分页内容
   *
   * @param data 请求的数据内容
   * @return 返回一个相关的实例列表信息
   */
  @RequestMapping("/query")
  public String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ApplicationSetting> list = service.findAll(query);

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
    ApplicationSetting entity = JSON.parseObject(data, ApplicationSetting.class);

    entity.setId(DigitalNumberContext.generate("Key_Guid"));

    // 根据实际需要设置默认值
    entity.setStatus(1);
    // entity.setModifiedDate(new Date());
    // entity.setCreatedDate(new Date());

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
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
    String applicationSettingGroupName = req.getString("applicationSettingGroupName");

    //
    String combobox = req.getString("combobox");
    String selectedValue = req.getString("selectedValue");
    String emptyItemText = req.getString("emptyItemText");

    // 容错处理
    if (StringUtil.isNullOrEmpty(selectedValue)) {
      selectedValue = "-1";
    }

    StringBuilder outString = new StringBuilder();

    List<ApplicationSetting> list = this.service.findAllByApplicationSettingGroupName(applicationSettingGroupName);

    outString.append("{\"data\":[");

    if (!StringUtil.isNullOrEmpty(emptyItemText)) {
      outString.append("{\"text\":\"" + emptyItemText + "\",\"value\":\"\"}" + ",");
    }

    for (ApplicationSetting item : list) {
      outString.append("{\"text\":\"" + item.getText() + "\",\"value\":\"" + item.getValue() + "\",\"selected\":" + (
        (item.getValue().equals(selectedValue)) ? 1 : 0) + "}" + ",");
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
