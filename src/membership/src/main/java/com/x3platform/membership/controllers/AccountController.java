package com.x3platform.membership.controllers;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.services.IAccountService;
import com.x3platform.membership.services.IOrganizationUnitService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x3platform.messages.MessageObject;
import com.x3platform.digitalNumber.DigitalNumberContext;
import com.x3platform.digitalNumber.models.*;
import com.x3platform.digitalNumber.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Lazy(true)
@RestController
@RequestMapping("/api/membership/account")
public class AccountController {
  // 数据提供器
  private IAccountService service = MembershipManagement.getInstance().getAccountService();

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

    IAccountInfo param = new AccountInfo();

    // param = AjaxUtil.<DigitalNumberInfo>Deserialize(param, doc);

    this.service.save(param);

    // return "{\"message\":{\"returnCode\":0,\"value\":\"查询成功。\"}}";
    return MessageObject.stringify("0", "msg_save_success");
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
   * @param data JSON 格式文本
   * @return 返回一个相关的实例列表.
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {

    StringBuilder outString = new StringBuilder();

    JSONObject req = JSON.parseObject(data);

    // String name = XmlHelper.Fetch("name", doc);
    String id = req.getString("id");

    IAccountInfo param = this.service.findOne(id);

    outString.append("{\"data\":" + JSON.toJSONString(param) + ",");
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
    List<DigitalNumberInfo> list = this.service.GetPaging(paging.RowIndex, paging.PageSize, paging.Query, tempRef_rowCount);
    rowCount = tempRef_rowCount.argValue;

    paging.RowCount = rowCount;

    outString.append("{\"data\":" + AjaxUtil.<DigitalNumberInfo>Parse(list) + ",");

    outString.append("\"paging\":" + paging + ",");

    outString.append(MessageObject.Stringify("0", I18n.Strings["msg_query_success"], true) + "}");

    return outString.toString();
  }*/

}
