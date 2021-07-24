package com.x3platform.membership.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.membership.Account;
import com.x3platform.membership.AccountGroupRelation;
import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.membership.User;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.*;
import org.dom4j.Element;

/**
 * 用户
 */
public class UserInfo implements User {

  /**
   */
  public UserInfo() {
  }

  /**
   */
  public UserInfo(String id, String accountId) {
    this();
    this.setId(id);
    this.setAccountId(accountId);
  }

  // -------------------------------------------------------
  // 具体属性
  // -------------------------------------------------------

  private String id;

  /**
   * 用户标识
   */
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String value) {
    id = value;
  }

  private String fullName = "";

  /**
   * 姓名
   */
  @Override
  public String getName() {
    return this.getAccount() == null ? fullName : this.getAccount().getName();
  }

  @Override
  public void setName(String value) {
    if (this.getAccount() == null) {
      fullName = value;
    } else {
      this.getAccount().setName(value);
    }
  }

  private Account account = null;

  /**
   * 帐号
   */
  @JSONField(serialize = false)
  @Override
  public Account getAccount() {
    if (account == null) {
      account = MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
    }

    return account;
  }
  
  @Override
  public void setAccount(Account value) {
    account = value;
  }

  private String accountId = "";

  /**
   * 帐户标识
   */
  @Override
  public String getAccountId() {
    return accountId;
  }
  
  @Override
  public void setAccountId(String value) {
    accountId = value;
  }

  /**
   * 帐号名称
   */
  @Override
  public String getAccountName() {
    return this.getAccount() == null ? "" : this.getAccount().getName();
  }

  private OrganizationUnit corporation = null;

  /**
   * 公司
   */
  @JSONField(serialize = false)
  public OrganizationUnit getCorporation() {
    if (corporation == null && !StringUtil.isNullOrEmpty(corporationId)) {
      corporation = MembershipManagement.getInstance().getOrganizationUnitService().findOne(corporationId);
    }

    return corporation;
  }

  private String corporationId = null;

  /**
   * 公司标识
   */
  @Override
  public String getCorporationId() {
    return corporationId;
  }
  
  @Override
  public void setCorporationId(String value) {
    corporationId = value;
  }

  /**
   * 公司名称
   */
  @Override
  public String getCorporationName() {
    return this.getCorporation() == null ? "" : this.getCorporation().getName();
  }

  private String departmentId = null;

  /**
   * 部门标识
   */
  @Override
  public String getDepartmentId() {
    return departmentId;
  }
  
  @Override
  public void setDepartmentId(String value) {
    departmentId = value;
  }

  /**
   * 一级部门名称
   */
  @Override
  public String getDepartmentName() {
    return this.getDepartment() == null ? "" : this.getDepartment().getName();
  }

  private OrganizationUnit department = null;

  /**
   * 一级部门
   */
  @JSONField(serialize = false)
  public OrganizationUnit getDepartment() {
    if (department == null && !StringUtil.isNullOrEmpty(departmentId)) {
      department = MembershipManagement.getInstance().getOrganizationUnitService().findOne(departmentId);
    }

    return department;
  }

  private String department2Id = null;

  /**
   * 二级部门标识
   */
  @Override
  public String getDepartment2Id() {
    return department2Id;
  }
  
  @Override
  public void setDepartment2Id(String value) {
    department2Id = value;
  }

  /**
   * 二级部门名称
   */
  @Override
  public String getDepartment2Name() {
    return this.getDepartment2() == null ? "" : this.getDepartment2().getName();
  }

  private OrganizationUnit department2 = null;

  /**
   * 二级部门
   */
  @JSONField(serialize = false)
  public OrganizationUnit getDepartment2() {
    if (department2 == null && !StringUtil.isNullOrEmpty(this.getDepartment2Id())) {
      department2 = MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getDepartment2Id());
    }

    return department2;
  }

  private String department3Id = null;

  /**
   * 三级部门标识
   */
  @Override
  public String getDepartment3Id() {
    return department3Id;
  }
  
  @Override
  public void setDepartment3Id(String value) {
    department3Id = value;
  }

  /**
   * 三级部门名称
   */
  @Override
  public String getDepartment3Name() {
    return this.getDepartment3() == null ? "" : this.getDepartment3().getName();
  }

  private OrganizationUnit department3 = null;

  /**
   * 三级部门
   */
  @JSONField(serialize = false)
  public OrganizationUnit getDepartment3() {
    if (department3 == null && !StringUtil.isNullOrEmpty(this.getDepartment3Id())) {
      department3 = MembershipManagement.getInstance().getOrganizationUnitService()
        .findOne(this.getDepartment3Id());
    }

    return department3;
  }

  private String organizationUnitId = null;

  /**
   * 默认的组织单位标识
   */
  @Override
  public String getOrganizationUnitId() {
    if (StringUtil.isNullOrEmpty(this.organizationUnitId)) {
      this.organizationUnitId = MembershipConfigurationView.getInstance().getDefaultOrganizationId();
    }

    return organizationUnitId;
  }
  
  @Override
  public void setOrganizationUnitId(String value) {
    organizationUnitId = value;
  }

  private OrganizationUnit organizationUnit = null;

  /**
   * 默认的组织单位
   */
  @JSONField(serialize = false)
  public OrganizationUnit getOrganizationUnit() {
    if (organizationUnit == null && !StringUtil.isNullOrEmpty(this.getOrganizationUnitId())) {
      organizationUnit = MembershipManagement.getInstance().getOrganizationUnitService()
        .findOne(this.getOrganizationUnitId());
    }

    return organizationUnit;
  }

  private String organizationPath = "";

  /**
   * 默认的组织路径
   */
  @Override
  public String getOrganizationPath() {
    if (StringUtil.isNullOrEmpty(this.organizationPath) && !StringUtil
      .isNullOrEmpty(this.getCorporationId())) {
      this.organizationPath = this.getCorporationName();

      if (this.getDepartment() != null) {
        this.organizationPath += "\\" + this.getDepartment().getName();
      }

      if (this.getDepartment2() != null) {
        this.organizationPath += "\\" + this.getDepartment2().getName();
      }

      if (this.getDepartment3() != null) {
        this.organizationPath += "\\" + this.getDepartment3().getName();
      }
    }

    return organizationPath;
  }

  private String roleId = null;

  /**
   * 默认的角色标识
   */
  @Override
  public String getRoleId() {
    return roleId;
  }
  
  @Override
  public void setRoleId(String value) {
    roleId = value;
  }

  /**
   * 默认角色名称
   */
  @Override
  public String getRoleName() {
    return this.getRole() == null ? "" : this.getRole().getName();
  }

  private Role role = null;

  /**
   * 默认的角色
   */
  @JSONField(serialize = false)
  public Role getRole() {
    if (role == null && !StringUtil.isNullOrEmpty(this.getRoleId())) {
      role = MembershipManagement.getInstance().getRoleService().findOne(this.getRoleId());
    }

    return role;
  }

  private String headship = "";

  /**
   * 职务 | 头衔
   */
  @Override
  public String getHeadship() {
    return headship;
  }
  
  @Override
  public void setHeadship(String value) {
    headship = value;
  }

  private String gender;

  /**
   * 性别
   */
  @Override
  public String getGender() {
    if (StringUtil.isNullOrEmpty(gender)) {
      gender = "男";
    }

    return gender;
  }
  
  @Override
  public void setGender(String value) {
    gender = value;
  }

  private LocalDateTime birthday = DateUtil.getDefaultLocalDateTime();

  /**
   * 生日
   */
  @Override
  public LocalDateTime getBirthday() {
    return birthday;
  }
  
  @Override
  public void setBirthday(LocalDateTime value) {
    birthday = value;
  }

  private LocalDateTime graduationDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 毕业时间
   */
  @Override
  public LocalDateTime getGraduationDate() {
    return this.graduationDate;
  }
  
  @Override
  public void setGraduationDate(LocalDateTime value) {
    this.graduationDate = value;
  }

  private LocalDateTime entryDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 入职时间
   */
  @Override
  public LocalDateTime getEntryDate() {
    return entryDate;
  }
  
  @Override
  public void setEntryDate(LocalDateTime value) {
    entryDate = value;
  }

  private LocalDateTime promotionDate = LocalDateTime.MIN;

  /**
   * 最近一次晋升时间，如果刚入职则等于入职时间
   */
  @Override
  public LocalDateTime getPromotionDate() {
    if (LocalDateTime.MIN.equals(this.promotionDate)) {
      this.promotionDate = this.getEntryDate();
    }

    return this.promotionDate;
  }
  
  @Override
  public void setPromotionDate(LocalDateTime value) {
    this.promotionDate = value;
  }

  private String city;

  /**
   * 居住城市
   */
  @Override
  public String getCity() {
    return city;
  }
  
  @Override
  public void setCity(String value) {
    city = value;
  }

  private String fullPath = null;

  /**
   * 所属组织架构全路径
   */
  @Override
  public String getFullPath() {
    return fullPath;
  }
  
  @Override
  public void setFullPath(String value) {
    fullPath = value;
  }

  private LocalDateTime modifiedDate = LocalDateTime.MIN;

  /**
   * 修改时间
   */
  @Override
  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }
  
  @Override
  public void setModifiedDate(LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = LocalDateTime.MIN;

  /**
   * 创建时间
   */
  @Override
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }
  
  @Override
  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 实现 ISerializedObject 序列化
  // -------------------------------------------------------

  /**
   * 根据对象导出Xml元素
   */
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment 显示注释
   * @param displayFriendlyName 显示友好名称
   */
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    if (displayComment) {
      outString.append("<!-- 用户对象 -->");
    }
    outString.append("<user>");
    if (displayComment) {
      outString.append("<!-- 用户标识 (字符串) (varchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    if (displayComment) {
      outString.append("<!-- 用户编号 (字符串) (nvarchar(30)) -->");
    }
    outString
      .append(String.format("<code><![CDATA[%1$s]]></code>", this.getAccount() == null ? "" : this.getAccount().getCode()));
    if (displayComment) {
      outString.append("<!-- 登录名 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String
      .format("<loginName><![CDATA[%1$s]]></loginName>", this.getAccount() == null ? "" : this.getAccount().getLoginName()));
    if (displayComment) {
      outString.append("<!-- 名称 (字符串) (nvarchar(50)) -->");
    }
    outString
      .append(String.format("<name><![CDATA[%1$s]]></name>", this.getAccount() == null ? "" : this.getAccount().getName()));
    if (displayComment) {
      outString.append("<!-- 全局名称 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<globalName><![CDATA[%1$s]]></globalName>",
      this.getAccount() == null ? "" : this.getAccount().getGlobalName()));
    if (displayComment) {
      outString.append("<!-- 显示名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(
      String.format("<alias><![CDATA[%1$s]]></alias>", this.getAccount() == null ? "" : this.getAccount().getDisplayName()));
    if (displayComment) {
      outString.append("<!-- 拼音 (字符串) (nvarchar(100)) -->");
    }
    outString.append(
      String
        .format("<pinyin><![CDATA[%1$s]]></pinyin>", this.getAccount() == null ? "" : this.getAccount().getPinYin()));
    if (displayComment) {
      outString.append("<!-- 默认所属公司标识 (字符串) (varchar(36)) -->");
    }
    outString.append(String.format("<corporationId><![CDATA[%1$s]]></corporationId>", this.getCorporationId()));
    if (displayComment) {
      outString.append("<!-- 默认所属部门标识 (字符串) (varchar(36)) -->");
    }
    outString.append(String.format("<departmentId><![CDATA[%1$s]]></departmentId>", this.getDepartmentId()));
    if (displayComment) {
      outString.append("<!-- 默认所属二级部门标识 (字符串) (varchar(36)) -->");
    }
    outString.append(String.format("<department2Id><![CDATA[%1$s]]></department2Id>", this.getDepartment2Id()));
    if (displayComment) {
      outString.append("<!-- 默认所属三级部门标识 (字符串) (varchar(36)) -->");
    }
    outString.append(String.format("<department3Id><![CDATA[%1$s]]></department3Id>", this.getDepartment3Id()));
    if (displayComment) {
      outString.append("<!-- 默认所属最末级组织标识 (字符串) (varchar(36)) -->");
    }
    outString
      .append(String.format("<organizationUnitId><![CDATA[%1$s]]></organizationUnitId>", this.getOrganizationUnitId()));
    if (displayComment) {
      outString.append("<!-- 默认所属角色 (字符串) (varchar(36)) -->");
    }
    outString.append(String.format("<roleId><![CDATA[%1$s]]></roleId>", this.getRoleId()));
    if (displayComment) {
      outString.append("<!-- 岗位头衔/职务 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<headship><![CDATA[%1$s]]></headship>", this.getHeadship()));
    if (displayComment) {
      outString.append("<!-- 性别 (字符串) (nvarchar(4)) -->");
    }
    outString.append(String.format("<gender><![CDATA[%1$s]]></gender>", this.getGender()));
    if (displayComment) {
      outString.append("<!-- 生日 (时间) (datetime) -->");
    }
    outString.append(String.format("<birthday><![CDATA[%1$s]]></birthday>", this.getBirthday()));
    if (displayComment) {
      outString.append("<!-- 毕业时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<graduateDate><![CDATA[%1$s]]></graduateDate>", this.getGraduationDate()));
    if (displayComment) {
      outString.append("<!-- 入职时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<entryDate><![CDATA[%1$s]]></entryDate>", this.getEntryDate()));
    if (displayComment) {
      outString.append("<!-- 晋升时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<promotionDate><![CDATA[%1$s]]></promotionDate>", this.getPromotionDate()));
    if (displayComment) {
      outString.append("<!-- 兼职信息 -->");
    }
    outString.append("<partTimeJobs>");

    if (displayComment) {
      outString.append("<!-- 关联对象-->");
    }
    outString.append("<relationObjects>");
    if (this.getAccount() != null) { //foreach (OrganizationUnit organization in this.Account.OrganizationUnits)
      //{
      //    outString.AppendFormat("<relationObject id=\"{0}\" type=\"OrganizationUnit\" />", organization.Id);
      //}
      for (AccountRoleRelation relation : this.getAccount().getRoleRelations()) {
        if (displayComment) {
          outString.append("<!-- 所属角色标识 (字符串) (varchar(36)) -->");
        }

        outString.append(String.format("<relationObject id=\"%1$s\" type=\"Role\" />", relation.getRoleId()));
      }
      for (AccountGroupRelation relation : this.getAccount().getGroupRelations()) {
        if (displayComment) {
          outString.append("<!-- 所属群组标识(兼容门户系统，可以忽略。) (字符串) (varchar(36)) -->");
        }

        outString.append(String.format("<relationObject id=\"%1$s\" type=\"Group\" />", relation.getGroupId()));
      }
    }
    outString.append("</relationObjects>");
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(
      String.format("<status><![CDATA[%1$s]]></status>", this.getAccount() == null ? 0 : this.getAccount().getStatus()));
    if (displayComment) {
      outString.append("<!-- 最后更新时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<modifiedDate><![CDATA[%1$s]]></modifiedDate>", this.getModifiedDate()));
    outString.append("</user>");

    return outString.toString();
  }

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  public void deserialize(Element element) {
    if (this.getAccount() == null) {
      account = new AccountInfo();
    }

    this.getAccount().deserialize(element);

//    this.setId(this.setAccountId(this.getAccount().Id = element.SelectSingleNode("id").InnerText));
//
//    this.setFullName(element.SelectSingleNode("name").InnerText);
//    this.setCorporationId(element.SelectSingleNode("corporationId").InnerText);
//    this.setDepartmentId(element.SelectSingleNode("departmentId").InnerText);
//
//    if (element.SelectSingleNode("department2Id") != null) {
//      this.setDepartment2Id(element.SelectSingleNode("department2Id").InnerText);
//    }
//
//    if (element.SelectSingleNode("department3Id") != null) {
//      this.setDepartment3Id(element.SelectSingleNode("department3Id").InnerText);
//    }
//
//    this.setOrganizationUnitId(element.SelectSingleNode("organizationId").InnerText);
//
//    if (element.SelectSingleNode("roleId") != null) {
//      this.setRoleId(element.SelectSingleNode("roleId").InnerText);
//    }
//
//    if (element.SelectSingleNode("headship") != null) {
//      this.setHeadship(element.SelectSingleNode("headship").InnerText);
//    }
//
//    if (element.SelectSingleNode("sex") != null) {
//      this.setSex(element.SelectSingleNode("sex").InnerText);
//    }
//
//    if (element.SelectSingleNode("birthday") != null) {
//      this.setBirthday(LocalDateTime.parse(element.SelectSingleNode("birthday").InnerText));
//    }
//
//    if (element.SelectSingleNode("graduateDate") != null) {
//      this.setGraduationDate(LocalDateTime.parse(element.SelectSingleNode("graduateDate").InnerText));
//    }
//
//    if (element.SelectSingleNode("entryDate") != null) {
//      this.setEntryDate(LocalDateTime.parse(element.SelectSingleNode("entryDate").InnerText));
//    }
//
//    if (element.SelectSingleNode("promotionDate") != null) {
//      this.setPromotionDate(LocalDateTime.parse(element.SelectSingleNode("promotionDate").InnerText));
//    }
//
//    this.setMobile(element.SelectSingleNode("mobile").InnerText);
//
//    this.setTelephone(element.SelectSingleNode("telephone").InnerText);
//
//    if (element.SelectSingleNode("email") != null) {
//      this.setEmail(element.SelectSingleNode("email").InnerText);
//    }
//
//    if (element.SelectSingleNode("rtx") != null) {
//      this.setRtx(element.SelectSingleNode("rtx").InnerText);
//    }
//
//    this.setAssignedJobId(element.SelectSingleNode("assignedJobId").InnerText);
//
//    XmlNodeList nodes = null;
//
//    nodes = element.SelectNodes("partTimeJobs/partTimeJobId");
//
//    for (XmlNode node : nodes) {
//      AssignedJob partTimeJob = MembershipManagement.Instance.AssignedJobService[node.InnerText];
//
//      if (partTimeJob != null) {
//        this.getPartTimeJobs().add(partTimeJob);
//      }
//    }
//
//    if (element.SelectSingleNode("jobId") != null) {
//      this.setJobId(element.SelectSingleNode("jobId").InnerText);
//    }
//
//    if (element.SelectSingleNode("jobGrade") != null) {
//      this.setJobGradeDisplayName(element.SelectSingleNode("jobGrade").InnerText);
//    }
//
//    this.setModifiedDate(LocalDateTime.parse(element.SelectSingleNode("updateDate").InnerText));
  }
}
