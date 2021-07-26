package com.x3platform.files.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.KernelContext;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.configuration.ObjectStorageConfigurationView;
import com.x3platform.files.util.ObjectStorageFileUtil;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.util.StringUtil;
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
@RestController("com.x3platform.files.controllers.CKEditorController")
@RequestMapping("/api/sys/files/ckeditor")
public class CKEditorController {

  @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
  public @ResponseBody
  String fileUpload(MultipartHttpServletRequest request) {
    try {
      // 上传的文件数据
      MultipartFile file = request.getFile("upload");

      // 如果附件信息为空 默认获取第一个文件
      if (file == null) {
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        for (String key : map.keySet()) {
          file = request.getFile(key);
          break;
        }
      }
      
      // 文件唯一标识
      String attachmentId = DigitalNumberContext.generate("Key_RunningNumber");
      // (必填)所属实体唯一标识
      String entityId = request.getParameter("entityId");
      // (必填)所属实体类名称
      String entityClassName = request.getParameter("entityClassName");
      // 附件实体类名称
      String attachmentEntityClassName = request.getParameter("attachmentEntityClassName");
      // (必填)文件夹名称
      String attachmentFolder = request.getParameter("attachmentFolder");

      if (StringUtil.isNullOrEmpty(entityId)) {
        entityId = attachmentId;
      }

      if (StringUtil.isNullOrEmpty(entityClassName)) {
        entityClassName = "ckeditor";
      }

      if (!file.isEmpty()) {
        String name = file.getName();

        byte[] buffer = file.getBytes();

        ObjectStorageFile attachment = ObjectStorageFileUtil.createObjectStorageFile(
          entityId,
          entityClassName,
          attachmentEntityClassName,
          attachmentFolder,
          file);

        // 检测文件最小限制
        if (attachment.getFileSize() < ObjectStorageConfigurationView.getInstance().getAllowMinFileSize()) {
          return "{\"uploaded\":0, \"error\":{\"message\":\"File file size is too small.\" }";
        }

        // 检测文件最大限制
        if (attachment.getFileSize() > ObjectStorageConfigurationView.getInstance().getAllowMaxFileSize()) {
          return "{\"uploaded\":0, \"error\":{\"message\":\"File file size is too big.\" }";
        }

        // jpg,png
        // 检测文件名后缀限制
        String allowFileTypes = ObjectStorageConfigurationView.getInstance().getAllowFileTypes();
        allowFileTypes = ".*(." + allowFileTypes.replace(",", "|.") + ")$";
        if (!Pattern.compile(allowFileTypes, Pattern.CASE_INSENSITIVE).matcher(attachment.getFileName()).matches()) {
          return "{\"uploaded\":0, \"error\":{\"message\":\"File file type is invalid.\" }";
        }

        attachment.save();

        StringBuffer outString = new StringBuffer();

        outString.append("{\"uploaded\":1,");
        outString.append("\"fileName\":\"" + attachment.getFileName() + "\",");
        outString.append("\"url\":\"" + KernelConfigurationView.getInstance().getFileHost() + attachment.getVirtualPath().replace("{uploads}", ObjectStorageConfigurationView.getInstance().getVirtualUploadFolder()) + "\"}");

        return outString.toString();
      }
    } catch (Exception ex) {
      KernelContext.getLog().error(ex.getMessage(), ex);
      return "{\"uploaded\":0, \"error\":{\"message\":\"" + ex.getMessage() + "\" }";
    }

    return "{\"uploaded\":0, \"error\":{\"message\":\"fail.\" }";
  }
}
