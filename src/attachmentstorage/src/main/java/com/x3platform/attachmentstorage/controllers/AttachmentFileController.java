package com.x3platform.attachmentstorage.controllers;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.attachmentstorage.AttachmentStorageContext;
import com.x3platform.attachmentstorage.IAttachmentFileInfo;
import com.x3platform.attachmentstorage.services.IAttachmentFileService;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.util.StringUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x3platform.messages.MessageObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Lazy(true)
@RestController
@RequestMapping("/api/attachmentstorage/file")
public class AttachmentFileController {
  // 数据提供器
  private IAttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  @RequestMapping("/save")
  public final String save(@RequestBody String data) {
    IAttachmentFileInfo entity = JSON.parseObject(data, IAttachmentFileInfo.class);

    this.service.save(entity);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_save_success"));
  }

  @RequestMapping("/delete")
  public final String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    this.service.delete(id);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  @RequestMapping("/findOne")
  public final String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    IAttachmentFileInfo entity = this.service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 返回一个相关的实例列表信息.
   */
  @RequestMapping(value = "/query")
  public final String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.Create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<IAttachmentFileInfo> list = this.service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
