package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationSetting;
import com.x3platform.apps.services.ApplicationSettingService;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 */
@Lazy(true)
@RestController
@RequestMapping("/api/apps/applicationSetting")
public class ApplicationSettingController {
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
  public final String save(HttpServletRequest req, HttpServletResponse res) {
    ApplicationSetting param = new ApplicationSetting();
    // param = AjaxUtil.<ApplicationSetting>Deserialize(param, doc);
    this.service.save(param);

    return "{\"message\":{\"returnCode\":0,\"value\":\"查询成功。\"}}";
  }

  /**
   * 删除记录
   *
   * @param doc Xml 文档对象
   * @return 返回操作结果
   */
  public final String delete(HttpServletRequest req, HttpServletResponse res) {
    // String name = XmlHelper.Fetch("name", doc);
    String name = req.getParameter("name");

    this.service.delete(name);

    // return MessageObject.Stringify("0", I18n.Strings["msg_delete_success"]);
    return MessageObject.stringify("0", "msg_delete_success");
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 获取分页内容 / get paging.
   *
   * @param doc Xml 文档对象
   * @return 返回一个相关的实例列表.
   */
  public final String findOne(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    // String name = XmlHelper.Fetch("name", doc);
    String name = req.getParameter("name");

    ApplicationSetting param = this.service.findOne(name);

    // outString.append("{\"data\":" + AjaxUtil.<ApplicationSetting>Parse(param) + ",");

    // outString.append(MessageObject.Stringify("0", I18n.Strings["msg_query_success"], true) + "}");
    outString.append(MessageObject.stringify("0", "msg_query_success", true) + "}");

    return outString.toString();
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 获取分页内容
   *
   * @param doc Xml 文档对象
   * @return 返回操作结果
   */
  /*public final String GetPaging(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    PagingHelper paging = PagingHelper.Create(XmlHelper.Fetch("paging", doc, "xml"), XmlHelper.Fetch("query", doc, "xml"));

    int rowCount = 0;

    tangible.RefObject<Integer> tempRef_rowCount = new tangible.RefObject<Integer>(rowCount);
    List<ApplicationSetting> list = this.service.GetPaging(paging.RowIndex, paging.PageSize, paging.Query, tempRef_rowCount);
    rowCount = tempRef_rowCount.argValue;

    paging.RowCount = rowCount;

    outString.append("{\"data\":" + AjaxUtil.<ApplicationSetting>Parse(list) + ",");

    outString.append("\"paging\":" + paging + ",");

    outString.append(MessageObject.Stringify("0", I18n.Strings["msg_query_success"], true) + "}");

    return outString.toString();
  }*/

  /**
   * 创建新的对象
   *
   * @param doc Xml 文档对象
   * @return 返回操作结果
   */
  /*
  public final String CreateNewObject(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    ApplicationSetting param = new ApplicationSetting();

    param.setName("");

    param.setCreatedDate = param.ModifiedDate = java.time.LocalDateTime.now();

    outString.append("{\"data\":" + AjaxUtil.<ApplicationSetting>Parse(param) + ",");

    outString.append(MessageObject.Stringify("0", I18n.Strings["msg_create_success"], true) + "}");

    return outString.toString();
  }*/

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

    // whereClause = " ApplicationSettingGroupId IN ( SELECT Id FROM tb_Application_SettingGroup WHERE Name = ##" + applicationSettingGroupName + "## ) ";

    // if (whereClause.toUpperCase().indexOf(" Status ") == -1) {
    //  // 只读取启用状态的数据
    //      whereClause = " Status = 1 AND " + whereClause;
    //    }
    //
    //    if (whereClause.toUpperCase().indexOf("ORDER BY") == -1) {
    //      whereClause = whereClause + " ORDER BY OrderId ";
    //    }

    StringBuilder outString = new StringBuilder();

    List<ApplicationSetting> list = this.service.findAllByApplicationSettingGroupName(applicationSettingGroupName);

    outString.append("{\"data\":[");

    if (!StringUtil.isNullOrEmpty(emptyItemText)) {
      outString.append("{\"text\":\"" + emptyItemText + "\",\"value\":\"\"}" + ",");
    }

    for (ApplicationSetting item : list) {
      outString.append("{\"text\":\"" + item.getText() + "\",\"value\":\"" + item.getValue() + "\",\"selected\":" + ((item.getValue().equals(selectedValue)) ? 1 : 0) + "}" + ",");
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(",")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("],");

    outString.append("\"combobox\":\"" + combobox + "\",");

    outString.append(MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");

    return outString.toString();
  }
}
