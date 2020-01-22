package com.x3platform.sync.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.Application;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.time.LocalDateTime;

/**
 * 同步的更新包信息
 *
 * @author ruanyu
 */
public class SyncPkg {

  /**
   * 默认构造函数
   */

  public SyncPkg() {
  }

  private String id = "";

  /**
   * 获取唯一标识
   *
   * @return 唯一标识
   */
  public String getId() {
    return id;
  }

  /**
   * 设置唯一标识
   *
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  private String applicationId = "";

  /**
   * 获取所属应用标识
   *
   * @return 应用标识
   */
  public String getApplicationId() {
    return applicationId;
  }

  private Application application;

  /**
   * 应用
   */
  @JSONField(serialize = false)
  public Application getApplication() {
    if (application == null && !StringUtil.isNullOrEmpty(getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(getApplicationId());
    }
    return application;
  }

  /**
   * 所属应用名称
   */
  public String getApplicationName() {
    return getApplication() == null ? "" : getApplication().getApplicationName();
  }

  /**
   * 所属应用显示名称
   */
  public String getApplicationDisplayName() {
    return getApplication() == null ? "" : getApplication().getApplicationDisplayName();
  }

  /**
   * 设置所属应用标识
   *
   * @param value 值
   */
  public void setApplicationId(String value) {
    applicationId = value;
  }

  private String path = "";

  /**
   * 获取更新包路径
   *
   * @return 更新包路径
   */
  public String getPath() {
    return path;
  }

  /**
   * 设置更新包路径
   *
   * @param value 值
   */
  public void setPath(String value) {
    path = value;
  }

  private long recordCount = 0;

  /**
   * 获取数据记录总数
   *
   * @return 数据记录总数
   */
  public long getRecordCount() {
    return recordCount;
  }

  /**
   * 设置数据记录总数
   *
   * @param value 值
   */
  public void setRecordCount(long value) {
    recordCount = value;
  }

  private LocalDateTime beginDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取开始时间
   *
   * @return 开始时间
   */
  public LocalDateTime getBeginDate() {
    return beginDate;
  }

  /**
   * 设置开始时间
   *
   * @param value 值
   */
  public void setBeginDate(LocalDateTime value) {
    beginDate = value;
  }

  private LocalDateTime endDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取结束时间
   *
   * @return 结束时间
   */
  public LocalDateTime getEndDate() {
    return endDate;
  }

  /**
   * 设置结束时间
   *
   * @param value 值
   */
  public void setEndDate(LocalDateTime value) {
    endDate = value;
  }

  private int isSend = 0;

  /**
   * 获取是否已发送
   *
   * @return 是否已发送
   */
  public int getIsSend() {
    return isSend;
  }

  /**
   * 设置结束时间
   *
   * @param value 值
   */
  public void setIsSend(int value) {
    isSend = value;
  }

  private LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取创建时间
   *
   * @return 创建时间
   */
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置创建时间
   *
   * @param value 值
   */
  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }
}
