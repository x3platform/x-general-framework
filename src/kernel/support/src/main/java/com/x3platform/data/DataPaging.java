package com.x3platform.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据分页辅助类
 *
 * @author ruanyu
 */
public class DataPaging {
  /**
   * 构造函数: 默认 Page Size = 10
   */
  public DataPaging() {
    this(10);
  }

  /**
   * 构造函数
   *
   * @param pageSize 每页显示的数目
   */
  public DataPaging(int pageSize) {
    this.pageSize = pageSize;

    currentPage = 1;

    firstPage = 1;

    mPreviousPage = 1;

    mNextPage = 2;

    getQuery().setLength(0);
  }

  /**
   * 分析
   */
  private void parse() {
    // Get [rowIndex] [pageCount]

    if (pageSize > 0) {
      offset = (currentPage < 1) ? 0 : (currentPage - 1) * pageSize;

      currentPage = (offset < 0) ? 1 : (offset / pageSize + 1);

      pageCount = (total < 0) ? 1 : ((total - 1) / pageSize + 1);

      mLastPage = pageCount;
    } else {
      pageCount = 1;
    }

    // Get [previousPage] [nextPage]

    mPreviousPage = (currentPage <= 1) ? 1 : currentPage - 1;

    mNextPage = (currentPage >= pageCount) ? pageCount : currentPage + 1;
  }

  private int pageSize;

  /**
   * 每页显示行数
   */
  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int value) {
    if (value > 0) {
      pageSize = value;

      parse();
    }
  }

  private int offset;

  /**
   * 行索引号
   */
  public int getOffset() {
    return offset;
  }

  public void setRowIndex(int value) {
    if (value > 0) {
      offset = value;

      parse();
    }
  }

  private int total;

  /**
   * 行数统计.
   */
  public int getTotal() {
    return total;
  }

  public void setTotal(int value) {
    total = value;

    parse();
  }

  private int pageCount;

  /**
   * 页数统计
   */
  public int getPageCount() {
    return pageCount;
  }

  private int currentPage;

  /**
   * 当前页码
   */
  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int value) {
    if (value > 0) {
      currentPage = value;

      parse();
    }
  }

  private int firstPage;

  /**
   * 首页
   */
  public int getFirstPage() {
    return firstPage;
  }

  /**
   * 上页
   */
  private int mPreviousPage;

  public int getPreviousPage() {
    return mPreviousPage;
  }

  private int mNextPage;

  /**
   * 下页
   */
  public int getNextPage() {
    return mNextPage;
  }

  private int mLastPage;

  /**
   * 末页
   */
  public int getLastPage() {
    return mLastPage;
  }

  // -------------------------------------------------------
  // 分页数据查询条件
  // -------------------------------------------------------

  private DataQuery mQuery = new DataQuery();

  /**
   * 数据查询对象
   */
  @JSONField(serialize = false)
  public DataQuery getQuery() {
    return mQuery;
  }

  /**
   * SQL 排序规则
   */
  public String getOrderBy() {
    return getQuery().getOrderBySql();
  }

  /**
   * @return JSON 格式字符串
   */
  @Override
  public String toString() {
    return "{\"pageSize\":\"" + getPageSize() + "\","
      + "\"offset\":\"" + getOffset() + "\","
      + "\"total\":\"" + getTotal() + "\","
      + "\"pageCount\":\"" + getPageCount() + "\","
      + "\"firstPage\":\"" + getFirstPage() + "\","
      + "\"previousPage\":\"" + getPreviousPage() + "\","
      + "\"nextPage\":\"" + getNextPage() + "\","
      + "\"lastPage\":\"" + getLastPage() + "\"}";
  }
}
