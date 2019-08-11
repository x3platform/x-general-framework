package com.x3platform.membership.services.impl;

import com.x3platform.membership.models.SettingInfo;
import com.x3platform.membership.services.SettingService;

import java.util.List;

/**
 * 新实现类
 *
 * @author xueqian.huang
 * @Date 2018-12-24
 */
public class SettingServiceImpl implements SettingService {

  @Override
  public SettingInfo save(SettingInfo param) {
    return null;
  }

  @Override
  public void delete(String id) {

  }

  @Override
  public SettingInfo findOne(String id) {
    return null;
  }

  @Override
  public List<SettingInfo> findAllBySettingGroupId(String settingGroupId) {
    return null;
  }

  @Override
  public List<SettingInfo> findAllBySettingGroupId(String settingGroupId, String keyword) {
    return null;
  }

  @Override
  public List<SettingInfo> findAllBySettingGroupName(String settingGroupName) {
    return null;
  }

  @Override
  public List<SettingInfo> findAllBySettingGroupName(String settingGroupName, String keyword) {
    return null;
  }

  @Override
  public boolean isExist(String id) {
    return false;
  }

  @Override
  public String getText(String settingGroupName, String value) {
    return null;
  }

  @Override
  public String getValue(String settingGroupName, String text) {
    return null;
  }
}
