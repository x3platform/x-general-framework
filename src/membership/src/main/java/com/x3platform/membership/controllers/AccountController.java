package com.x3platform.membership.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.KernelContext;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.AccountService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.IPQueryUtil;
import com.x3platform.util.StringUtil;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帐号 API 接口
 *
 * @author ruanyu
 */
@Lazy(true)
@RestController
@RequestMapping("/api/membership/account")
public class AccountController {

  /**
   * 帐号服务接口
   */
  AccountService service = MembershipManagement.getInstance().getAccountService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 新增账号
   *
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public final String save(@RequestBody String data) {
    // 采用点对点进行保持么
    AccountInfo entity = JSON.parseObject(data, AccountInfo.class);

    if (StringUtil.isNullOrEmpty(entity.getPassword())) {
      String defaultPassword = service.findDefaultPassword(null);
      // 设置 系统默认密码
      entity.setPassword(DigestUtils.sha1Hex(defaultPassword));
    }
    if (entity.getCreatedDate() == null) {
      entity.setCreatedDate(new Date());
    }
    entity.setModifiedDate(new Date());
    try {
      service.save(entity);
    } catch (Exception e) {
      KernelContext.getLog().error("账号保存出错" + e.getLocalizedMessage());
      return MessageObject.stringify("1", I18n.getStrings().text("msg_save_failed"));
    }
    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除账号， 逻辑删除
   *
   * @return 返回操作结果
   */
  @RequestMapping("/remove")
  public final String remove(@RequestBody String data) {
    List<Account> resultList = JSON.parseArray(data, Account.class);
    try {
      if (resultList != null && resultList.size() > 0) {
        for (Account Account : resultList) {
          service.setStatus(Account.getId(), Account.getCreatedBy(), 0);  // 状态标识, 1:启用, 0:禁用
        }
      }
      return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
    } catch (Exception e) {
      return MessageObject.stringify("1", I18n.getStrings().text("msg_save_failed"));
    }
  }

  /**
   * 由于真实姓名，存在 或者 使用显示名称进行控制
   */
  @RequestMapping("isExistDisplayName")
  public final String isExistDisplayName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String displayName = req.getString("displayName");
    String accountId = req.getString("accountId");
    boolean isExist = service.isExistDisplayName(accountId, displayName);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 由于真实姓名，存在 或者 使用显示名称进行控制
   */
  @RequestMapping("isExistCertifiedEmail")
  public final String isExistCertifiedEmail(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String certifiedEmail = req.getString("certifiedEmail");
    boolean isExist = service.isExistCertifiedEmail(accountId, certifiedEmail);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 登录名是否存在
   */
  @RequestMapping("isExistLoginName")
  public final String isExistLoginName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String loginName = req.getString("loginname");
    boolean isExist = service.isExistLoginName(accountId, loginName);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 身份证 是否存在
   */
  @RequestMapping("isExistIdentityCard")
  public final String isExistIdentityCard(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String identityCard = req.getString("identityCard");
    boolean isExist = service.isExistIdentityCard(accountId, identityCard);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";

  }

  /**
   * 电话号码是否存在
   */
  @RequestMapping("isExistCertifiedTelephone")
  public final String isExistCertifiedTelephone(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String certifiedTelephone = req.getString("certifiedTelephone");
    boolean isExist = service.isExistCertifiedMobile(accountId, certifiedTelephone);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 删除记录
   *
   * @param data 请求的内容数据
   * @return 响应的内容数据
   */
  public final String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    service.delete(id);

    return MessageObject.stringify("0", "msg_delete_success");
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 根据帐号标识查找帐号信息
   *
   * @param data 请求的内容数据
   * @return 响应的内容数据
   */
  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();
    JSONObject req = JSON.parseObject(data);
    // String name = XmlHelper.Fetch("name", doc);
    String id = req.getString("id");
    AccountInfo param = service.findOneById(id);
    outString.append("{\"data\":" + JSON.toJSONString(param) + ",");
    outString.append(MessageObject.stringify("0", "msg_query_success", true) + "}");
    return outString.toString();
  }

  /**
   * 根据登录名查找帐号信息
   *
   * @param data 请求的内容数据
   * @return 响应的内容数据
   */
  @RequestMapping("/findOneByLoginName")
  public final String findOneByLoginName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String loginName = req.getString("loginName");

    Account entity = service.findOneByLoginName(loginName);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", "msg_query_success", true) + "}";
  }

  /**
   * 查询对应接口下所属人员信息 ；
   *
   * @param data JSON 格式文本
   * @return 返回一个相关的实例列表.
   */
  @RequestMapping("/query")
  public final String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 查询信息
    DataQuery query = DataQuery.create(req.getString("query"));
    // 分页信息
    DataPaging paging = DataPagingUtil.create(req.getString("paging"));
    String global = req.getString("global");

    // 查询 ，所有
    // Object global = query.getMap().get("global");
    // 是否是全局，如果是全则，查看所有的， organization顶层设置为 0 ，不采用组织限制 ，
    // 设置 organizationUnitText 设置0 ；则默认查询
    // 判断当前是否为0 ， 不为0 的时候

    Object defaultOrgain = query.getWhere().get("organizationUnitText");

