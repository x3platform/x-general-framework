package com.x3platform.membership.services.impl;

import static com.x3platform.membership.Constants.ORGANIZATION_STRUCTURE_ID;

import com.x3platform.KernelContext;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.Organization;
import com.x3platform.membership.mappers.OrganizationMapper;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.OrganizationService;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织服务
 *
 * @author ruanyu
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

  @Autowired(required = false)
  OrganizationMapper provider;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link Organization} 实例的详细信息
   * @return {@link Organization} 实例的详细信息
   */
  @Override
  public int save(Organization entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !provider.isExist(entity.getId());

    if (isNewObject) {
      // 新增组织信息时, 同时增加一个组织单元
      OrganizationUnitInfo organizationUnit = new OrganizationUnitInfo();

      organizationUnit.setId(entity.getId());
      organizationUnit.setParentId(ORGANIZATION_STRUCTURE_ID);
      organizationUnit.setName(entity.getName());
      organizationUnit.setGlobalName(entity.getName());

      MembershipManagement.getInstance().getOrganizationUnitService().save(organizationUnit);
      affectedRows = this.provider.insert(entity);
    } else {
      affectedRows = this.provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    int affectedRows = this.provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link Organization} 实例的详细信息
   */
  @Override
  public Organization findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link Organization} 实例的详细信息
   */
  @Override
  public List<Organization> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：boolean isExist(String id)。");
  }
}
