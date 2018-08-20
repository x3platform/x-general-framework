package com.x3platform.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据分页辅助类
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
    this.mPageSize = pageSize;

    this.mCurrentPage = 1;

    this.mFirstPage = 1;

    this.mPreviousPage = 1;

    this.mNextPage = 2;

    this.getQuery().setLength(0);
  }

  /**
   * 分析
   */
  private void Parse() {
    // Get [rowIndex] [pageCount]

    if (mPageSize > 0) {
      mOffset = (mCurrentPage < 1) ? 0 : (mCurrentPage - 1) * mPageSize;

      mCurrentPage = (mOffset < 0) ? 1 : (mOffset / mPageSize + 1);

      mPageCount = (mTotal < 0) ? 1 : ((mTotal - 1) / mPageSize + 1);

      mLastPage = mPageCount;
    } else {
      mPageCount = 1;
    }

    // Get [previousPage] [nextPage]

    mPreviousPage = (mCurrentPage <= 1) ? 1 : mCurrentPage - 1;

    mNextPage = (mCurrentPage >= mPageCount) ? mPageCount : mCurrentPage + 1;
  }

  private int mPageSize;

  /**
   * 每页显示行数
   */
  public final int getPageSize() {
    return mPageSize;
  }

  public final void setPageSize(int value) {
    if (value > 0) {
      mPageSize = value;

      this.Parse();
    }
  }

  private int mOffset;

  /**
   * 行索引号
   */
  public final int getOffset() {
    return mOffset;
  }

  public final void setRowIndex(int value) {
    if (value > 0) {
      mOffset = value;

      this.Parse();
    }
  }

  private int mTotal;

  /**
   * 行数统计.
   */
  public final int getTotal() {
    return mTotal;
  }

  public final void setTotal(int value) {
    mTotal = value;

    this.Parse();
  }

  private int mPageCount;

  /**
   * 页数统计
   */
  public final int getPageCount() {
    return mPageCount;
  }

  private int mCurrentPage;

  /**
   * 当前页码
   */
  public final int getCurrentPage() {
    return mCurrentPage;
  }

  public final void setCurrentPage(int value) {
    if (value > 0) {
      mCurrentPage = value;

      this.Parse();
    }
  }

  public int mFirstPage;

  /**
   * 首页
   */
  public final int getFirstPage() {
    return mFirstPage;
  }

  /**
   * 上页
   */
  private int mPreviousPage;

  public final int getPreviousPage() {
    return mPreviousPage;
  }

  private int mNextPage;

  /**
   * 下页
   */
  public final int getNextPage() {
    return mNextPage;
  }

  private int mLastPage;

  /**
   * 末页
   */
  public final int getLastPage() {
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
  public final DataQuery getQuery() {
    return this.mQuery;
  }

  /**
   * SQL 排序规则
   */
  public final String getOrderBy() {
    return this.getQuery().getOrderBySql();
  }

  /**
   * @return
   */
  @Override
  public String toString() {
    return "{\"pageSize\":\"" + this.getPageSize() + "\","
      + "\"offset\":\"" + this.getOffset() + "\","
      + "\"total\":\"" + this.getTotal() + "\","
      + "\"pageCount\":\"" + this.getPageCount() + "\","
      + "\"firstPage\":\"" + this.getFirstPage() + "\","
      + "\"previousPage\":\"" + this.getPreviousPage() + "\","
      + "\"nextPage\":\"" + this.getNextPage() + "\","
      + "\"lastPage\":\"" + this.getLastPage() + "\"}";
  }
}
