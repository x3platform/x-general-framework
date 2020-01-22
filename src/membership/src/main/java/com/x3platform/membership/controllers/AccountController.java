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
import com.x3platform.membership.User;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.services.AccountService;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.IPQueryUtil;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帐号 API 接口
 *
 * @author ruanyu
 */
@Lazy
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
   * 保存帐号
   *
   * @return 返回操作结果
   */
  @RequestMapping("/save")
  public String save(@RequestBody String data) throws Exception {
    JSONObject req = JSON.parseObject(data);

    Account entity = JSON.parseObject(data, AccountInfo.class);

    String originalName = req.getString("originalName");

    String originalGlobalName = req.getString("originalGlobalName");

    String organizationUnitText = req.getString("organizationUnitText");

    String roleText = req.getString("roleText");

    String groupText = req.getString("groupText");

    if (StringUtil.isNullOrEmpty(entity.getLoginName())) {
      return MessageObject.stringify(
        I18n.getExceptions().text("code_membership_login_name_is_required"),
        I18n.getExceptions().text("text_membership_login_name_is_required"));
    }

    if (StringUtil.isNullOrEmpty(entity.getId())) {
      // 新增
      if (service.isExistGlobalName(entity.getGlobalName())) {
        return MessageObject.stringify(
          I18n.getExceptions().text("code_membership_global_name_already_exists"),
          I18n.getExceptions().text("text_membership_global_name_already_exists"));
      }

      if (service.isExistLoginNameAndGlobalName(entity.getLoginName(), entity.getName())) {
        return MessageObject.stringify(
          I18n.getExceptions().text("code_membership_account_login_name_or_name_already_exists"),
          I18n.getExceptions().text("text_membership_account_login_name_or_name_already_exists"));
      }

      entity.setId(StringUtil.toUuid());
    } else {
      // 修改
      if (!originalGlobalName.equals(entity.getGlobalName())) {
        if (service.isExistGlobalName(entity.getGlobalName())) {
          return MessageObject.stringify(
            I18n.getExceptions().text("code_membership_global_name_already_exists"),
            I18n.getExceptions().text("text_membership_global_name_already_exists"));
        }
      }

      if (!originalName.equals(entity.getName())) {
        if (service.isExistName(entity.getName())) {
          return MessageObject.stringify(
            I18n.getExceptions().text("code_membership_account_name_already_exists"),
            I18n.getExceptions().text("text_membership_account_name_already_exists"));
        }
      }
    }

    entity.resetRelations("organizationUnit", organizationUnitText);
    entity.resetRelations("role", roleText);
    entity.resetRelations("group", groupText);

    // 读取原始对象信息
    Account originalObject = service.findOne(entity.getId());
    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = originalObject == null;

    service.save(entity);

    // 创建空的用户信息
    if (!StringUtil.isNullOrEmpty(entity.getId()) &&
      !MembershipManagement.getInstance().getUserService().isExist(entity.getId())) {
      User user = MembershipManagement.getInstance().getUserService().createEmptyUser(entity.getId());
      MembershipManagement.getInstance().getUserService().save(user);
    }

    // 如果没有默认，则设置以排在第一个角色为默认角色。
    if (!MembershipManagement.getInstance().getRoleService().hasDefaultRelation(entity.getId())) {
      if (!entity.getRoleRelations().isEmpty()) {
        MembershipManagement.getInstance().getUserService().setDefaultRole(entity.getId(),
          entity.getRoleRelations().get(0).getRoleId());
      }
    }

    // 记录帐号操作日志
    String operationName = isNewObject ? "新建" : "编辑";

    Account operationAccount = KernelContext.getCurrent().getUser();

    String description = "[" + operationAccount.getName() + "] " + operationName + "了帐号 [" + entity.getName() + "]。";

    if (operationName.equals(I18n.getStrings().text("text_edit"))) {
      description = "[" + operationAccount.getName() + "] 编辑了用户【" + entity.getName() + "】的帐号信息。";
    }

    MembershipManagement.getInstance().getAccountLogService().log(entity.getId(), operationName, description,
      operationAccount.getId());

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  /**
   * 删除账号， 逻辑删除
   *
   * @return 返回操作结果
   */
  @RequestMapping("/remove")
  public String remove(@RequestBody String data) {
    List<Account> resultList = JSON.parseArray(data, Account.class);

    if (resultList != null && resultList.size() > 0) {
      for (Account Account : resultList) {
        // 状态标识, 1:启用, 0:禁用
        service.setStatus(Account.getId(), 0);
      }
    }

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  /**
   * 删除记录
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/delete")
  public String delete(@RequestBody String data) {
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
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    Account entity = service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", "msg_query_success", true) + "}";
  }

  /**
   * 根据登录名查找帐号信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/findOneByLoginName")
  public String findOneByLoginName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String loginName = req.getString("loginName");

    Account entity = service.findOneByLoginName(loginName);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", "msg_query_success", true) + "}";
  }

  /**
   * 查询对应接口下所属帐号信息
   *
   * @param data JSON 格式文本
   * @return 返回一个相关的实例列表.
   */
  @RequestMapping("/query")
  public String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 查询信息
    DataQuery query = DataQuery.create(req.getString("query"));
    // 分页信息
    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<Account> list = service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 登录名是否存在
   */
  @RequestMapping("isExistLoginName")
  public String isExistLoginName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String loginName = req.getString("loginName");
    boolean isExist = service.isExistLoginName(accountId, loginName);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 由于真实姓名，存在 或者 使用显示名称进行控制
   */
  @RequestMapping("isExistDisplayName")
  public String isExistDisplayName(@RequestBody String data) {
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
  public String isExistCertifiedEmail(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String certifiedEmail = req.getString("certifiedEmail");
    boolean isExist = service.isExistCertifiedEmail(accountId, certifiedEmail);
    return "{\"data\":" + isExist + "," + MessageObject
      .stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 身份证 是否存在
   */
  @RequestMapping("isExistIdentityCard")
  public String isExistIdentityCard(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String identityCard = req.getString("identityCard");
    boolean isExist = service.isExistIdentityCard(accountId, identityCard);
    return "{\"data\":" + isExist + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 电话号码是否存在
   */
  @RequestMapping("isExistCertifiedMobile")
  public String isExistCertifiedTelephone(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String accountId = req.getString("accountId");
    String certifiedMobile = req.getString("certifiedMobile");
    boolean isExist = service.isExistCertifiedMobile(accountId, certifiedMobile);
    return "{\"data\":" + isExist + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * @param data JSON 格式文本
   * @return 返回一个相关的实例列表.
   */
//  @RequestMapping("/getCode")
//  public String getCode(@RequestBody String data) {
//    JSONObject req = JSON.parseObject(data);
//    Account accountInfo = service.findMaxCode(req);
//    String result = StringUtil.toNumber(Integer.parseInt(accountInfo.getCode()) + 1, "00000");
//    return "{\"data\":" + result + ",\"paging\":" + JSON.toJSONString(accountInfo) + ","
//      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
//  }

  /**
   * 组织机构获得所拥有的人员
   */
  @RequestMapping("/getOwnAccount")
  public String getOwnAccount(@RequestBody String data) {
    // 查询当前组织或者当前组织下所有的
    JSONObject req = JSON.parseObject(data);
    String organizationUnitId = req.getString("organizationUnitId");
    List<Account> accountInfo = service.findAllAccountsByOrganization(organizationUnitId);
    return "{\"data\":" + accountInfo + ",\"paging\":" + JSON.toJSONString(accountInfo) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 设置密码
   *
   * @return 操作结果
   */
  @RequestMapping("/setPassword")
  public String setPassword(@RequestBody String data) {
    JSONObject jsonObject = JSONObject.parseObject(data);
    // 帐号标识
    String id = jsonObject.getString("id");
    // 密码
    String password = jsonObject.getString("password");

    Account account = MembershipManagement.getInstance().getAccountService().findOne(id);

    Account optionAccount = KernelContext.getCurrent().getUser();

    if (account == null || optionAccount == null) {
      return MessageObject.stringify("1", "未检测到当前用户的合法信息。");
    }

    service.setPassword(id, password);

    // 记录帐号操作日志
    MembershipManagement.getInstance().getAccountLogService().log(account.getId(), "设置密码",
      StringUtil.format("【{}】重新设置了用户【{}】的密码，【IP:{}】。",
        optionAccount.getName(), account.getName(), IPQueryUtil.getClientIP()),
      optionAccount.getId());

    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
  }

  /**
   * 修改密码
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/changePassword")
  public String changePassword(@RequestBody String data) {
    JSONObject req = JSONObject.parseObject(data);

    String loginName = req.getString("loginName");
    String password = req.getString("password");
    String originalPassword = req.getString("originalPassword");

    Account account = MembershipManagement.getInstance().getAccountService().findOneByLoginName(loginName);

    Account operationAccount = KernelContext.getCurrent().getUser();

    if (account == null || operationAccount == null) {
      return MessageObject.stringify(I18n.getExceptions().text("code_membership_user_info_not_found"),
        I18n.getExceptions().text("text_membership_user_info_not_found"));
    }

    if (loginName.equals(operationAccount.getLoginName())) {
      int result = service.changePassword(loginName, password, originalPassword);

      if (result == 0) {
        // 记录帐号操作日志
        MembershipManagement.getInstance().getAccountLogService().log(operationAccount.getId(), "修改密码",
          StringUtil.format("用户【{}】修改密码成功。", operationAccount.getName()));

        return MessageObject.stringify("0", I18n.getStrings().text("msg_modify_success"));
      } else {
        // 记录帐号操作日志
        MembershipManagement.getInstance().getAccountLogService().log(operationAccount.getId(), "修改密码",
          StringUtil.format("用户【{}】修改密码失败，【IP:{}】。",
            operationAccount.getName(), IPQueryUtil.getClientIP()));

        return MessageObject.stringify(I18n.getExceptions().text("code_membership_user_or_password_is_incorrect"),
          I18n.getExceptions().text("text_membership_user_or_password_is_incorrect"));
      }
    } else {
      // 记录帐号操作日志
      MembershipManagement.getInstance().getAccountLogService().log(account.getId(), "修改密码",
        StringUtil.format("【{}】尝试修改另一个用户【{}】的密码，【IP:{}】。",
          operationAccount.getName(),
          account.getName(),
          IPQueryUtil.getClientIP())
        , operationAccount.getId());

      return MessageObject.stringify(I18n.getExceptions().text("code_membership_user_or_password_is_incorrect"),
        I18n.getExceptions().text("text_membership_user_or_password_is_incorrect"));
    }
  }

  /**
   * 设置帐号锁定信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setLoginName")
  public String setLoginName(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    String loginName = req.getString("loginName");

    Account account = MembershipManagement.getInstance().getAccountService().findOne(id);

    Account optionAccount = KernelContext.getCurrent().getUser();

    if (account == null || optionAccount == null) {
      return MessageObject.stringify(I18n.getExceptions().text("code_membership_user_info_not_found"),
        I18n.getExceptions().text("text_membership_user_info_not_found"));
    }

    if (StringUtil.isNullOrEmpty(loginName)) {
      return MessageObject.stringify(I18n.getExceptions().text("code_membership_login_name_is_required"),
        I18n.getExceptions().text("text_membership_login_name_is_required"));
    } else if (!service.isExistLoginName(id, loginName)) {
      service.setLoginName(id, loginName);
      // 记录帐号操作日志
      MembershipManagement.getInstance().getAccountLogService().log(account.getId(), "设置登录名",
        StringUtil.format("【{}】将用户【{}】的登录名设置为【{}】，【IP:{}】。",
          optionAccount.getName(),
          account.getName(),
          loginName,
          IPQueryUtil.getClientIP()),
        optionAccount.getId());

      return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
    } else {
      return MessageObject.stringify(I18n.getExceptions().text("code_membership_login_name_already_exists"),
        I18n.getExceptions().text("text_membership_login_name_already_exists"));
    }
  }

  /**
   * 设置帐号锁定信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setLocking")
  public String setLocking(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    String id = req.getString("id");
    int locking = req.getInteger("locking");

    // locking 1 解锁状态 ，0: 锁定
    service.setLocking(id, locking);
    return MessageObject.stringify("0", I18n.getStrings().text("msg_setup_success"));
  }

  /**
   * 设置帐号状态信息
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/setStatus")
  public String setStatus(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    // 帐号标识
    String id = req.getString("id");
    // 状态信息
    int status = req.getInteger("status");

    int reuslt = service.setStatus(id, status);

    return MessageObject.stringify("0", "msg_commit_success");
  }
}
