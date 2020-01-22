package com.x3platform.membership.controllers;

import com.x3platform.KernelContext;
import com.x3platform.membership.*;
import com.x3platform.membership.services.*;

import com.x3platform.data.*;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.messages.MessageObject;
import com.x3platform.globalization.I18n;
import com.x3platform.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/membership/accountBinding")
public class AccountBindingController {

  /**
   * 业务服务接口
   */
  private AccountBindingService service = MembershipManagement.getInstance().getAccountBindingService();

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 获取详细信息
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 帐号唯一标识
   *   accountId:"",
   *   // 绑定类型
   *   bindingType:""
   * }
   * </pre>
   * @return 一个相关的实例详细信息
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String accountId = req.getString("accountId");
    String bindingType = req.getString("bindingType");

    AccountBinding entity = this.service.findOne(accountId, bindingType);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/findAllByAccountId")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String accountId = req.getString("accountId");

    List<AccountBinding> list = this.service.findAllByAccountId(accountId);

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 绑定第三方帐号绑定关系
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 帐号唯一标识
   *   accountId:"",
   *   // 绑定类型
   *   bindingType:"",
   *   // 绑定对象的标识
   *   bindingObjectId:"",
   *   // 绑定类型
   *   bindingOptions:""
   * }
   * </pre>
   * @return 响应的数据内容
   */
  @RequestMapping("/bind")
  public String bind(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String accountId = KernelContext.getCurrent().getUser().getId();

    String bindingType = req.getString("bindingType");
    String bindingObjectId = req.getString("bindingObjectId");
    String bindingOptions = req.getString("bindingOptions");

    int result = this.service.bind(accountId, bindingType, bindingObjectId, bindingOptions);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
  }

  /**
   * 绑定第三方帐号绑定关系
   *
   * @param data 请求的数据内容
   * <pre>
   * {
   *   // 帐号唯一标识
   *   accountId:"",
   *   // 绑定类型
   *   bindingType:"",
   *   // 绑定对象的标识
   *   bindingObjectId:""
   * }
   * </pre>
   * @return 响应的数据内容
   */
  @RequestMapping("/unbind")
  public String unbind(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String accountId = KernelContext.getCurrent().getUser().getId();

    String bindingType = req.getString("bindingType");
    String bindingObjectId = req.getString("bindingObjectId");

    int result = this.service.unbind(accountId, bindingType, bindingObjectId);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
  }
}
