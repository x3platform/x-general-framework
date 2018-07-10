package com.x3platform.membership;

/** 帐户和群组的关联信息
 */
public interface IAccountGroupRelationInfo
{
  ///#region 属性:AccountId
  /** 帐号标识
   */
  String getAccountId();
  void setAccountId(String value);
  ///#endregion

  ///#region 属性:AccountGlobalName
  /** 帐号全局名称
   */
  String getAccountGlobalName();
  void setAccountGlobalName(String value);
  ///#endregion

  ///#region 属性:GroupId
  /** 群组标识
   */
  String getGroupId();
  void setGroupId(String value);
  ///#endregion

  ///#region 属性:GroupGlobalName
  /** 群组全局名称
   */
  String getGroupGlobalName();
  void setGroupGlobalName(String value);
  ///#endregion

  ///#region 属性:BeginDate
  /** 生效时间
   */
  java.time.LocalDateTime getBeginDate();
  void setBeginDate(java.time.LocalDateTime value);
  ///#endregion

  ///#region 属性:EndDate
  /** 失效时间
   */
  java.time.LocalDateTime getEndDate();
  void setEndDate(java.time.LocalDateTime value);
  ///#endregion

  ///#region 函数:GetAccount()
  IAccountInfo GetAccount();
  ///#endregion

  ///#region 函数:GetGroup()
  IGroupInfo GetGroup();
  ///#endregion
}
