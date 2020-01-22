package com.x3platform;

import com.x3platform.util.StringUtil;

/**
 * 授权对象类型
 *
 * @author ruanyu
 */
public enum AuthorizationObjectType {

  /**
   * 帐号
   */
  ACCOUNT("Account"),

  /**
   * 组织单位
   */
  ORGANIZATION_UNIT("OrganizationUnit"),

  /**
   * 角色
   */
  ROLE("Role"),

  /**
   * 群组
   */
  GROUP("Group"),

  /**
   * 标准组织
   */
  STANDARD_ORGANIZATION_UNIT("StandardOrganizationUnit"),

  /**
   * 标准角色
   */
  STANDARD_ROLE("StandardRole"),

  /**
   * 职位信息
   */
  JOB("Job"),

  /**
   * 岗位信息
   */
  ASSIGNED_JOB("AssignedJob"),

  /**
   * 我的联系人
   */
  CONTACT("Contact"),

  /**
   * 未知的对象
   */
  UNKOWN("Unkown");

  // -------------------------------------------------------
  // 扩展信息
  // DEFAULT 默认 ACCOUNT ORGANIZATION_UNIT ROLE GROUP
  // ALL     全部 除 Unkown 外的所有对象
  // DEFAULT 和 ALL 在其他语言中可以通过 | 操作实现，
  // Java 枚举类型没有对应的操作需要手工实现。
  // -------------------------------------------------------

  /**
   * 构造方法
   */
  private AuthorizationObjectType(String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }

  public static String getValue(String text) {
    if (StringUtil.isNullOrEmpty(text)) {
      return "Unkown";
    }

    String value;

    text = text.toUpperCase();

    switch (text) {
      case "ACCOUNT":
        value = "ACCOUNT";
        break;
      case "ORGANIZATIONUNIT":
      case "ORGANIZATION_UNIT":
        value = "ORGANIZATION_UNIT";
        break;
      case "ROLE":
        value = "ROLE";
        break;
      case "GROUP":
        value = "GROUP";
        break;
      case "STANDARDORGANIZATIONUNIT":
      case "STANDARD_ORGANIZATION_UNIT":
        value = "STANDARD_ORGANIZATION_UNIT";
        break;
      case "STANDARDROLE":
      case "STANDARD_ROLE":
        value = "STANDARD_ROLE";
        break;
      case "JOB":
        value = "Job";
        break;
      case "ASSIGNEDJOB":
      case "ASSIGNED_JOB":
        value = "ASSIGNED_JOB";
        break;
      default:
        value = "Unkown";
        break;
    }

    AuthorizationObjectType entry = Enum.valueOf(AuthorizationObjectType.class, value.toUpperCase());

    return entry.getValue();
  }
}