    if (defaultOrgain == null || defaultOrgain.equals("0")) {
      if (global != null && global.equalsIgnoreCase("true")) {
        query.getWhere().put("organizationUnitText", "0");
      } else {
        if (global == null || global.equals("false")) {
          Account account = KernelContext.getCurrent().getUser();
          // 查询当前 组织机构数据问题 ；
          List<OrganizationUnitInfo> organizationUnits = MembershipManagement.getInstance().getOrganizationUnitService()
            .findAllByAccountId(account.getId());
          if (organizationUnits.size() > 0) {
            OrganizationUnitInfo rootOrganization = MembershipManagement.getInstance().getOrganizationUnitService()
              .findCorporationByOrganizationUnitId(organizationUnits.get(0).getId());
            query.getWhere().put("organizationUnitText", rootOrganization.getId());
          }
        } else {
          query.getWhere().put("organizationUnitText", "0");
        }
      }
    }

    //  查询是否 global : false

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());
    // 判断是否为 默认管理员 ， 不是默认管理员，这按照顶层查询，进行顶层向下查询；
    List<AccountInfo> list = service.queryAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * @param data JSON 格式文本
   * @return 返回一个相关的实例列表.
   */
  @RequestMapping("/getCode")
  public final String getCode(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    Account accountInfo = service.findMaxCode(req);
    String result = StringUtil.numberToStr(Integer.parseInt(accountInfo.getCode()) + 1, "00000");
    return "{\"data\":" + result + ",\"paging\":" + JSON.toJSONString(accountInfo) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 组织机构获得所拥有的人员
   */
  @RequestMapping("/getOwnAccount")
  public final String getOwnAccount(@RequestBody String data) {
    // 查询当前组织或者当前组织下所有的
    JSONObject req = JSON.parseObject(data);
    String organizationUnitId = req.getString("organizationUnitId");
    List<Account> accountInfo = service.findAllAccountsByOrganization(organizationUnitId);
    return "{\"data\":" + accountInfo + ",\"paging\":" + JSON.toJSONString(accountInfo) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 重置密码
   *
   * @return 返回操作结果
   */
  @RequestMapping("/resetPassword")
  public final String resetPassword(@RequestBody String data) {
    JSONObject jsonObject = JSONObject.parseObject(data);
    // 登录名
    String loginName = jsonObject.getString("loginName");
    // 密码
    String password = jsonObject.getString("password");
    // 获取默认密码
    String defaultPassword = service.findDefaultPassword(null);
    defaultPassword = DigestUtils.sha1Hex(defaultPassword);
    try {
      service.changePassword(loginName, defaultPassword, password);
    } catch (Exception e) {
      KernelContext.getLog().error(e.toString());
      return MessageObject.stringify("1", I18n.getStrings().text("msg_save_failed"));
    }
    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 修改密码
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/changePassword ")
  public final String changePassword(@RequestBody String data) {
    JSONObject req = JSONObject.parseObject(data);

    String loginName = req.getString("loginName");
    String password = req.getString("password");
    String originalPassword = req.getString("originalPassword");

    Account account = MembershipManagement.getInstance().getAccountService().findOneByLoginName(loginName);

    Account operationAccount = KernelContext.getCurrent().getUser();

    if (account == null || operationAccount == null) {
      return "{message:{\"code\":1,\"value\":\"未检测到当前用户的合法信息。\"}}";
    }

    if (loginName.equals(operationAccount.getLoginName())) {
      try {
        int result = service.changePassword(loginName, password, originalPassword);

        if (result == 0) {
          // 记录帐号操作日志
          MembershipManagement.getInstance().getAccountLogService().log(operationAccount.getId(), "修改密码",
            "用户【" + operationAccount.getName() + "】修改密码成功。");

          return "{message:{\"code\":0,\"value\":\"修改成功。\"}}";
        } else {
          // 记录帐号操作日志
          MembershipManagement.getInstance().getAccountLogService().log(operationAccount.getId(), "修改密码",
            "用户【" + operationAccount.getName() + "】修改密码失败，【IP:" + IPQueryUtil.getClientIP() + "】。");

          return "{message:{\"code\":1,\"value\":\"修改失败, 用户或密码错误.\"}}";
        }
      } catch (Exception e) {
        KernelContext.getLog().error("修改密码出错" + e.getLocalizedMessage());
        return MessageObject.stringify("1", "修改失败。");
      }
    } else {
      // 记录帐号操作日志
      MembershipManagement.getInstance().getAccountLogService().log(account.getId(), "修改密码",
        "【" + operationAccount.getName() + "】尝试修改另一个用户【" + account.getName() + "】的密码，【IP:" + IPQueryUtil.getClientIP()
          + "】。", operationAccount.getId());

      return "{message:{\"code\":1,\"value\":\"修改失败, 用户或密码错误.\"}}";
    }
  }

  /**
   * 设置帐号锁定信息
   *
   * @param data 请求的内容数据
   * @return 响应的内容数据
   */
  @RequestMapping("/setLocking")
  public final String setLocking(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    int locking = req.getInteger("locking");
    try {
      // 1 解锁状态 ，0: 锁定
      service.setLocking(id, locking);
      return MessageObject.stringify("0", locking == 1 ? "成功解锁" : "成功锁定");
    } catch (Exception e) {
      e.printStackTrace();
      return MessageObject.stringify("1001", locking == 1 ? "成功失败" : "锁定失败");
    }
  }

  /**
   * 设置帐号状态信息
   *
   * @param data 请求的内容数据
   * @return 响应的内容数据
   */
  @RequestMapping("/setStatus")
  public final String setStatus(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 帐号标识
    String id = req.getString("id");
    // 状态信息
    int status = req.getInteger("status");

    int reuslt = service.setStatus(id, "0", status);

    return MessageObject.stringify("0", "msg_commit_success");
  }
}
