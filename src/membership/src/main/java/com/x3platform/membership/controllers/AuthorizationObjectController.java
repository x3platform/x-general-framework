package com.x3platform.membership.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Group;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.services.AuthorizationObjectService;
import com.x3platform.membership.services.GroupService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权对象 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/membership/authorizationObject")
public class AuthorizationObjectController {

  /**
   * 业务服务接口
   */
  private AuthorizationObjectService service = MembershipManagement.getInstance().getAuthorizationObjectService();

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 获取实体对象和授权对象的授权关系
   *
   * @param data 请求的数据内容
   * @return 授权关系
   */
  @RequestMapping("/getAuthorizationScopeRelationByAuthorizationObjectId")
  public String getAuthorizationScopeRelationByAuthorizationObjectId(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String scopeTableName = req.getString("scopeTableName");
    String authorizationObjectType = req.getString("authorizationObjectType");
    String authorizationObjectId = req.getString("authorizationObjectId");
    String authorityName = req.getString("authorityName");

    List<Map> list = service.getAuthorizationScopeRelationByAuthorizationObjectId(StringUtil.toSafeSQL(scopeTableName),
      authorizationObjectType, authorizationObjectId, authorityName);

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
