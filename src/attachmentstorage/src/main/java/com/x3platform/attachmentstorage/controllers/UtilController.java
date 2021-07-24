package com.x3platform.attachmentstorage.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.KernelContext;
import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.util.UploadFileUtil;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.x3platform.messages.MessageObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Lazy
@RestController("com.x3platform.attachmentstorage.controllers.UtilController")
@RequestMapping("/api/attachmentstorage/util")
public class UtilController {

  /**
   * 获取列表信息
   *
   * @param request HTTP 请求
   * @return 返回一个相关的实例列表信息.
   */
  @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
  public @ResponseBody
  String fileUpload(MultipartHttpServletRequest request) {
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

      if (!file.isEmpty()) {
        String name = file.getName();

        byte[] buffer = file.getBytes();

        AttachmentFile attachment = UploadFileUtil.createAttachmentFile(
          entityId,
          entityClassName,
          attachmentEntityClassName,
          attachmentFolder,
          file);

        // 检测文件最小限制
        if (attachment.getFileSize() < AttachmentStorageConfigurationView.getInstance().getAllowMinFileSize()) {
          return MessageObject.stringify("500", "Attachment file size is too small.");
        }

        // 检测文件最大限制
        if (attachment.getFileSize() > AttachmentStorageConfigurationView.getInstance().getAllowMaxFileSize()) {
          return MessageObject.stringify("500", "Attachment file size is too big.");
        }

        // 检测文件名后缀限制
        String allowFileTypes = AttachmentStorageConfigurationView.getInstance().getAllowFileTypes();
        allowFileTypes = ".*(." + allowFileTypes.replace(",", "|.") + ")$";
        if (!Pattern.compile(allowFileTypes, Pattern.CASE_INSENSITIVE).matcher(attachment.getAttachmentName())
          .matches()) {
          return MessageObject.stringify("500", "Attachment file type is invalid.");
        }

        attachment.save();

        KernelContext.getLog().info("attachment id: {}", attachment.getId());

        if (outputType.equals("uri")) {
          // 输出 uri
          return attachment.getVirtualPath()
            .replace("{uploads}", AttachmentStorageConfigurationView.getInstance().getVirtualUploadFolder());
        } else if (outputType.equals("id")) {
          // 输出 id
          return attachment.getId();
        } else {
          // 输出 object
          return "{\"data\":{\"id\":\"" + attachment.getId() + "\",\"url\":\"" + attachment.getVirtualPath()
            .replace("{uploads}", AttachmentStorageConfigurationView.getInstance().getVirtualUploadFolder()) + "\"},"
            + MessageObject.stringify("0", "Attachment file upload success", true) + "}";
        }
      }
    } catch (Exception ex) {
      KernelContext.getLog().error(ex.getMessage(), ex);
      return MessageObject.stringify("1", ex.getMessage());
    }

    return MessageObject.stringify("1", "Fail");
  }
}
