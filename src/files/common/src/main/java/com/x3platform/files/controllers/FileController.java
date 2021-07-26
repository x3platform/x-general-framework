package com.x3platform.files.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.ObjectStorageContext;
import com.x3platform.files.configuration.ObjectStorageConfigurationView;
import com.x3platform.files.services.ObjectStorageService;
import com.x3platform.files.util.ObjectStoragePathUtil;
import com.x3platform.files.util.ObjectStorageFileUtil;
import com.x3platform.files.util.ObjectStoragePathUtil;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.StringUtil;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 附件 API 接口
 *
 * @author ruanyu
 */
@Lazy
@RestController("com.x3platform.files.controllers.FileController")
@RequestMapping("/api/sys/files/file")
public class FileController {

  /**
   * 业务服务接口
   */
  private ObjectStorageService service = ObjectStorageContext.getInstance().getObjectStorageService();

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  @RequestMapping("/delete")
  public String delete(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    this.service.delete(id);

    return MessageObject.stringify("0", I18n.getStrings().text("msg_delete_success"));
  }

  /**
   * 获取详细信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/findOne")
  public String findOne(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String id = req.getString("id");

    ObjectStorageFile entity = this.service.findOne(id);

    return "{\"data\":" + JSON.toJSONString(entity) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping("/findAll")
  public String findAll(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String entityId = req.getString("entityId");
    String entityClassName = req.getString("entityClassName");
    String orderBy = req.getString("orderBy");
    String lengthValue = req.getString("length");
    if (StringUtil.isNullOrEmpty(orderBy)) {
      orderBy = "attachment_name";
    }
    int length = 0;
    if (!StringUtil.isNullOrEmpty(lengthValue)) {
      length = Integer.valueOf(lengthValue);
    }

    DataQuery query = new DataQuery();

    query.getWhere().put("entity_id", entityId);
    query.getWhere().put("entity_class_name", entityClassName);

    query.getOrders().add(orderBy);
    query.setLength(length);

    List<ObjectStorageFile> list = this.service.findAll(query);

    return "{\"data\":" + JSON.toJSONString(list) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping(value = "/query")
  public String query(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    DataPaging paging = DataPagingUtil.create(req.getString("paging"));

    DataQuery query = DataQuery.create(req.getString("query"));

    PageHelper.startPage(paging.getCurrentPage(), paging.getPageSize());

    List<ObjectStorageFile> list = this.service.findAll(query);

    paging.setTotal(DataPagingUtil.getTotal(list));

    return "{\"data\":" + JSON.toJSONString(list) + ",\"paging\":" + JSON.toJSONString(paging) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取列表信息
   *
   * @param data 请求的数据内容
   * @return 一个相关的实例列表信息
   */
  @RequestMapping(value = "/base64upload", method = RequestMethod.POST)
  public String base64Upload(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    try {
      // (必填)上传的文件数据
      String base64Data = req.getString("fileData");
      // 输出类型 id uri base64
      String outputType = req.getString("outputType");
      // 文件唯一标识
      String attachmentId = ObjectStorageFileUtil.newIdentity();
      // (必填)所属实体唯一标识
      String entityId = req.getString("entityId");
      // (必填)所属实体类名称
      String entityClassName = req.getString("entityClassName");
      // 附件实体类名称
      String attachmentEntityClassName = req.getString("attachmentEntityClassName");
      // (必填)文件夹名称
      String attachmentFolder = req.getString("attachmentFolder");

      // 设置默认输出内容类型
      if (StringUtil.isNullOrEmpty(outputType)) {
        outputType = "object";
      }

      if (!StringUtil.isNullOrEmpty(base64Data)) {
        byte[] fileData = Base64.getDecoder().decode(base64Data);

        String fileType = "";

        String fileTypeValue = Integer.valueOf(fileData[0] & 0xFF) + "" + Integer.valueOf(fileData[1] & 0xFF);

        switch (fileTypeValue) {
          case "255216":
            fileType = ".jpg";
            break;
          case "7173":
            fileType = ".gif";
            break;
          case "13780":
            fileType = ".png";
            break;
          default:
            break;
        }

        String fileName = attachmentId + fileType;

        int fileSize = fileData.length;

        ObjectStorageFile attachment = ObjectStorageFileUtil.createObjectStorageFile(
          entityId,
          entityClassName,
          attachmentEntityClassName,
          attachmentFolder,
          attachmentId,
          fileName,
          fileType,
          fileSize,
          fileData);

        // 检测文件最小限制
        if (attachment.getFileSize() < ObjectStorageConfigurationView.getInstance().getAllowMinFileSize()) {
          return MessageObject.stringify("500", "File size is too small.");
        }

        // 检测文件最大限制
        if (attachment.getFileSize() > ObjectStorageConfigurationView.getInstance().getAllowMaxFileSize()) {
          return MessageObject.stringify("500", "File size is too big.");
        }

        // 检测文件名后缀限制
        String allowFileTypes = ObjectStorageConfigurationView.getInstance().getAllowFileTypes();
        allowFileTypes = ".*(." + allowFileTypes.replace(",", "|.") + ")$";
        if (!Pattern.compile(allowFileTypes, Pattern.CASE_INSENSITIVE).matcher(attachment.getFileName())
          .matches()) {
          return MessageObject.stringify("500", "File type is invalid.");
        }

        attachment.save();

        KernelContext.getLog().info("attachment id: {}", attachment.getId());

        if (outputType.equals("uri")) {
          // 输出 uri
          return attachment.getVirtualPath()
            .replace("{uploads}", ObjectStorageConfigurationView.getInstance().getVirtualUploadFolder());
        } else if (outputType.equals("id")) {
          // 输出 id
          return attachment.getId();
        } else {
          // 输出 object
          return "{\"data\":{\"id\":\"" + attachment.getId() + "\",\"url\":\"" + attachment.getVirtualPath()
            .replace("{uploads}", ObjectStorageConfigurationView.getInstance().getVirtualUploadFolder()) + "\"},"
            + MessageObject.stringify("0", "File upload success", true) + "}";
        }
      }
    } catch (Exception ex) {
      KernelContext.getLog().error(ex.getMessage(), ex);
      return MessageObject.stringify("1", ex.getMessage());
    }

    return MessageObject.stringify("1", "Fail");
  }

