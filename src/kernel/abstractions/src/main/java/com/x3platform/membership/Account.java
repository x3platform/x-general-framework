package com.x3platform.membership;

import com.x3platform.AuthorizationObject;

import java.io.Serializable;
import java.util.*;

/**
 * 帐号信息
 *
 * @author ruanyu
 */
public interface Account extends AuthorizationObject, Serializable {

  /**
   * 标识
   */
  String getId();

  void setId(String value);

  /**
   * 编号
   */
  String getCode();

  void setCode(String value);

  /**
   * 姓名
   */
  String getName();

  void setName(String value);

  /**
   * 全局名称
   */
  String getGlobalName();

  /**
   * 显示名称
   */
  String getDisplayName();

  void setDisplayName(String value);

  /**
   * 拼音
   */
  String getPinYin();

  void setPinYin(String value);

  /**
   * 登录名
   */
  String getLoginName();

  void setLoginName(String value);

  /**
   * 密码更新时间
   */
  Date getPasswordChangedDate();

  void setPasswordChangedDate(Date value);

  /**
   * 身份证
   */
  String getIdentityCard();

  void setIdentityCard(String value);

  /**
   * 帐号类型 0:普通帐号 1:邮箱帐号 2:Rtx帐号 3:CRM帐号 1000:供应商帐号 2000:客户帐号
   */
  int getType();

  void setType(int value);

  /**
   * 已验证的手机号码
   */
  String getCertifiedMobile();

  void setCertifiedMobile(String value);

  /**
   * 已验证的邮箱
   */
  String getCertifiedEmail();

  void setCertifiedEmail(String value);

  /**
   * 已验证的头像路径
   */
  String getCertifiedAvatar();

  void setCertifiedAvatar(String value);

  /**
   * 已验证的头像虚拟路径
   */
  String getCertifiedAvatarView();

  /**
   * 启用企业邮箱
   */
  int getEnableEmail();

  void setEnableEmail(int value);

  /**
   * 修改人
   */
  String getCreatedBy();

  void  setCreatedBy(String value);
  /**
   * 排序标识
   */
  String getOrderId();

  void setOrderId(String value);

  /**
   * IP地址
   */
  String getIP();

  void setIP(String value);

  /**
   * 组织信息
   */
  List<AccountOrganizationUnitRelation> getOrganizationUnitRelations();

  /**
   * 角色集合
   */
  List<AccountRoleRelation> getRoleRelations();

  /**
   * 群组集合
   */
  List<AccountGroupRelation> getGroupRelations();

  /**
   * 登录时间
   */
  Date getLoginDate();

  void setLoginDate(Date value);

  /**
   * 唯一名称
   */
  String getDistinguishedName();

  void setDistinguishedName(String value);

  /**
   * 创建时间
   */
  Date getCreatedDate();

  void setCreatedDate(Date value);


}
