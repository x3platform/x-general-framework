package com.x3platform.membership.services.impl;

import com.x3platform.membership.mappers.OrganizationMapper;
import com.x3platform.membership.models.OrganizationInfo;
import com.x3platform.membership.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService  {

  @Autowired(required = false)
  OrganizationMapper organizationMapper ;

  @Override
  public List<OrganizationInfo> findAll() {
    return null;
  }

  @Override
  public OrganizationInfo findOne(String id) {
    return null;
  }
}
