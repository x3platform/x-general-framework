package com.x3platform.membership.services;

import com.x3platform.membership.models.OrganizationInfo;

import java.util.List;

/**
 * 组织结构
 */
public interface OrganizationService {

  /**
   *  查询所有
   *
   */
  List<OrganizationInfo> findAll() ;

  /**
   * @param id 根据id 查询当前项目问题；
   * @return
   */
  OrganizationInfo findOne(String id);

}
