package com.x3platform.attachmentstorage.services;

import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.FileMessageObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * fastdfs文件上传服务接口
 */
public interface FastDFSFileTransferService {

  public String getFileAbsolutePath(String[] results);

  /**
   * 上传文件
   *
   * @param file 附件信息
   */
  public String[] upload(AttachmentFile file);

  /**
   * @param fileName   文件名称
   * @param fileBase64 文件加密方法
   * @return 加密上传
   */
  public FileMessageObject fileUpload(String fileName, String fileBase64);

  /**
   * @param file 单文件
   */
  public FileMessageObject fileUpload(MultipartFile file);

  /**
   * @param myfiles 多文件上传
   */
  public FileMessageObject fileUpload(MultipartFile[] myfiles);

  /**
   * @param localFileName 上传本地文件
   */
  public FileMessageObject fileUpload(String localFileName);

  /**
   * @param remoteFileNameWithGroupName 文件删除
   */
  public void deleteFile(String remoteFileNameWithGroupName);

  /**
   * @param remoteFileNameWithGroupName 文件下载
   * @param savePath                    保存路径
   */
  public FileMessageObject download(String remoteFileNameWithGroupName, String savePath);

  /**
   * @param remoteFileNameWithGroupName 文件下载
   */
  public FileMessageObject downloadAsBytes(String remoteFileNameWithGroupName);

  /**
   * @param fileURL
   * @param kjbDFS
   * @param response 文件预览
   */
  public void outFileStream(String fileURL, Boolean kjbDFS, HttpServletResponse response);

  /**
   * 普通文件上传
   * @param file
   * @return
   */
  public FileMessageObject uploadFile(File file);

}
