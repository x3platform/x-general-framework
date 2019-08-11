package com.x3platform.attachmentstorage.services.impl;

import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.AttachmentStorageContext;
import com.x3platform.attachmentstorage.GeneralAttachmentFile;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.fastdfs.FastDFSFileManager;
import com.x3platform.attachmentstorage.fastdfs.FastDFSFile;
import com.x3platform.attachmentstorage.services.FastDFSFileTransferService;
import com.x3platform.attachmentstorage.services.AttachmentFileService;
import com.x3platform.attachmentstorage.FileMessageObject;
import com.x3platform.attachmentstorage.util.SysUtils;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FastDFSFileTransferServiceImpl implements FastDFSFileTransferService {

  private static Logger logger = LoggerFactory.getLogger(FastDFSFileTransferServiceImpl.class);

  @Autowired
  private FastDFSFileManager fastDFSFileManager;

  private FastDFSFile createFastDFSFile(AttachmentFile file) {
    String text = file.getAttachmentName();
    String fileName = "";
    String extName = "";
    int index = text.lastIndexOf(".");
    if (index >= 0 && index < text.length() - 1) {
      fileName = text.substring(0, index);
      extName = text.substring(index + 1);
    }
    return new FastDFSFile(fileName, file.getFileData(), extName);
  }

  /**
   * 获取文件绝对路径
   */
  public String getFileAbsolutePath(String[] results) {
    String groupName = results[0];
    String remoteFileName = results[1];
        /*String fileAbsolutePath = PROTOCOL + trackerServer.getInetSocketAddress().getHostName() + PORT_SEPARATOR
				+ TRACKER_NGNIX_PORT + SEPARATOR + groupName + SEPARATOR + remoteFileName;*/
    String fileAbsolutePath = groupName + AttachmentStorageConfigurationView.getInstance().getPathSeparator() + remoteFileName;
    return fileAbsolutePath;
  }

  /**
   * 文件上传
   *
   * @ param file 单文件
   */
  @Override
  public String[] upload(AttachmentFile file) {
    try {
      FastDFSFile fdfsFile = createFastDFSFile(file);
      String[] results = fastDFSFileManager.upload(fdfsFile);
      return results;
    } catch (Exception e) {
      logger.error(e.toString());
    }
    return null;
  }

  /**
   * @param fileName   文件名称
   * @param fileBase64 文件加密方法
   *                   文件加密上传
   */
  @Override
  public FileMessageObject fileUpload(String fileName, String fileBase64) {
    FileMessageObject result = new FileMessageObject();
    try {
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] resultImg = decoder.decodeBuffer(fileBase64.split(",")[1]);
      for (int i = 0; i < resultImg.length; ++i) {
        if (resultImg[i] < 0) {
          // 调整异常数据
          resultImg[i] += 256;
        }
      }

      if (resultImg.length > AttachmentStorageConfigurationView.getInstance().getAllowMaxFileSize()) {
        result.setCode("-1");
        result.setMessage(I18n.getStrings().text("msg_file_size_max"));
        return result;
      }
      List<String> paths = new ArrayList<String>();
      String fName = SysUtils.nullString(fileName);
      int index = fName.lastIndexOf(".");
      String extName = "";
      if (index >= 0 && index < fName.length() - 1) {
        extName = fName.substring(index + 1);
        fName = fName.substring(0, index);
      }
      FastDFSFile fdfsFile = new FastDFSFile(fName, resultImg, extName);
      String[] results = fastDFSFileManager.upload(fdfsFile);
      String fileAbsolutePath = getFileAbsolutePath(results);

      if (fileAbsolutePath == null) {
        result.setCode("-1");
        result.setMessage(I18n.getStrings().text("msg_file_path_isnull"));
        return result;
      }
      paths.add(fileAbsolutePath);
      result.setCode("0");
      result.setMessage(I18n.getStrings().text("msg_file_upload_success"));
      result.setData(paths);
      return result;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      logger.error(e.getMessage(), e);
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_server_exception"));
      return result;
    }
  }

  /**
   * @ param file 单文件
   * @ 单文件上传
   */
  @Override
  public FileMessageObject fileUpload(MultipartFile file) {
    FileMessageObject result = new FileMessageObject();

    try {
      String fName = file.getOriginalFilename();
      int index = fName.lastIndexOf(".");
      String extName = "";
      if (index >= 0 && index < fName.length() - 1) {
        extName = fName.substring(index + 1);
        fName = fName.substring(0, index);
      }

      FastDFSFile fdfsFile = new FastDFSFile(fName, file.getBytes(), extName);
      String[] results = fastDFSFileManager.upload(fdfsFile);
      String fileAbsolutePath = getFileAbsolutePath(results);
      if (fileAbsolutePath == null) {
        result.setCode("-1");
        result.setMessage(I18n.getStrings().text("msg_file_path_isnull"));
        return result;
      }
      GeneralAttachmentFile info = new GeneralAttachmentFile();
      info.setId(DigitalNumberContext.generate("Key_Guid"));
      info.setAttachmentName(fName);
      info.setVirtualPath(fileAbsolutePath);
      info.setCreatedDate(LocalDateTime.now());
      info.setFileSize(Integer.parseInt(file.getSize() + ""));
      info.setFileType(extName);
      AttachmentStorageContext.getInstance().getAttachmentFileService().save(info);

      result.setCode("0");
      result.setMessage(I18n.getStrings().text("msg_file_upload_success"));
      result.setData(info);
      return result;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_server_exception"));
      return result;
    }
  }

  /**
   * @param files 批量上传文件
   *              多文件上传
   */
  @Override
  public FileMessageObject fileUpload(MultipartFile[] files) {
    FileMessageObject result = new FileMessageObject();
    try {
      List<String> paths = new ArrayList<String>();
      for (MultipartFile file : files) {
        String fName = file.getOriginalFilename();
        int index = fName.lastIndexOf(".");
        String extName = "";
        if (index >= 0 && index < fName.length() - 1) {
          extName = fName.substring(index + 1);
          fName = fName.substring(0, index);
        }
        FastDFSFile fdfsFile = new FastDFSFile(fName, file.getBytes(), extName);
        String[] results = fastDFSFileManager.upload(fdfsFile);
        String fileAbsolutePath = getFileAbsolutePath(results);
        if (fileAbsolutePath == null) {
          result.setCode("-1");
          result.setMessage(I18n.getStrings().text("msg_file_path_isnull"));
          return result;
        }
        paths.add(fileAbsolutePath);

        GeneralAttachmentFile info = new GeneralAttachmentFile();
        info.setId(DigitalNumberContext.generate("Key_Guid"));
        info.setAttachmentName(fName);
        info.setVirtualPath(fileAbsolutePath);
        info.setCreatedDate(LocalDateTime.now());
        info.setFileSize(Integer.parseInt(file.getSize() + ""));
        info.setFileType(extName);
        AttachmentStorageContext.getInstance().getAttachmentFileService().save(info);
      }
      // 多个文件一起上传,全部传成功了才返回
      result.setCode("0");
      result.setMessage(I18n.getStrings().text("msg_file_upload_success"));
      result.setData(paths);
      return result;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_server_exception"));
      return result;
    }
  }

  @Override
  public FileMessageObject fileUpload(String localFileName) {
    FileMessageObject result = new FileMessageObject();
    try {
      String fileAbsolutePath = fastDFSFileManager.upload(localFileName);
      List<String> paths = new ArrayList<String>();
      if (fileAbsolutePath == null) {
        result.setCode("-1");
        result.setMessage(I18n.getStrings().text("msg_file_path_isnull"));
        return result;
      }
      paths.add(fileAbsolutePath);
      result.setCode("0");
      result.setMessage(I18n.getStrings().text("msg_file_upload_success"));
      result.setData(paths);
      return result;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_server_exception"));
      return result;
    }
  }

  /**
   * 文件删除  按组删除  fastdfs 中有 group1 , group2 , group3
   *
   * @param remoteFileNameWithGroupName 文件删除
   */
  @Override
  public void deleteFile(String remoteFileNameWithGroupName) {
    try {
      SplitFileNameAdnGroupName splitFileNameAdnGroupName = new SplitFileNameAdnGroupName(remoteFileNameWithGroupName).invoke();
      if (splitFileNameAdnGroupName.is()) {
        return;
      }
      String groupName = splitFileNameAdnGroupName.getGroupName();
      String remoteFileName = splitFileNameAdnGroupName.getRemoteFileName();
      fastDFSFileManager.deleteFile(groupName, remoteFileName);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }


  /**
   * @param remoteFileNameWithGroupName
   * @param savePath                    本地保存路径
   */
  @Override
  public FileMessageObject download(String remoteFileNameWithGroupName, String savePath) {
    FileMessageObject result = new FileMessageObject();
    SplitFileNameAdnGroupName splitFileNameAdnGroupName = new SplitFileNameAdnGroupName(remoteFileNameWithGroupName).invoke();
    if (splitFileNameAdnGroupName.is()) {
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_download_group_exit"));
      return result;
    }
    File dir = new File(savePath);
    if (!dir.exists() && !dir.isDirectory()) dir.mkdirs();
    String groupName = splitFileNameAdnGroupName.getGroupName();
    String remoteFileName = splitFileNameAdnGroupName.getRemoteFileName();
    return fastDFSFileManager.download(groupName, remoteFileName, savePath);
  }

  /**
   * @param remoteFileNameWithGroupName
   */
  @Override
  public FileMessageObject downloadAsBytes(String remoteFileNameWithGroupName) {
    FileMessageObject result = new FileMessageObject();
    SplitFileNameAdnGroupName splitFileNameAdnGroupName = new SplitFileNameAdnGroupName(remoteFileNameWithGroupName).invoke();
    if (splitFileNameAdnGroupName.is()) {
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_download_group_exit"));
      return result;
    }
    String groupName = splitFileNameAdnGroupName.getGroupName();
    String remoteFileName = splitFileNameAdnGroupName.getRemoteFileName();
    return fastDFSFileManager.downloadAsBytes(groupName, remoteFileName);
  }

  @Override
  public void outFileStream(String fileURL, Boolean kjbDFS, HttpServletResponse response) {
    String file = fileURL;
    if (kjbDFS)
      //   file = systemConfig.getFileServerUrl() + fileURL;
      try {
        //http://114.215.221.123/ueditor/jsp/upload/file/20170413/1492017493544030070.pdf
        // 纯下载方式
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.substring(file.lastIndexOf("/" + 1), file.length()));
        URL url = new URL(file);
        URLConnection conn = url.openConnection();
        InputStream fileInputStream = conn.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
  }

  private class SplitFileNameAdnGroupName {
    private boolean myResult;
    private String remoteFileNameWithGroupName;
    private String groupName;
    private String remoteFileName;

    public SplitFileNameAdnGroupName(String remoteFileNameWithGroupName) {
      this.remoteFileNameWithGroupName = remoteFileNameWithGroupName;
    }

    boolean is() {
      return myResult;
    }

    public String getGroupName() {
      return groupName;
    }

    public String getRemoteFileName() {
      return remoteFileName;
    }

    public SplitFileNameAdnGroupName invoke() {
      int pos = remoteFileNameWithGroupName.indexOf(AttachmentStorageConfigurationView.getInstance().getPathSeparator());
      if (pos < 1) {
        myResult = true;
        return this;
      }
      groupName = remoteFileNameWithGroupName.substring(0, pos);
      remoteFileName = remoteFileNameWithGroupName.substring(pos + 1);
      myResult = false;
      return this;
    }
  }


  /**
   * 普通文件上传
   * @param file
   * @return
   */
  @Override
  public FileMessageObject uploadFile(File file)  {
    FileMessageObject result = new FileMessageObject();
    try {
      InputStream inputStream = null;
      inputStream = new FileInputStream(file);
      byte[] bytes = toByteArray(inputStream,inputStream.available());
//    FileInputStream inputStream = new FileInputStream(file);

    String fName = file.getName();
    int index = fName.lastIndexOf(".");
    String extName = "";
    if (index >= 0 && index < fName.length() - 1) {
      extName = fName.substring(index + 1);
      fName = fName.substring(0, index);
    }

    FastDFSFile fdfsFile = new FastDFSFile(fName, bytes, extName);
    String[] results = fastDFSFileManager.upload(fdfsFile);
    String fileAbsolutePath = getFileAbsolutePath(results);
    if (fileAbsolutePath == null) {
      result.setCode("-1");
      result.setMessage(I18n.getStrings().text("msg_file_path_isnull"));
      return result;
    }
    GeneralAttachmentFile info = new GeneralAttachmentFile();
    info.setId(DigitalNumberContext.generate("Key_Guid"));
    info.setAttachmentName(fName);
    info.setVirtualPath(fileAbsolutePath);
    info.setCreatedDate(LocalDateTime.now());
    info.setFileSize(Integer.parseInt(inputStream.available() + ""));
    info.setFileType(extName);
    AttachmentStorageContext.getInstance().getAttachmentFileService().save(info);

    result.setCode("0");
    result.setMessage(I18n.getStrings().text("msg_file_upload_success"));
    result.setData(info);
    } catch (Exception e) {
      logger.error("uploadFile 普通文件上传失败"+e.getMessage());
      e.printStackTrace();
    }
    return result;

  }


  private byte[] toByteArray(InputStream in,int len) {
    ByteArrayOutputStream out = null;
    try {
      out = new ByteArrayOutputStream();
      byte[] buffer = new byte[len];
      int n = 0;
      while ((n = in.read(buffer)) != -1) {
        out.write(buffer, 0, n);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }
}