  @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
  public String fileUpload(MultipartHttpServletRequest request) {
    try {
      // 上传的文件数据
      MultipartFile file = request.getFile("fileData");

      // 如果附件信息为空 默认获取第一个文件
      if (file == null) {
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        for (String key : map.keySet()) {
          file = request.getFile(key);
          break;
        }
      }

      // 输出类型 id uri base64
      String outputType = request.getParameter("outputType");
      // 文件唯一标识
      String attachmentId = request.getParameter("attachmentId");
      // (必填)所属实体唯一标识
      String entityId = request.getParameter("entityId");
      // (必填)所属实体类名称
      String entityClassName = request.getParameter("entityClassName");
      // 附件实体类名称
      String attachmentEntityClassName = request.getParameter("attachmentEntityClassName");
      // (必填)文件夹名称
      String attachmentFolder = request.getParameter("attachmentFolder");

      // 设置默认输出内容类型
      if (StringUtil.isNullOrEmpty(outputType)) {
        outputType = "object";
      }

      if (!file.isEmpty()) {
        ObjectStorageFile attachment = ObjectStorageFileUtil.createObjectStorageFile(
          entityId,
          entityClassName,
          attachmentEntityClassName,
          attachmentFolder,
          file);

        // 检测文件最小限制
        if (attachment.getFileSize() < ObjectStorageConfigurationView.getInstance().getAllowMinFileSize()) {
          return MessageObject.stringify("500", "File size is too small.");
        }

        // 检测文件最大限制
        if (attachment.getFileSize() > ObjectStorageConfigurationView.getInstance().getAllowMaxFileSize()) {
          return MessageObject.stringify("500", "File size is too big.");
        }

        // 检测文件名后缀限制
        String allowFileTypes = ObjectStorageConfigurationView.getInstance().getAllowFileTypes();
        allowFileTypes = ".*(." + allowFileTypes.replace(",", "|.") + ")$";
        if (!Pattern.compile(allowFileTypes, Pattern.CASE_INSENSITIVE).matcher(attachment.getFileName())
          .matches()) {
          return MessageObject.stringify("500", "File type is invalid.");
        }

        attachment.save();

        KernelContext.getLog().info("attachment id: {}", attachment.getId());

        if (outputType.equals("uri")) {
          // 输出 uri
          return attachment.getVirtualPath()
            .replace("{uploads}", ObjectStorageConfigurationView.getInstance().getVirtualUploadFolder());
        } else if (outputType.equals("id")) {
          // 输出 id
          return attachment.getId();
        } else {
          // 输出 object
          return "{\"data\":{\"id\":\"" + attachment.getId() + "\",\"url\":\"" + attachment.getVirtualPath()
            .replace("{uploads}", ObjectStorageConfigurationView.getInstance().getVirtualUploadFolder()) + "\"},"
            + MessageObject.stringify("0", "File upload success", true) + "}";
        }
      }
    } catch (Exception ex) {
      InternalLogger.getLogger().error("ObjectStorageFileController.fileUpload()", ex);
      return MessageObject.stringify("1", ex.getMessage());
    }

    return MessageObject.stringify("1", "Fail");
  }

  /**
   * 下载文件
   */
  @RequestMapping(value = "/download", method = RequestMethod.GET)
  public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String id = request.getParameter("id");

    ObjectStorageFile file = this.service.findOne(id);

    String location = file.getVirtualPath().replace("{uploads}", ObjectStoragePathUtil.getVirtualUploadFolder());

    response.sendRedirect(KernelConfigurationView.getInstance().getFileHost() + location);
  }
}
