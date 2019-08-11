package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationOption;

import java.util.List;

/**
 * 应用管理查询对应数据格式
 */
public interface ApplicationOptionService {

  /**
   * 获取所有应用配置
   * @param applicationId
   * @return
   */
  public List<ApplicationOption> getApplicationOption(String applicationId) ;


  /**
   * @param applicationId 设置应用id
   * @param name 根据 名称来获取对应值
   */
  public ApplicationOption getApplicationOption(String applicationId,String name);

  /**
   * @param name
   */
  public ApplicationOption getApplicationOptionByName(String name);

}
