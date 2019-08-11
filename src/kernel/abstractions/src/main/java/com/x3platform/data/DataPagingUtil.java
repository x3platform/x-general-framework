package com.x3platform.data;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.x3platform.util.StringUtil;

import java.util.List;

/**
 * 数据分页辅助类
 *
 * @author ruanyu
 */
public class DataPagingUtil {

  /**
   * 根据Xml字符串创建对象
   */
  public static DataPaging create(String jsonText) {
  /*
   xml = String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n<root>%1$s</root>", xml);

    PagingHelper paging = new PagingHelper();

    return AjaxUtil.<PagingHelper>Deserialize(paging, xml);
  */

    return JSON.parseObject(jsonText, DataPaging.class);
  }
  ///#endregion

  ///#region 静态函数:Create(string xml, string queryXml)

  /**
   * 根据Xml字符串创建对象
   */
  public static DataPaging create(String jsonText, String queryJsonText) {
    DataPaging paging = create(jsonText);

    if (!StringUtil.isNullOrEmpty(queryJsonText)) {
      paging.getQuery().fromJSON(queryJsonText);
    }
    return paging;
  }

  public static <T> int getTotal(List<T> list) {
    if (list instanceof Page) {
      return (int)((Page) list).getTotal();
    } else {
      return list.size();
    }
  }
}
