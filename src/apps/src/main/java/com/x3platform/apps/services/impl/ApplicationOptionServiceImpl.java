package com.x3platform.apps.services.impl;

import com.x3platform.apps.mappers.ApplicationOptionMapper;
import com.x3platform.apps.models.ApplicationOption;
import com.x3platform.apps.services.ApplicationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采用注释模式进行处理
 */
@Service
public class ApplicationOptionServiceImpl implements ApplicationOptionService {

  @Autowired(required = false)
  ApplicationOptionMapper optionMapper;

  @Override
  public List<ApplicationOption> getApplicationOption(String applicationId) {
    Map<String, Object> params = new HashMap<>();
    params.put("applicationId", applicationId);
    return optionMapper.findAll(params);
  }

  @Override
  public ApplicationOption getApplicationOption(String applicationId, String name) {
    return optionMapper.findApplicationOption(applicationId, name);
  }

  @Override
  public ApplicationOption getApplicationOptionByName(String name) {
    return optionMapper.findApplicationOptionByName(name);
  }
}
