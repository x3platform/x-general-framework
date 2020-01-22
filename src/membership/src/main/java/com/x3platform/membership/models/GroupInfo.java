package com.x3platform.membership.models;

import com.x3platform.membership.Account;
import com.x3platform.membership.Group;
import com.x3platform.util.DateUtil;
import java.util.Date;
import java.util.List;
import org.dom4j.Element;

/**
 * @author ruanyu
 */
public class GroupInfo implements Group {

  /**
   * 默认构造函数
   */
  public GroupInfo() {
  }

  private String id = "";

  /**
   * 获取唯一标识
   *
   * @return 唯一标识
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * 设置唯一标识
   *
   * @param value 值
   */
  @Override
  public void setId(String value) {
    id = value;
  }

  private String code = "";

  /**
   * 获取编号
   *
   * @return 编号
   */
  @Override
  public String getCode() {
    return code;
  }

  /**
   * 设置编号
   *
   * @param value 值
   */
  @Override
  public void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   * 获取名称
   *
   * @return 名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置名称
   *
   * @param value 值
   */
  public void setName(String value) {
    name = value;
  }

  private String globalName = "";

  /**
   * 获取全局名称
   *
   * @return 全局名称
   */
  @Override
  public String getGlobalName() {
    return globalName;
  }

  /**
   * 设置全局名称
   *
   * @param value 值
   */
  public void setGlobalName(String value) {
    globalName = value;
  }

  private String pinyin = "";

  /**
   * 获取拼音
   *
   * @return 拼音
   */
  @Override
  public String getPinYin() {
    return pinyin;
  }

  /**
   * 设置拼音
   *
   * @param value 值
   */
  @Override
  public void setPinYin(String value) {
    pinyin = value;
  }

  private String type = "";

  /**
   * 获取类型
   *
   * @return 类型
   */
  public String getType() {
    return type;
  }

  /**
   * 设置类型
   *
   * @param value 值
   */
  public void setType(String value) {
    type = value;
  }

  private String catalogItemId = "";

  /**
   * 获取分类目录标识
   *
   * @return 分类目录标识
   */
  @Override
  public String getCatalogItemId() {
    return catalogItemId;
  }

  /**
   * 设置分类目录标识
   *
   * @param value 值
   */
  @Override
  public void setCatalogItemId(String value) {
    catalogItemId = value;
  }

  private int enableEmail = 0;

  /**
   * 获取启用邮箱
   *
   * @return 启用邮箱
   */
  @Override
  public int getEnableEmail() {
    return enableEmail;
  }

  /**
   * 设置启用邮箱
   *
   * @param value 值
   */
  @Override
  public void setEnableEmail(int value) {
    enableEmail = value;
  }

  private int effectScope = 0;

  /**
   * 获取权限作用范围
   *
   * @return 权限作用范围
   */
  public int getEffectScope() {
    return effectScope;
  }

  /**
   * 设置权限作用范围
   *
   * @param value 值
   */
  public void setEffectScope(int value) {
    effectScope = value;
  }

  private int locking = 0;

  /**
   * 获取锁定
   *
   * @return 锁定
   */
  @Override
  public int getLocking() {
    return locking;
  }

  /**
   * 设置锁定
   *
   * @param value 值
   */
  @Override
  public void setLocking(int value) {
    locking = value;
  }

  private String orderId = "";

  /**
   * 获取排序
   *
   * @return 排序
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * 设置排序
   *
   * @param value 值
   */
  public void setOrderId(String value) {
    orderId = value;
  }

  private int status = 0;

  /**
   * 获取状态
   *
   * @return 状态
   */
  @Override
  public int getStatus() {
    return status;
  }

  /**
   * 设置状态
   *
   * @param value 值
   */
  @Override
  public void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   * 获取备注
   *
   * @return 备注
   */
  @Override
  public String getRemark() {
    return remark;
  }

  /**
   * 设置备注
   *
   * @param value 值
   */
  @Override
  public void setRemark(String value) {
    remark = value;
  }

  private String fullPath = "";

  /**
   * 获取完整路径
   *
   * @return 完整路径
   */
  @Override
  public String getFullPath() {
    return fullPath;
  }

  /**
   * 设置完整路径
   *
   * @param value 值
   */
  @Override
  public void setFullPath(String value) {
    fullPath = value;
  }

  private String distinguishedName = "";

  /**
   * 获取唯一名称
   *
   * @return 唯一名称
   */
  @Override
  public String getDistinguishedName() {
    return distinguishedName;
  }

  /**
   * 设置唯一名称
   *
   * @param value 值
   */
  @Override
  public void setDistinguishedName(String value) {
    distinguishedName = value;
  }

  @Override
  public List<Account> getMembers() {
    return null;
  }

  @Override
  public void resetMemberRelations(String relationText) {

  }

  private Date modifiedDate = DateUtil.getDefaultDate();

  /**
   * 获取修改时间
   *
   * @return 修改时间
   */
  @Override
  public Date getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置修改时间
   *
   * @param value 值
   */
  @Override
  public void setModifiedDate(Date value) {
    modifiedDate = value;
  }

  private Date createdDate = DateUtil.getDefaultDate();

  /**
   * 获取创建时间
   *
   * @return 创建时间
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置创建时间
   *
   * @param value 值
   */
  public void setCreatedDate(Date value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 实现 AuthorizationObject 接口
  // -------------------------------------------------------

  @Override
  public String getAuthorizationObjectType() {
    return "Role";
  }

  @Override
  public String getAuthorizationObjectId() {
    return getId();
  }

  @Override
  public void setAuthorizationObjectId(String value) {
    setId(value);
  }

  @Override
  public String getAuthorizationObjectName() {
    return getGlobalName();
  }

  @Override
  public void setAuthorizationObjectName(String value) {
    setName(value);
  }

  // -------------------------------------------------------
  // 实现 SerializedObject 序列化
  // -------------------------------------------------------

  @Override
  public String serializable() {
    return null;
  }

  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    return null;
  }

  @Override
  public void deserialize(Element element) {

  }
}
