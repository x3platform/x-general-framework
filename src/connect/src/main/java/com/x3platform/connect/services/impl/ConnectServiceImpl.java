package com.x3platform.connect.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.connect.configuration.*;
import com.x3platform.connect.mappers.ConnectMapper;
import com.x3platform.connect.models.Connect;
import com.x3platform.connect.services.ConnectService;

import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;

import com.x3platform.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ConnectServiceImpl implements ConnectService {

  @Autowired
  private ConnectMapper provider = null;

  /**
   * 缓存存储
   */
  private Map<String, Connect> cacheStorage = new HashMap<String, Connect>();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  ///#region 函数:Save(Connect param)

  /**
   * 保存记录
   *
   * @param param Connect 实例详细信息
   * @return Connect 实例详细信息
   */
  @Override
  public int save(Connect param) {
    if (StringUtil.isNullOrEmpty(param.getId())) {
      throw new RuntimeException("实例标识不能为空。");
    }
    boolean isNewObject = !this.isExist(param.getId());
    String methodName = isNewObject ? "新增" : "编辑";
    if (isNewObject) {
      Account account = KernelContext.getCurrent().getUser();
      param.setAccountId(account.getId());
      param.setAccountName(account.getName());
    }
    // 过滤 Cross Site Script
    // param = StringHelper.<Connect>ToSafeXSS(param);
    int res = this.provider.save(param);
    return res;
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public int delete(String id) {
    return this.provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 连接器标识
   * @return 返回一个实例 Connect 的详细信息
   */
  @Override
  public Connect findOne(String id) {
    return this.provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @param appKey AppKey
   * @return 返回一个实例 Connect 的详细信息
   */
  @Override
  public Connect findOneByAppKey(String appKey) {
    Connect param = null;

    // 初始化缓存
    if (this.cacheStorage.isEmpty()) {
      DataQuery query = new DataQuery();
      List<Connect> list = this.findAll(query);

      for (Connect item : list) {
        this.cacheStorage.put(item.getAppKey(), item);
      }
    }

    // 查找缓存数据
    if (this.cacheStorage.containsKey(appKey)) {
      param = this.cacheStorage.get(appKey);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    return param == null ? this.provider.findOneByAppKey(appKey) : param;
  }

  ///#region 函数:FindAll(string whereClause,int length)

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 Connect 的详细信息
   */
  @Override
  public List<Connect> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }
  ///#endregion

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  ///#region 函数:GetPaging(int startIndex, int pageSize, DataQuery query, out int rowCount)
  /** 分页函数
   @param startIndex 开始行索引数,由0开始统计
   @param pageSize 页面大小
   @param query 数据查询参数
   @param rowCount 行数
   @return 返回一个列表 Connect 实例
   */
  // public List<Connect> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount)
  // {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }
  ///#endregion

  ///#region 函数:GetQueryObjectPaging(int startIndex, int pageSize, string whereClause, string orderBy, out int rowCount)
  /** 分页函数
   @param startIndex 开始行索引数,由0开始统计
   @param pageSize 页面大小
   @param query 数据查询参数
   @param rowCount 行数
   @return 返回一个列表 Connect 实例
   */
  // public List<ConnectQueryInfo> GetQueryObjectPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount)
  // {
  //   return this.provider.GetQueryObjectPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param appKey AppKey
   * @return 布尔值
   */
  @Override
  public boolean isExistAppKey(String appKey) {
    return (this.findOneByAppKey(appKey) == null) ? false : true;
  }

  /**
   * 重置应用链接器的密钥
   *
   * @param appKey AppKey
   * @return
   */
  @Override
  public int resetAppSecret(String appKey) {
    String appSecret = StringUtil.substring(DigitalNumberContext.generate("Key_32DigitGuid"), 0, 8);

    return this.resetAppSecret(appKey, appSecret);
  }

  /**
   * 重置应用链接器的密钥
   *
   * @param appKey    App Key
   * @param appSecret App Secret
   * @return
   */
  @Override
  public int resetAppSecret(String appKey, String appSecret) {
    // 更新缓存信息
    Connect param = this.findOneByAppKey(appKey);

    param.setAppSecret(appSecret);

    // 更新数据库信息
    return this.provider.resetAppSecret(appKey, appSecret);
  }
}
