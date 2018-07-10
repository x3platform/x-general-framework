package com.x3platform.membership;

import java.util.*;

import com.x3platform.IAuthorizationObject;
import com.x3platform.cacheBuffer.ICacheable;

/** 组织单位
 */
public interface IOrganizationUnitInfo extends IAuthorizationObject, ICacheable
{
  /** 标识
   */
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new string getId();
  String getId();
  //C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new void setId(string value);
  void setId(String value);

  /** 编号
   */
  String getCode();
  void setCode(String value);

  /** 名称
   */
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new string getName();
  String getName();
  //C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new void setName(string value);
  void setName(String value);

  ///#region 属性:GlobalName
  /** 全局名称
   */
  String getGlobalName();
  ///#endregion

  ///#region 属性:FullName
  /** 全称
   */
  String getFullName();
  ///#endregion

  /** 拼音
   */
  String getPinYin();
  void setPinYin(String value);

  /** 所属父级组织标识
   */
  String getParentId();
  void setParentId(String value);

  ///#region 属性:ParentGlobalName
  /** 父节点全局名称
   */
  String getParentGlobalName();
  ///#endregion

  /** 父级组织信息
   */
  IOrganizationUnitInfo getParent();

  ///#region 属性:ChindNodes
  /** 子节点
   */
  List<IAuthorizationObject> getChindNodes();
  ///#endregion

  /** 角色集合
   */
  List<IRoleInfo> getRoles();

  /** 所属标准组织标识
   */
  String getStandardOrganizationUnitId();
  void setStandardOrganizationUnitId(String value);

  ///#region 属性:Type
  /** 类型
   */
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new int getType();
  int getType();
  //C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: new void setType(int value);
  void setType(int value);
  ///#endregion

  ///#region 属性:EnableEmail
  /** 启用企业邮箱
   */
  int getEnableEmail();
  void setEnableEmail(int value);
  ///#endregion

  ///#region 属性:OrderId
  /** 排序标识
   */
  String getOrderId();
  void setOrderId(String value);
  ///#endregion

  ///#region 属性:FullPath
  /** 所属组织架构全路径
   */
  String getFullPath();
  void setFullPath(String value);
  ///#endregion

  ///#region 属性:DistinguishedName
  /** 唯一名称
   */
  String getDistinguishedName();
  void setDistinguishedName(String value);
  ///#endregion

  ///#region 属性:ExtensionInformation
  /** 组织单位扩展信息
   */
  IExtensionInformation getExtensionInformation();
  ///#endregion
}
