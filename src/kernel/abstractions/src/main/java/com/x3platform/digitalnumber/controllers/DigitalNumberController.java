package com.x3platform.digitalnumber.controllers;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.globalization.I18n;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import X3Platform.Ajax.*;
// import X3Platform.Util.*;

// import X3Platform.Globalization.*;
// import X3Platform.Messages.*;

import com.x3platform.messages.MessageObject;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.digitalnumber.models.*;
import com.x3platform.digitalnumber.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ruanyu
 */
@RestController
@Lazy(true)
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
  public final String save(@RequestBody String data) {
    DigitalNumber entity = JSON.parseObject(data, DigitalNumber.class);

    this.service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
   * @return 响应的内容
   */
  public final String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String name = req.getString("name");

    this.service.delete(name);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
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

    DigitalNumber param = this.service.findOne(name);

    // outString.append("{\"data\":" + AjaxUtil.<DigitalNumber>Parse(param) + ",");

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
   * @return HTTP 响应内容
   */
  /*public final String GetPaging(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    PagingHelper paging = PagingHelper.Create(XmlHelper.Fetch("paging", doc, "xml"), XmlHelper.Fetch("query", doc, "xml"));

    int rowCount = 0;

    tangible.RefObject<Integer> tempRef_rowCount = new tangible.RefObject<Integer>(rowCount);
    List<DigitalNumber> list = this.service.GetPaging(paging.RowIndex, paging.PageSize, paging.Query, tempRef_rowCount);
    rowCount = tempRef_rowCount.argValue;

    paging.RowCount = rowCount;

    outString.append("{\"data\":" + AjaxUtil.<DigitalNumber>Parse(list) + ",");

    outString.append("\"paging\":" + paging + ",");

    outString.append(MessageObject.Stringify("0", I18n.Strings["msg_query_success"], true) + "}");

    return outString.toString();
  }*/

  /**
   * 创建新的对象
   *
   * @param doc Xml 文档对象
   * @return HTTP 响应内容
   */
  /*
  public final String CreateNewObject(HttpServletRequest req, HttpServletResponse res) {
    StringBuilder outString = new StringBuilder();

    DigitalNumber param = new DigitalNumber();

    param.setName("");

    param.setCreatedDate = param.ModifiedDate = java.time.LocalDateTime.now();

    outString.append("{\"data\":" + AjaxUtil.<DigitalNumber>Parse(param) + ",");

    outString.append(MessageObject.Stringify("0", I18n.Strings["msg_create_success"], true) + "}");

    return outString.toString();
  }*/

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

    String result = this.service.generate(name);

    return "{\"data\":" + result + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
