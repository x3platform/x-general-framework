package com.x3platform.apps.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.KernelContext;
import com.x3platform.apps.models.ApplicationOption;
import com.x3platform.apps.services.ApplicationOptionService;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用选项 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.apps.controllers.ApplicationOptionController")
@RequestMapping("/api/apps/applicationOption")
public class ApplicationOptionController {

  @Autowired(required = false)
  private ApplicationOptionService service;

  /**
   * 查询所有系统共用节点
   */
//  @RequestMapping("applicationOption")
//  public String ApplicationOption(@RequestBody java.lang.String data) {
//    JSONObject req = JSON.parseObject(data);
//    String applicationKey = req.getString("applicationKey");
//
//    // 使用国际化模块，切换中英文或者 其他的消息 ; ,查询到其他参数，缺少参数;
//    if (StringUtil.isNullOrEmpty(applicationKey)) {
//      return MessageObject.stringify("1", I18n.getStrings().text("interface_not_params"));
//    } else {
//      List<ApplicationOption> dataList = service.getApplicationOption(applicationKey);
//      return "{\"data\":" + JSON.toJSONString(dataList) + ","
//        + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
//    }
//  }

  /**
   * 判断当前是否为系统登录员
   */
//  @RequestMapping("isAdmin")
//  public String isAdmin(@RequestBody java.lang.String data) {
//    boolean isAdmin = false;
//    JSONObject req = JSON.parseObject(data);
//    String applicationKey = req.getString("applicationKey");
//    // 使用国际化模块，切换中英文或者 其他的消息 ; ,查询到其他参数，缺少参数;
//    if (StringUtil.isNullOrEmpty(applicationKey)) {
//      return MessageObject.stringify("1", I18n.getStrings().text("interface_not_params"));
//    } else {
//      ApplicationOption optionInfo = service.getApplicationOption(applicationKey, "Apps.Administrators");
//      KernelContext.getLog().info("optionInfo---》" + optionInfo);
//      KernelContext.getLog().info("getUser---》" + JSON.toJSONString(KernelContext.getCurrent().getUser()));
//      String user = KernelContext.getCurrent().getUser().getLoginName();
//      if (StringUtil.isNullOrEmpty(user)) {
//        return MessageObject.stringify("1", I18n.getStrings().text("interface_not_params"));
//      } else {
//        if (optionInfo != null) {
//          String[] defalut = optionInfo.getValue().split("，");
//          for (int i = 0; i < defalut.length; i++) {
//            if (user.equalsIgnoreCase(defalut[i])) {
//              isAdmin = true;
//            }
//          }
//        }
//      }
//      return "{\"data\":" + JSON.toJSONString(isAdmin) + ","
//        + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
//    }
//  }
}
