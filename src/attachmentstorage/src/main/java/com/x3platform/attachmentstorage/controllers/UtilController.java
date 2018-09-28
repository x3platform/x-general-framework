package com.x3platform.attachmentstorage.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.KernelContext;
import com.x3platform.attachmentstorage.IAttachmentFileInfo;
import com.x3platform.attachmentstorage.util.UploadFileUtil;
import com.x3platform.globalization.I18n;
import com.x3platform.util.StringUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import com.x3platform.messages.MessageObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Lazy(true)
@RestController
@RequestMapping("/api/attachmentStorage/util")
public class UtilController {

  @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
  public @ResponseBody
  String fileUpload(MultipartHttpServletRequest request) {
    try {
      // 上传的文件数据
      MultipartFile file = request.getFile("fileData");

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

        IAttachmentFileInfo attachment = UploadFileUtil.createAttachmentFile(
          entityId,
          entityClassName,
          attachmentEntityClassName,
          attachmentFolder,
          file);

        // BufferedOutputStream stream =
        //  new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
        // stream.write(buffer);
        // stream.close();

        attachment.save();

        return "You successfully uploaded " + name + " into " + name + "-uploaded !";
      }
    } catch (Exception ex) {
      KernelContext.getLog().error(ex.getMessage(), ex);
    }

    return "fail.";
  }
}
