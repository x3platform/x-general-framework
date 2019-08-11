package com.x3platform.membership.controllers;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.models.OrganizationInfo;
import com.x3platform.membership.services.OrganizationService;
import com.x3platform.messages.MessageObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织 API 接口
 *
 * @author ruanyu
 */
@Lazy(true)
@RestController
@RequestMapping("/api/membership/organization")
public class OrganizationController {

  @Autowired
  OrganizationService organizationService;

  /**
   * @return 查询所有，数据控制；
   */
  @RequestMapping("/query")
  public final String findAll() {
    List<OrganizationInfo> organizationInfos = organizationService.findAll();
    return "{\"data\":" + JSON.toJSONString(organizationInfos) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 查询当前所属对应节点信息 ；
   *
   * @param data
   * @return
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    OrganizationInfo organizationInfo = organizationService.findOne(id);
    return "{\"data\":" + JSON.toJSONString(organizationInfo) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
